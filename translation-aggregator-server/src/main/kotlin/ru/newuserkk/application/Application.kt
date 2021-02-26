package ru.newuserkk.application

import ch.qos.logback.core.util.OptionHelper.getEnv
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.request.*
import io.ktor.sessions.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.kodein.di.instance
import org.kodein.di.ktor.di
import org.slf4j.event.Level
import ru.newuserkk.controller.auth.Session
import ru.newuserkk.data.auth.CredentialsTable

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

/**
 * Please note that you can use any other name instead of *module*.
 * Also note that you can have more then one modules in your application.
 * */
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(launchMode: LaunchMode = Config.launchMode) {
    setupDi(launchMode)

    if (launchMode != LaunchMode.UNIT_TEST) {
        setupDatabase()
    }

    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Post)
        method(HttpMethod.Get)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        header(HttpHeaders.AccessControlAllowOrigin)
        header(HttpHeaders.AccessControlAllowHeaders)
        allowCredentials = true
        allowNonSimpleContentTypes = true
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }

    install(Sessions) {
        cookie<Session>("SESSION") {
            cookie.extensions["SameSite"] = "lax"
        }
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    val router by di().instance<Router>()
    router.provideRouting(this)
}

fun Application.setupDatabase() {
    val host = getEnv("POSTGRES_HOST")
    val db = getEnv("POSTGRES_DB")
    val user = getEnv("POSTGRES_USER")
    val password = getEnv("POSTGRES_PASSWORD")
    val port = getEnv("POSTGRES_PORT")

    Database.connect(
        url = "jdbc:postgresql://$host:$port/$db",
        driver = "org.postgresql.Driver",
        user = user,
        password = password
    )

    transaction {
        SchemaUtils.create(CredentialsTable)
    }
}