package com.sestikom.ctsdigital

import com.sestikom.ctsdigital.auth.*
import com.sestikom.ctsdigital.model.*
import com.sestikom.ctsdigital.model.session.*
import com.sestikom.ctsdigital.repository.*
import com.sestikom.ctsdigital.webapp.*
import freemarker.cache.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.freemarker.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
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
        static("/static") {
            resources("images")
        }
        home(db)
        logout()
        login(db,hashFunction)
        signup(db, hashFunction)
        registerCenter(db, hashFunction)
        recordTester(db, hashFunction)
        manageKit(db)
        recordTest(db, hashFunction)
        updateTestResult(db)
    }
}

suspend fun getUserFromSession(session: CTSSession?, db: Repository): User? =
        session?.let {
            db.getUser(it.username)
        }