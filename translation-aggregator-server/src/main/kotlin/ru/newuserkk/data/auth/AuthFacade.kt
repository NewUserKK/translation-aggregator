package ru.newuserkk.data.auth

import ru.newuserkk.common.OperationResult
import ru.newuserkk.common.leftOf
import ru.newuserkk.model.auth.User

typealias Username = String
typealias Password = String

class AuthError(message: String) : Exception(message)

class AuthFacade(private val authRepository: AuthRepository) {
    fun registerUser(username: Username, password: Password): OperationResult<Unit> = when {
        username.isBlank() || password.isBlank() -> leftOf(
            AuthError("Username or password should not be blank!")
        )
        else -> authRepository.addUser(username, password)
    }

    fun authorizeUser(username: Username, password: Password): OperationResult<User> = when {
        username.isBlank() || password.isBlank() -> leftOf(
            AuthError("Username or password should not be blank!")
        )
        else -> authRepository.authenticate(username, password)
    }
}