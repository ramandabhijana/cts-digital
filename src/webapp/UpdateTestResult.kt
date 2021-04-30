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
import org.joda.time.*
import java.lang.IllegalArgumentException
import java.time.*
import java.time.LocalDate
import java.time.format.*

const val UPDATE_TEST_RESULT = "/tester/updatetestresult"

@KtorExperimentalLocationsAPI
@Location(UPDATE_TEST_RESULT)
data class UpdateTestResult(
        val activeIndex: String = "2",
        val error: String? = null,
        val success: String? = null,
        val dateNow: String = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
)

@KtorExperimentalLocationsAPI
fun Route.updateTestResult(db: Repository) {
  get<UpdateTestResult> {
    val user = getUserFromSession(call.sessions.get<CTSSession>(), db)
    when {
      user == null
      -> call.redirect(Login(error = SESSION_TIMED_OUT))
      user !is Tester
      -> call.respondText("No access")
      db.getPendingTests(user.username).isNullOrEmpty()
      -> call.redirect(TesterDashboard())
      else
      -> {
        val pendingTests = db.getPendingTests(user.username)
        call.respond(FreeMarkerContent(
                "updatetestresult.ftl",
                mapOf(
                        "success" to it.success,
                        "activeIndex" to it.activeIndex,
                        "error" to it.error,
                        "tests" to pendingTests,
                        "dateNow" to it.dateNow
                )
        ))
      }
    }
  }

  post<UpdateTestResult> {
    val user = getUserFromSession(call.sessions.get<CTSSession>(), db) as? Tester
    if (user == null) return@post call.redirect(Login())
    else {
      val params = call.receive<Parameters>()
      val testId = params["testId"]?.toIntOrNull() ?: throw IllegalArgumentException("Missing argument testId")
      val result = params["testResult"]?.toIntOrNull() ?: throw IllegalArgumentException("Missing argument testResult")
      val dateNow = DateTime.now()
      val updateTestResult = UpdateTestResult()
      try {
        db.updateTestResult(result, dateNow, testId)
        call.redirect(updateTestResult.copy(success = "The test with ID $testId was updated!"))
      } catch (e: Throwable) {
        call.redirect(updateTestResult.copy(error = e.localizedMessage))
      }
    }
  }
}