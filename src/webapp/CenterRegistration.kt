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
    } ?: return@get call.respond(FreeMarkerContent("logindummy.ftl", null))
    when {
      user as? TestCenterManager == null
      -> call.respondText("Manager only")
      user.position != null
      -> call.respondText("Should go to manager's dashboard page")
      else
      -> {
        val date = System.currentTimeMillis()
        val code = call.securityCode(date, user, hashFunction)
        call.respond(
                FreeMarkerContent(
                        "regcenter.ftl",
                        mapOf(
                                "user" to user,
                                "date" to date,
                                "code" to code,
                                "error" to it.error
                        ),
                        user.username
                )
        )
      }
    }
  }

  post<CenterRegistration> {
    val user = call.sessions.get<CTSSession>()?.let {
      db.getUser(it.username)
    } as? TestCenterManager ?: return@post call.redirect(Login())
    val params = call.receiveParameters()
    val name = params["centerName"] ?: return@post call.redirect(it)
    val address = params["centerAddress"] ?: return@post call.redirect(it)
    val date = params["date"]?.toLongOrNull() ?: return@post call.redirect(it)
    val code = params["code"] ?: return@post call.redirect(it)
//    if (!call.verifyCode(date, user, code, hashFunction))
//      call.redirect(Login(error = "Couldn't verify security code"))
    val regisError = CenterRegistration()
    user.registerCenter(name, address)?.let {
      db.createCenter(it, user.username)
      call.respondText("New Test Center has been registered")
    } ?: return@post call.redirect(
            regisError.copy(error = "Be sure all fields are filled in")
    )
  }
}

