package ru.newuserkk.application

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.json.*
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.ktor.di
import org.kodein.di.provider
import org.kodein.di.singleton
import ru.newuserkk.controller.translation.TranslationController
import ru.newuserkk.controller.translation.TranslationControllerImpl
import ru.newuserkk.controller.translation.TranslationControllerTest
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

fun Application.setupDi(testing: Boolean) = di {
    bind<HttpClient>() with provider { httpClient }
    bind<UrbanTranslationService>() with singleton { UrbanTranslationService(instance()) }
    bind<RegularTranslationService>() with singleton { RegularTranslationService(instance()) }
    bind<TranslationController>() with singleton {
        when {
            !testing -> TranslationControllerImpl(instance(), instance())
            else -> TranslationControllerTest
        }
    }
    bind<Router>() with singleton { Router(instance()) }
}
