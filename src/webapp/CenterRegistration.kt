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

const val REGISTRATION = "/manager/regcenter"

@KtorExperimentalLocationsAPI
@Location(REGISTRATION)
data class CenterRegistration(
        val error: String = ""
)

@KtorExperimentalLocationsAPI
fun Route.registerCenter(db: Repository, hashFunction: (String) -> String) {
  get<CenterRegistration> {
    val user = call.sessions.get<CTSSession>()?.let {
      db.getUser(it.username)
    } ?: return@get call.respond(FreeMarkerContent("index.ftl", null))
    if (user as? TestCenterManager != null)
      call.respond(FreeMarkerContent("regcenterdummy.ftl", null))
    else
      call.respondText("Manager only")
  }

  post<CenterRegistration> {
    val user = call.sessions.get<CTSSession>()?.let {
      db.getUser(it.username)
    } as? TestCenterManager ?: return@post call.redirect(Login())
    val params = call.receiveParameters()
    val centerName = "Denpasar Health Center"
    val address = "Renon Boulevard"
    user.registerCenter(centerName, address)?.let {
      db.createCenter(it, user.username)
      call.respondText("New Test Center has been registered")
    } ?: return@post call.respondText("Please fill in all fields")

//    val date = params["date"]?.toLongOrNull() ?: return@post call.redirect(it)
//    val code = params["code"] ?: return@post call.redirect(it)


  }
}

