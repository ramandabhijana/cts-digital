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
        val activeIndex: String = "1",
        val error: String? = null,
        val success: String? = null
)

@KtorExperimentalLocationsAPI
fun Route.recordTester(db: Repository, hashFunction: (String) -> String) {
  get<RecordTester> {
    val user = getUserFromSession(call.sessions.get<CTSSession>(), db)
    when {
      user == null
      -> call.respond(FreeMarkerContent(
              "login.ftl", mapOf("error" to it.error))
      )
      user !is TestCenterManager
      -> call.respondText("No access")
      user.position == null
      -> call.redirect(CenterRegistration())
      else
      -> call.respond(FreeMarkerContent(
              "recordtester.ftl",
              mapOf(
                      "success" to it.success,
                      "activeIndex" to it.activeIndex,
                      "error" to it.error,
              ))
      )
    }
  }

  post<RecordTester> {
    val user = getUserFromSession(call.sessions.get<CTSSession>(), db)
    if (user == null) call.redirect(Login())
    else {
      val params = call.receive<Parameters>()
      val username = params["username"] ?: return@post call.redirect(it)
      val password = params["password"] ?: return@post call.redirect(it)
      val firstName = params["firstname"] ?: return@post call.redirect(it)
      val lastName = params["lastname"] ?: return@post call.redirect(it)
      val manager = (user as TestCenterManager)
      val creation = manager.recordTester(
              username, password, firstName, lastName
      )
      val record = RecordTester()
      creation.errorMessage?.let {
        call.redirect(record.copy(error = it))
      } ?: run {
        val tester = creation.officer as Tester
        val hash = hashFunction(tester.password)
        try {
          db.createTester(tester.copy(password = hash), manager.username)
        } catch (e: Throwable) {
          when {
            db.getUser(tester.username) != null ->
              return@post call.redirect(record.copy(error = "Username \'$username\' is already taken"))
            else -> {
              application.log.error("Failed to register user", e)
              return@post call.redirect(record.copy(error = "Failed to register user"))
            }
          }
        }
        call.redirect(record.copy(success = "Tester Created"))
      }
    }
  }
}