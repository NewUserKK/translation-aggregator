package ru.newuserkk.controller.history

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.newuserkk.controller.BaseController
import ru.newuserkk.controller.auth.authorized
import ru.newuserkk.data.history.HistoryRepository

class HistoryController(private val historyRepository: HistoryRepository) : BaseController("history") {
    override fun Route.doProvideRoutes() {
        get("") {
            authorized { session ->
                val history = historyRepository.getHistory(session.id)
                call.respond(HttpStatusCode.OK, history)
            }
        }
    }
}
