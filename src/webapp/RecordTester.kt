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

const val RECORD_TESTER = "/manager/recordtester"

@KtorExperimentalLocationsAPI
@Location(RECORD_TESTER)
data class RecordTester(
        val error: String = ""
)

@KtorExperimentalLocationsAPI
fun Route.recordTester(db: Repository, hashFunction: (String) -> String) {
  get<RecordTester> {
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
      -> call.respond(FreeMarkerContent(
              "recordtesterdummy.ftl", mapOf("error" to it.error))
      )
    }
  }

  post<RecordTester> {
    val user = getUserFromSession(call.sessions.get<CTSSession>(), db)
    if (user == null) call.redirect(Login())
    else {
      val params = call.receive<Parameters>()
      val username = "Aldowisnu"
      val password = "Password123"
      val firstName = "Aldo"
      val lastName = "Wisnu"
      val manager = (user as TestCenterManager)
      val creation = manager.recordTester(
              username, password, firstName, lastName
      )
      val recordError = RecordTester()
      creation.errorMessage?.let {
        call.redirect(recordError.copy(error = it))
      } ?: run {
        val tester = creation.officer as Tester
        val hash = hashFunction(tester.password)
        try {
          db.createTester(tester.copy(password = hash), manager.username)
        } catch (e: Throwable) {
          when {
            db.getUser(tester.username) != null ->
              call.redirect(recordError.copy(error = "Username \'$username\' is already taken"))
            else -> {
              application.log.error("Failed to register user", e)
              call.redirect(recordError.copy(error = "Failed to register user"))
            }
          }
        }
        call.respondText("Tester Created")
      }
    }

  }
}