package ru.newuserkk.application

object Config {
    val isTestMode = System.getenv("TEST_MODE") == "true"
}