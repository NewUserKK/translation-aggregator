package ru.newuserkk.controller

import io.ktor.routing.*

abstract class BaseController(private val initialPath: String) {
    fun provideRoutes(routing: Route): Route = routing.route(initialPath) {
        doProvideRoutes()
    }

    protected abstract fun Route.doProvideRoutes()
}
