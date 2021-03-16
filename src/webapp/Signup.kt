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
    val user = call.sessions.get<CTSSession>()?.let {
      db.getUser(it.username)
    }
    when(user) {
      null
      -> call.respond(FreeMarkerContent("signupdummy.ftl", null))
      !is TestCenterManager
      -> call.respondText("No access")
      else
      -> call.respondText("Manager still in session, ")
    }
  }

  post<Signup> {
    val user = call.sessions.get<CTSSession>()?.let {
      db.getUser(it.username)
    }
    val username = "abhijana99"
    val password = "abhijana123"
    val firstName = "abhijana"
    val lastName = "ramanda"

    if (user != null) return@post call.respondText("User has session")
    val signupParameters = call.receive<Parameters>()
    val response = TestCenterManager().signup(
            username = username,
            password = password,
            firstName = firstName,
            lastName = lastName
    )
    response.errorMessage?.let {
      call.redirect(Signup(error = it ))
    } ?: run {
      val manager = response.manager!!
      val hash = hashFunction(manager.password)
      try {
        db.createManager(manager.copy(password = hash))
      } catch (e: Throwable) {
        when {
          db.getUser(manager.username) != null ->
            call.respondText("Username already taken")
          // call.redirect(Signup(error = it))
          else -> {
            application.log.error("Failed to register user", e)
            call.redirect(Signup(error = "Failed to register user"))
          }
        }
      }
      call.sessions.set(CTSSession(manager.username))
      call.respondText("Manager created")
    }
  }
}