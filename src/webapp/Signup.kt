package com.sestikom.ctsdigital.webapp

import com.sestikom.ctsdigital.*
import com.sestikom.ctsdigital.model.*
import com.sestikom.ctsdigital.model.session.*
import com.sestikom.ctsdigital.repository.*
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import org.jetbrains.exposed.sql.*

const val SIGNUP = "/manager/signup"

@KtorExperimentalLocationsAPI
@Location(SIGNUP)
data class Signup(
        val username: String = "",
        val error: String = ""
)

@KtorExperimentalLocationsAPI
fun Route.signup(db: Repository, hashFunction: (String) -> String) {
  get<Signup> {
    val user = getUserFromSession(call.sessions.get<CTSSession>(), db)
    when {
      user == null
      -> call.respond(FreeMarkerContent(
              "signup.ftl", mapOf("error" to it.error))
      )
      user !is TestCenterManager
      -> call.respondText("No access")
      user.position == null
      -> call.redirect(CenterRegistration())
      else
      -> call.respondText("Manager still in session, ")
    }
  }

  post<Signup> {
    val user = call.sessions.get<CTSSession>()?.let {
      db.getUser(it.username)
    }
    if (user != null)
      return@post call.respondText("User has session")
    val signupParameters = call.receive<Parameters>()
    val username = signupParameters["username"] ?: return@post call.redirect(it)
    val password = signupParameters["password"] ?: return@post call.redirect(it)
    val firstName = signupParameters["firstName"] ?: return@post call.redirect(it)
    val lastName = signupParameters["lastName"] ?: return@post call.redirect(it)
    val response = TestCenterManager().signup(
            username = username,
            password = password,
            firstName = firstName,
            lastName = lastName
    )
    val signupError = Signup(username)
    response.errorMessage?.let {
      call.redirect(signupError.copy(error = it))
    } ?: run {
      val manager = response.officer as TestCenterManager
      val hash = hashFunction(manager.password)
      try {
        db.createManager(manager.copy(password = hash))
      } catch (e: Throwable) {
        when {
          db.getUser(manager.username) != null ->
           call.redirect(signupError.copy(error = "Username \'$username\' is already taken"))
          else -> {
            application.log.error("Failed to register user", e)
            call.redirect(signupError.copy(error = "Failed to register user"))
          }
        }
      }
      call.sessions.set(CTSSession(manager.username))
      call.redirect(CenterRegistration())
    }
  }
}