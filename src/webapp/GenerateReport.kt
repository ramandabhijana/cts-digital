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

const val GENERATE_REPORT = "officer/viewreport"

@KtorExperimentalLocationsAPI
@Location(GENERATE_REPORT)
class GenerateReport

@KtorExperimentalLocationsAPI
fun Route.generateReport(db: Repository) {
  get<GenerateReport> {
    val user = getUserFromSession(call.sessions.get<CTSSession>(), db)
    when {
      user == null
      -> call.redirect(Login(error = SESSION_TIMED_OUT))
      user !is CenterOfficer
      -> call.respondText("No access")
      user.center == null
      -> call.redirect(CenterRegistration())
      else -> {
        val tests = db.getTestsHistoryByCenterId(user.center?.id!!)
        val report = user.generateTestReport(tests)
        call.respond(FreeMarkerContent(
                "generatereport.ftl",
                mapOf("report" to report)
        ))
      }
    }
  }
}