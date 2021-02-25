package ru.newuserkk.common

fun requireEnv(name: String): String =
    System.getenv(name) ?: error("Env variable $name not specified!")
