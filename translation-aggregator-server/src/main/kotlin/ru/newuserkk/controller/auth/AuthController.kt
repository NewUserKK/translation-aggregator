package ru.newuserkk.controller.auth

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.ktor.util.pipeline.*
import ru.newuserkk.application.Config
import ru.newuserkk.application.LaunchMode
import ru.newuserkk.common.Left
import ru.newuserkk.common.Right
import ru.newuserkk.controller.BaseController
import ru.newuserkk.data.auth.AuthFacade
import ru.newuserkk.data.auth.AuthRepository
import ru.newuserkk.model.auth.UserId

data class Session(val username: String, val id: UserId = 0)

private data class Credentials(val username: String, val password: String)

class AuthController(
    private val authFacade: AuthFacade,
    private val authRepository: AuthRepository
) : BaseController("auth") {
    override fun Route.doProvideRoutes() {
        get("ping") {
            authorized {
                call.respond(HttpStatusCode.OK)
            }
        }

        post("register") {
            val body = call.receiveOrNull<Credentials>()

            if (body == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid body")
                return@post
            }

            when (val registrationResult = authFacade.registerUser(body.username, body.password)) {
                is Right -> call.respond(HttpStatusCode.OK)
                is Left -> call.respond(HttpStatusCode.BadRequest, registrationResult.value.message.toString())
            }
        }

        post("login") {
            val body = call.receiveOrNull<Credentials>()

            if (body == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid body")
                return@post
            }

            with(body) {
                when (val authenticationResult = authFacade.authorizeUser(username, password)) {
                    is Right -> with(authenticationResult.value) {
                        call.sessions.set(Session(username, id))
                        call.respond(HttpStatusCode.OK, authenticationResult.value)
                    }
                    is Left -> call.respond(
                        HttpStatusCode.BadRequest,
                        authenticationResult.value.message.toString()
                    )
                }
            }
        }

        post("logout") {
            authorized {
                call.sessions.clear<Session>()
                call.respond(HttpStatusCode.OK)
            }
        }

        if (Config.launchMode == LaunchMode.E2E_TEST) {
            post("clearUsers") {
                authRepository.clearUsers()
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}

suspend inline fun PipelineContext<Unit, ApplicationCall>.authorized(block: (Session) -> Unit) {
    when (val session = call.sessions.get<Session>()) {
        null -> call.respond(HttpStatusCode.Unauthorized)
        else -> block(session)
    }
}
