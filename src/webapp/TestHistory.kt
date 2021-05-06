package com.sestikom.ctsdigital.webapp

import com.sestikom.ctsdigital.*
import com.sestikom.ctsdigital.model.*
import com.sestikom.ctsdigital.model.session.*
import com.sestikom.ctsdigital.repository.*
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*

const val TEST_HISTORY = "/patient/testhistory"

@KtorExperimentalLocationsAPI
@Location(TEST_HISTORY)
class TestHistory

@KtorExperimentalLocationsAPI
fun Route.testHistory(db: Repository) {
  get<TestHistory> {
    val user = getUserFromSession(call.sessions.get<CTSSession>(), db)
    when (user) {
      null
      -> call.redirect(Login(error = SESSION_TIMED_OUT))
      !is Patient -> call.respondText("No access")
      else -> {
        val tests = db.getTestHistory(user.username)
        call.respond(FreeMarkerContent(
                "testhistory.ftl",
                mapOf("tests" to tests)
        ))
      }
    }
  }
}