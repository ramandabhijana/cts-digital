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

const val MANAGE_KIT = "/manager/managekit"

@KtorExperimentalLocationsAPI
@Location(MANAGE_KIT)
data class ManageKit(
        val activeIndex: String = "2",
        val error: String? = null,
        val success: String? = null,
        val kits: List<TestKit> = listOf()
)

@KtorExperimentalLocationsAPI
fun Route.manageKit(db: Repository) {
  get<ManageKit> {
    val user = getUserFromSession(call.sessions.get<CTSSession>(), db)
    when {
      user == null
      -> call.redirect(Login(error = SESSION_TIMED_OUT))
      user !is TestCenterManager
      -> call.respondText("No access")
      user.position == null
      -> call.redirect(CenterRegistration())
      else
      -> {
        val testKits = db.getTestKits(user.center?.id!!)
        call.respond(FreeMarkerContent(
                "managekit.ftl",
                mapOf(
                        "success" to it.success,
                        "activeIndex" to it.activeIndex,
                        "error" to it.error,
                        "kits" to testKits
                )
        ))
      }
    }
  }

  post<ManageKit> {
    val user = getUserFromSession(call.sessions.get<CTSSession>(), db)
    if (user == null) call.redirect(Login())
    else {
      val params = call.receive<Parameters>()
      val manageKit = ManageKit()
      val id = params["selectedtestkitid"]
      val name = params["kitName"]
      val manager = (user as TestCenterManager)
      try {
        when {
          (id == null && name == null)
          -> return@post call.redirect(it)
          id == null
          -> {
            val receivedStock = params["stock"]?.toInt()?.let {
              if (it <= 0) return@post call.redirect(
                      manageKit.copy(error = "Stock must not be 0 or less")
              )
              return@let it
            } ?: return@post call.redirect(
                    manageKit.copy(error = "Incorrect input for stock")
            )
            val kit = manager.createTestKit(name!!, receivedStock)
            db.createTestKit(kit, manager.center?.id!!)
            call.redirect(manageKit.copy(success = "A new test kit was created"))
          }
          name == null
          -> {
            val newStock = params["newstock"]?.toInt()?.let {
              if (it <= 0) return@post call.redirect(
                      manageKit.copy(error = "Stock must not be 0 or less")
              )
              return@let it
            } ?: return@post call.redirect(
                    manageKit.copy(error = "Incorrect input for stock")
            )
            val testKits = db.getTestKits(user.center?.id!!)
            val kit = testKits.single { kit -> kit.id == id.toInt() }
            val adjustedKit = manager.createTestKit(kit, newStock)
            db.updateStock(id.toInt(), adjustedKit.availableStock)
            call.redirect(manageKit.copy(success = "Test kit's stock with ID '$id' was updated"))
          }
        }
      } catch (e: Throwable) {
        call.respondText(e.toString())
      }
    }
  }
}