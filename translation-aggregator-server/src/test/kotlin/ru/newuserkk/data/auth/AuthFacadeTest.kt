package ru.newuserkk.data.auth

import com.nhaarman.mockitokotlin2.mock
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.types.shouldBeTypeOf
import ru.newuserkk.common.Left

class AuthFacadeTest : FreeSpec({
    lateinit var authFacade: AuthFacade
    lateinit var authRepository: AuthRepository

    beforeAny {
        authRepository = AuthRepository(mock())
        authFacade = AuthFacade(authRepository)
    }

    "cannot register user with blank username" {
        authFacade.registerUser("", "pass").shouldBeTypeOf<Left<*>>()
    }

    "cannot register user with blank password" {
        authFacade.registerUser("user", "").shouldBeTypeOf<Left<*>>()
    }

    "cannot authenticate user with blank username" {
        authFacade.authorizeUser("", "pass").shouldBeTypeOf<Left<*>>()
    }

    "cannot authenticate user with blank password" {
        authFacade.registerUser("user", "").shouldBeTypeOf<Left<*>>()
    }
})
