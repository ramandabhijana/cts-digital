package com.sestikom.ctsdigital

import com.sestikom.ctsdigital.model.*
import com.sestikom.ctsdigital.webapp.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import java.net.URI
import java.util.concurrent.*

// This method calls respondRedirect based on a location that is passed in
@KtorExperimentalLocationsAPI
suspend fun ApplicationCall.redirect(location: Any) {
    respondRedirect(application.locations.href(location))
}

@KtorExperimentalLocationsAPI
suspend fun ApplicationCall.redirectUser(user: User) {
  when(user) {
    is TestCenterManager
    -> {
      if (user.position == null) redirect(CenterRegistration())
      else redirect(ManagerDashboard())
    }
    is Tester
    -> redirect(TesterDashboard())

    is Patient
    -> redirect(PatientDashboard())
  }
}