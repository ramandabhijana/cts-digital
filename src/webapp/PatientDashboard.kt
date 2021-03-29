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

const val PATIENT_DASHBOARD = "/patient/dashboard"

@KtorExperimentalLocationsAPI
@Location(PATIENT_DASHBOARD)
data class PatientDashboard(
        val activeIndex: String = "0",
)

@KtorExperimentalLocationsAPI
fun Route.patientDashboard(db: Repository) {
  get<PatientDashboard> {
    val user = getUserFromSession(call.sessions.get<CTSSession>(), db)
    when (user) {
      null
      -> call.respond(FreeMarkerContent("login.ftl", null)
      )
      !is Patient -> call.respondText("No access")
      else -> call.respond(FreeMarkerContent(
              "patientdashboard.ftl",
              mapOf(
                      "activeIndex" to it.activeIndex,
                      "patient" to user,
              ))
      )
    }
  }
}