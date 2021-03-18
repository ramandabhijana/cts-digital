package com.sestikom.ctsdigital.webapp

import com.sestikom.ctsdigital.model.TestCenterManager
import com.sestikom.ctsdigital.model.session.CTSSession
import com.sestikom.ctsdigital.repository.Repository
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*

const val LOGIN = "/login"

@KtorExperimentalLocationsAPI
@Location(LOGIN)
data class Login(val username: String = "", val error: String = "")

@KtorExperimentalLocationsAPI
fun Route.login(db: Repository, hashFunction: (String) -> String) {
    get<Login> {
        val user = call.sessions.get<CTSSession>()?.let {
            db.getUser(it.username)
        }
        if (user != null)
            call.respondText("User still in session")
        else
            call.respond(FreeMarkerContent("logindummy.ftl", null))
    }

    post<Login> {
        val username = "abhi180031251"
        val password = "password123"
        val response = TestCenterManager().login(
            username = username,
            password = password,
        )
        val loginError = Login(username)
        response?.let {
            call.respondText(it)
//      call.redirect(loginError.copy(error = it))
        } ?: run {
            val hash = hashFunction(password)
            db.getUser(username, hash)?.let {
                call.sessions.set(CTSSession(username))
                call.respondText("User logged in")
            } ?: run {
                call.respondText("Username and Password do not match")
//        call.redirect(loginError.copy(error = "Username and Password do not match"))
            }
        }
    }
}