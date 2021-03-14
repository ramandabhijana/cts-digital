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
    call.respond(FreeMarkerContent("index.ftl", null))
  }
}