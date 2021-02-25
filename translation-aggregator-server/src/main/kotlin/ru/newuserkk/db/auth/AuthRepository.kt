package ru.newuserkk.db.auth

import ru.newuserkk.common.OperationResult
import ru.newuserkk.common.asRight
import ru.newuserkk.common.leftOf
import ru.newuserkk.common.rightOf
import ru.newuserkk.model.auth.User
import ru.newuserkk.model.auth.UserId

private typealias Username = String
private typealias Password = String

class AuthError(message: String?) : Exception(message)

class AuthRepository {
    private var lastId: UserId = 0L
    private val credentials = mutableMapOf<Username, Password>()

    fun register(username: Username, password: Password): OperationResult<Unit> = when {
        username in credentials -> leftOf(AuthError("User $username is already registered!"))
        else -> {
            credentials[username] = password
            rightOf(Unit)
        }
    }

    fun authenticate(username: Username, password: Password): OperationResult<User> = when {
        username !in credentials -> leftOf(AuthError("User with name $username is not registered!"))
        credentials[username] != password -> leftOf(AuthError("Invalid password"))
        else -> User(username, id = lastId++).asRight()
    }

    fun clearUsers() {
        lastId = 0
        credentials.clear()
    }
}
