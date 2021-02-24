package ru.newuserkk.controller.translation

import io.ktor.application.*
import io.ktor.routing.*

object TranslationControllerTest : TranslationController() {
    override fun Route.doProvideRoutes() {
    }
}