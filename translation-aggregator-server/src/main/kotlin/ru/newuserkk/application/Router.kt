package ru.newuserkk.application

import io.ktor.application.*
import io.ktor.routing.*
import ru.newuserkk.controller.auth.AuthController
import ru.newuserkk.controller.history.HistoryController
import ru.newuserkk.controller.translation.TranslationController

class Router(
    private val authController: AuthController,
    private val translationController: TranslationController,
    private val historyController: HistoryController
) {
    fun provideRouting(application: Application): Routing = application.routing {
        route("api") {
            authController.provideRoutes(this)
            translationController.provideRoutes(this)
            historyController.provideRoutes(this)
        }
    }
}
