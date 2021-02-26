package ru.newuserkk.controller.auth

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.ktor.http.*
import io.ktor.server.testing.*
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import ru.newuserkk.application.LaunchMode
import ru.newuserkk.application.module
import ru.newuserkk.common.rightOf
import ru.newuserkk.data.auth.AuthFacade

class AuthControllerTestContainer : PostgreSQLContainer<AuthControllerTestContainer>(
    DockerImageName.parse("postgres:latest")
) {

}

class AuthControllerTest : FreeSpec({
//    val container = AuthControllerTestContainer().withExposedPorts(5431)
//    lateinit var underTest: AuthController
//
//    val authFacade: AuthFacade = mock {
//        on { registerUser(any(), any()) } doReturn rightOf(Unit)
//    }
//
//    beforeEach {
//        underTest = AuthController(authFacade, mock())
//    }

//    "should return OK when registered successfully" {
//        withTestApplication({ module(launchMode = LaunchMode.UNIT_TEST) }) {
//            handleRequest(HttpMethod.Get, "/api/auth/register").apply {
//                response.status() shouldBe HttpStatusCode.OK
//            }
//        }
//    }
})