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

const val MANAGER_DASHBOARD = "/manager/dashboard"

@KtorExperimentalLocationsAPI
@Location(MANAGER_DASHBOARD)
data class ManagerDashboard(
        val activeIndex: String = "0",
)

@KtorExperimentalLocationsAPI
fun Route.managerDashboard(db: Repository) {
  get<ManagerDashboard> {
    val user = getUserFromSession(call.sessions.get<CTSSession>(), db)
    when (user) {
      null
      -> call.redirect(Login(error = SESSION_TIMED_OUT))
      !is TestCenterManager
      -> call.respondText("No access")
      else
      -> {
        val testKits = db.getTestKits(user.center?.id!!)
        val sumOfKitStock = db.sumOfTestKitStock(user.center.id!!)
        val testers = db.getAllTesters(user.center.id!!)
        call.respond(FreeMarkerContent(
                "managerdashboard.ftl",
                mapOf(
                        "activeIndex" to it.activeIndex,
                        "user" to user,
                        "testers" to testers,
                        "kits" to testKits,
                        "stockAvailable" to sumOfKitStock
                        ))
        )
      }
    }
  }
}