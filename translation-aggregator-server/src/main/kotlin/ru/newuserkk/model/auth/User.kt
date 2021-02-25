package ru.newuserkk.model.auth

typealias UserId = Long

data class User(val name: String, val id: UserId = 0)
