package com.sestikom.ctsdigital.webapp

import com.sestikom.ctsdigital.*
import com.sestikom.ctsdigital.model.*
import com.sestikom.ctsdigital.model.session.CTSSession
import com.sestikom.ctsdigital.repository.Repository
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import java.lang.IllegalArgumentException

const val LOGIN = "/login"
const val SESSION_TIMED_OUT = "Session has timed out. Please Login again"

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
            call.redirectUser(user)
        else
            call.respond(FreeMarkerContent(
                "login.ftl",
                mapOf(
                    "userId" to it.username,
                    "error" to it.error
                )
            ))
    }

    post<Login> {
        val params = call.receive<Parameters>()
        val username = params["username"] ?: throw IllegalArgumentException("Missing argument username")
        val password = params["password"] ?: throw IllegalArgumentException("Missing argument password")
        val response = TestCenterManager().login(
            username = username,
            password = password,
        )
        val loginError = Login(username)
        response?.let {
            call.redirect(loginError.copy(error = it))
        } ?: run {
            val hash = hashFunction(password)
            db.getUser(username, hash)?.let {
                call.sessions.set(CTSSession(username))
                call.redirectUser(it)
            } ?: run {
                call.redirect(loginError.copy(error = "Username and Password do not match"))
            }
        }
    }
}