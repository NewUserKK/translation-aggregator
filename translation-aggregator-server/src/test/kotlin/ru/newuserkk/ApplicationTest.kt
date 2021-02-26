package ru.newuserkk

import com.nhaarman.mockitokotlin2.mock
import io.kotest.core.spec.style.FreeSpec
import io.ktor.http.*
import io.ktor.server.testing.*
import ru.newuserkk.application.LaunchMode
import ru.newuserkk.application.module

class ApplicationTest : FreeSpec({
    "test" {
        withTestApplication({ module(launchMode = LaunchMode.UNIT_TEST) }) {
            handleRequest(HttpMethod.Get, "/").apply {
            }
        }
    }
})
