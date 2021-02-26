package ru.newuserkk.data.auth

import org.jetbrains.exposed.sql.transactions.transaction
import ru.newuserkk.common.OperationResult
import ru.newuserkk.common.asRight
import ru.newuserkk.common.leftOf
import ru.newuserkk.model.auth.User

class AuthRepository(private val authDao: AuthDao) {
    fun addUser(username: Username, password: Password): OperationResult<Unit> = transaction {
        when (authDao.findUser(username)) {
            null -> authDao.addUser(username, password).asRight()
            else -> leftOf(AuthError("User $username is already registered!"))
        }
    }


    fun authenticate(username: Username, password: Password): OperationResult<User> = transaction {
        val existingUser = authDao.findUser(username)

        when {
            existingUser == null -> leftOf(AuthError("User with name $username is not registered!"))
            existingUser.password != password -> leftOf(AuthError("Invalid password"))
            else -> existingUser.asUser().asRight()
        }

    }

    fun clearUsers() = transaction {
        authDao.deleteAllUsers()
    }
}
