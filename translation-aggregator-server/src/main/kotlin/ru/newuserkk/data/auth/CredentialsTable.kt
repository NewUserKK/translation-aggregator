package ru.newuserkk.data.auth

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import ru.newuserkk.model.auth.User

object CredentialsTable : LongIdTable() {
    val username = varchar("username", 64)
    val password = text("password")
}

class CredentialsTableEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<CredentialsTableEntity>(CredentialsTable)

    var username by CredentialsTable.username
    var password by CredentialsTable.password
}

fun CredentialsTableEntity.asUser(): User =
    User(name = username, id = id.value)
