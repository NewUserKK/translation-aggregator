package ru.newuserkk.data.auth

import org.jetbrains.exposed.sql.deleteAll

class AuthDao {
    fun findUser(username: String): CredentialsTableEntity? = CredentialsTableEntity
        .find { CredentialsTable.username eq username }
        .firstOrNull()

    fun addUser(username: Username, password: Password)  {
        CredentialsTableEntity.new {
            this.username = username
            this.password = password
        }
    }

    fun deleteAllUsers() = CredentialsTable.deleteAll()
}
