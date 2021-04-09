package com.sestikom.ctsdigital.webapp

import com.sestikom.ctsdigital.repository.*
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*

const val HOME = "/"

@KtorExperimentalLocationsAPI
@Location(HOME)
class Home

@KtorExperimentalLocationsAPI
fun Route.home(db: Repository) {
  get<Home> {
    val centers = db.getTestCenters()
    val tests = db.getAllTests()
    call.respond(FreeMarkerContent(
            "home.ftl",
            mapOf("centers" to centers, "tests" to tests),
    ))
  }
}