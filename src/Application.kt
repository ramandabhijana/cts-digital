package com.sestikom.ctsdigital

import com.sestikom.ctsdigital.auth.hash
import com.sestikom.ctsdigital.auth.hashKey
import com.sestikom.ctsdigital.model.session.CTSSession
import com.sestikom.ctsdigital.repository.CTSRepository
import com.sestikom.ctsdigital.repository.DatabaseFactory
import com.sestikom.ctsdigital.webapp.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import freemarker.cache.*
import io.ktor.features.*
import io.ktor.freemarker.*
import io.ktor.gson.*
import io.ktor.locations.*
import io.ktor.sessions.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalLocationsAPI
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(DefaultHeaders)
    install(StatusPages) {
        exception<Throwable> { e ->
            call.respondText(
                e.toString(),
                ContentType.Text.Plain,
                HttpStatusCode.InternalServerError,
            )
        }
    }
    install(ContentNegotiation) {
        gson()
    }
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }
    install(Locations)
    install(Sessions) {
        cookie<CTSSession>("SESSION") {
            transform(SessionTransportTransformerMessageAuthentication(hashKey))
            cookie.maxAgeInSeconds = 1000
        }
    }

    val hashFunction = { s: String -> hash(s) }
    DatabaseFactory.init()
    val db = CTSRepository()

    routing {
        home(db)
        logout()
        login(db,hashFunction)
    }
}

