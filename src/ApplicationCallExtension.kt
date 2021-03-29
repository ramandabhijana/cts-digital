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

fun ApplicationCall.refererHost() = request.header(HttpHeaders.Referrer)?.let { URI.create(it).host }

fun ApplicationCall.securityCode(date: Long, user: User, hashFunction: (String) -> String) =
        hashFunction("$date:${user.username}:${request.host()}:${refererHost()}")

// Uses security code to verify a code passed in
fun ApplicationCall.verifyCode(date: Long, user: User, code: String, hashFunction: (String) -> String) =
        securityCode(date, user, hashFunction) == code &&
                (System.currentTimeMillis() - date).let {
                    it > 0 && it < TimeUnit.MICROSECONDS.convert(2, TimeUnit.HOURS)
                }

@KtorExperimentalLocationsAPI
suspend fun ApplicationCall.redirectUser(user: User) {
  when(user) {
    is Tester
    -> redirect(RecordTest())
    is TestCenterManager
    -> redirect(RecordTester())
    is Patient
    -> redirect(PatientDashboard())
  }
}