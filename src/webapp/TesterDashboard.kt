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

const val TESTER_DASHBOARD = "/tester/dashboard"

@KtorExperimentalLocationsAPI
@Location(TESTER_DASHBOARD)
data class TesterDashboard(
        val activeIndex: String = "0",
)

@KtorExperimentalLocationsAPI
fun Route.testerDashboard(db: Repository) {
  get<TesterDashboard> {
    val user = getUserFromSession(call.sessions.get<CTSSession>(), db)
    when (user) {
      null
      -> call.redirect(Login(error = SESSION_TIMED_OUT))
      !is Tester
      -> call.respondText("No access")
      else
      -> {
        val tests = db.getAllTestsPerformedBy(user.username)
        val numOfPendingTest = tests.filter {
          it["status"] == "0"
        }.count()
        val numOfCompletedTest = tests.filter {
          it["status"] == "1"
        }.count()
        call.respond(FreeMarkerContent(
                "testerdashboard.ftl",
                mapOf(
                        "activeIndex" to it.activeIndex,
                        "user" to user,
                        "tests" to tests,
                        "pending" to numOfPendingTest,
                        "completed" to numOfCompletedTest
                ))
        )
      }
    }
  }
}