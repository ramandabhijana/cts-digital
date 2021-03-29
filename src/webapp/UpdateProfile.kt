package com.sestikom.ctsdigital.webapp

import com.sestikom.ctsdigital.*
import com.sestikom.ctsdigital.model.*
import com.sestikom.ctsdigital.model.session.*
import com.sestikom.ctsdigital.repository.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import java.lang.IllegalArgumentException

const val UPDATE_PROFILE = "/user/updateprofile"

@KtorExperimentalLocationsAPI
@Location(UPDATE_PROFILE)
class UpdateProfile

@KtorExperimentalLocationsAPI
fun Route.updateProfile(db: Repository, hashFunction: (String) -> String) {
  post<UpdateProfile>{
    val user = getUserFromSession(call.sessions.get<CTSSession>(), db)
    if (user == null) call.redirect(Login())
    else {
      val params = call.receive<Parameters>()
      val firstName = params["firstName"] ?: throw IllegalArgumentException("Missing parameter firstName")
      val lastName = params["lastName"] ?: throw IllegalArgumentException("Missing parameter lastName")
      val password = params["password"] ?: throw IllegalArgumentException("Missing parameter password")
      if (user is Patient) {
        val dob = params["dob"] ?: throw IllegalArgumentException("Missing parameter dob")
        val extraField = mutableMapOf<String, String>()
        if (dob != user.dobString)
          extraField["dob"] = dob
        if (password.isNotEmpty())
          extraField["password"] = hashFunction(password)
        user.updateProfile(firstName, lastName, extraField)
        try {
          db.updatePatientProfile(
                  user.username,
                  user.firstName,
                  user.lastName,
                  if (extraField["dob"] == null) null else user.birthDate,
                  if (extraField["password"] == null) null else user.password
          )
          call.redirect(PatientDashboard())
        } catch (e: Throwable) {
          call.respondText(e.toString())
        }
      } else {

      }
    }
  }
}


