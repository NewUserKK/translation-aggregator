package ru.newuserkk.controller.translation

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.ktor.util.*
import io.ktor.util.pipeline.*
import ru.newuserkk.common.Left
import ru.newuserkk.common.Right
import ru.newuserkk.controller.auth.Session
import ru.newuserkk.data.history.HistoryRepository
import ru.newuserkk.service.translation.RegularTranslationService
import ru.newuserkk.service.translation.TranslationService
import ru.newuserkk.service.translation.UrbanTranslationService

class TranslationControllerImpl(
    private val regularTranslationService: RegularTranslationService,
    private val urbanTranslationService: UrbanTranslationService,
    private val historyRepository: HistoryRepository
) : TranslationController() {
    override fun Route.doProvideRoutes() {
        get("regular") {
            executeTranslateCall(regularTranslationService)
        }

        get("urban") {
            executeTranslateCall(urbanTranslationService)
        }
    }

    private suspend fun PipelineContext<Unit, ApplicationCall>.executeTranslateCall(service: TranslationService) {
        val word = call.parameters.getOrFail("word")
        when (val result = service.translate(word)) {
            is Left -> {
                println(result.value)
                call.respond(HttpStatusCode.BadGateway, result.value.toString())
            }
            is Right -> {
                val session = call.sessions.get<Session>()
                if (session != null) {
                    historyRepository.addQueryToHistory(session.id, word)
                }
                call.respond(HttpStatusCode.OK, result.value)
            }
        }
    }
}
