package ru.newuserkk.application

import io.ktor.application.*
import io.ktor.routing.*
import ru.newuserkk.controller.auth.AuthController
import ru.newuserkk.controller.translation.TranslationController

class Router(
    private val authController: AuthController,
    private val translationController: TranslationController
) {
    fun provideRouting(application: Application): Routing = application.routing {
        route("api") {
            authController.provideRoutes(this)
            translationController.provideRoutes(this)
        }
    }
}