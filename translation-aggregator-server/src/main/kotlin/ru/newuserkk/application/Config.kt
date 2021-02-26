package ru.newuserkk.application

enum class LaunchMode {
    E2E_TEST, UNIT_TEST, PRODUCTION
}

object Config {
    val launchMode = when {
        System.getenv("TEST_MODE") == "true" -> LaunchMode.E2E_TEST
        else -> LaunchMode.PRODUCTION
    }
}