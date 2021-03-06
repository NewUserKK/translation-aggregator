package ru.newuserkk.application

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.json.*
import org.kodein.di.*
import org.kodein.di.ktor.di
import ru.newuserkk.controller.auth.AuthController
import ru.newuserkk.controller.history.HistoryController
import ru.newuserkk.controller.translation.TranslationController
import ru.newuserkk.controller.translation.TranslationControllerImpl
import ru.newuserkk.controller.translation.TranslationControllerTest
import ru.newuserkk.data.auth.AuthDao
import ru.newuserkk.data.auth.AuthFacade
import ru.newuserkk.data.auth.AuthRepository
import ru.newuserkk.data.history.HistoryRepository
import ru.newuserkk.service.translation.RegularTranslationService
import ru.newuserkk.service.translation.UrbanTranslationService

val objectMapper = ObjectMapper().apply {
    enable(SerializationFeature.INDENT_OUTPUT)
    disable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID)
    disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)

    registerModule(KotlinModule())
}

val httpClient = HttpClient(Apache) {
    install(JsonFeature) {
        serializer = JacksonSerializer(objectMapper)
    }
}

fun Application.setupDi(launchMode: LaunchMode) = di {
    bind<Router>() with singleton { Router(instance(), instance(), instance()) }
    bind<HttpClient>() with provider { httpClient }

    import(authModule())
    import(translationModule(launchMode))
    import(historyModule())
}

private fun authModule() = DI.Module(name = "authModule") {
    bind<AuthController>() with singleton { AuthController(instance(), instance()) }
    bind<AuthFacade>() with singleton { AuthFacade(instance()) }
    bind<AuthRepository>() with singleton { AuthRepository(instance()) }
    bind<AuthDao>() with singleton { AuthDao() }
}

private fun translationModule(launchMode: LaunchMode) = DI.Module(name = "translationModule") {
    bind<TranslationController>() with singleton {
        when(launchMode) {
            LaunchMode.PRODUCTION -> TranslationControllerImpl(instance(), instance(), instance())
            else -> TranslationControllerTest
        }
    }

    bind<UrbanTranslationService>() with singleton { UrbanTranslationService(instance()) }
    bind<RegularTranslationService>() with singleton { RegularTranslationService(instance()) }
}

private fun historyModule() = DI.Module("historyModule") {
    bind<HistoryController>() with singleton { HistoryController(instance()) }
    bind<HistoryRepository>() with singleton { HistoryRepository() }
}
