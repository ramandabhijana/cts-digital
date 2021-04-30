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
import java.util.*

const val RECORD_TEST = "/tester/recordtest"

@KtorExperimentalLocationsAPI
@Location(RECORD_TEST)
data class RecordTest(
  val activeIndex: String = "1",
  val error: String? = null,
  val success: String? = null,
  val testDetail: String? = null,
  val kits: List<TestKit> = listOf(),
  val patients: List<Patient> = listOf()
)

@KtorExperimentalLocationsAPI
fun Route.recordTest(db: Repository, hashFunction: (String) -> String) {
  get<RecordTest> {
    val user = getUserFromSession(call.sessions.get<CTSSession>(), db)
    when {
      user == null
      -> call.redirect(Login(error = SESSION_TIMED_OUT))
      user !is Tester
      -> call.respondText("No access")
      db.sumOfTestKitStock(user.center?.id!!) == 0
      -> call.redirect(TesterDashboard())
      else
      -> {
        val testKits = db.getTestKits(user.center.id!!)
        val patients = db.getAllPatients()
        call.respond(FreeMarkerContent(
                "recordtest.ftl",
                mapOf(
                        "success" to it.success,
                        "activeIndex" to it.activeIndex,
                        "error" to it.error,
                        "kits" to testKits,
                        "patients" to patients,
                        "testDetail" to it.testDetail,
                )
        ))
      }
    }
  }

  post<RecordTest> {
    fun makeTestDetail(patientName: String,
                       patientUsername: String,
                       testerName: String,
                       kitName: String
    ): String = "An account with username $patientUsername " +
            "has been created for patient named $patientName.\n" +
            "A new COVID-19 test was also created for the patient on " +
            "${Calendar.getInstance().time} by tester named $testerName " +
            "using the '$kitName' test kit."

    val user = getUserFromSession(call.sessions.get<CTSSession>(), db)
    if (user == null) call.redirect(Login())
    else {
      val tester = (user as Tester)
      val params = call.receive<Parameters>()
      val recordTest = RecordTest()
      val kitId = params["selectedTestKitId"] ?: return@post call.redirect(it)
      val type = params["patientType"] ?: return@post call.redirect(it)
      val symptoms = params["symptoms"] ?: return@post call.redirect(it)
      val selectedUsername = params["selectedUsername"]

      // Registered Patient
      if (selectedUsername != null) {
        val test = tester.generateTest()
        try {
          db.createTest(
                  test,
                  selectedUsername,
                  type.toInt(),
                  symptoms,
                  tester.username,
                  kitId.toInt(),
          )
          call.redirect(
                  recordTest.copy(success = "A new test was created!")
          )
        } catch (e: Throwable) {
          call.respondText(e.toString())
        }

      }
      val firstName = params["firstName"] ?: return@post call.redirect(it)
      val lastName = params["lastName"] ?: return@post call.redirect(it)
      val dob = params["dob"] ?: return@post call.redirect(it)
      val username = params["username"] ?: return@post call.redirect(it)
      val password = params["password"] ?: return@post call.redirect(it)
      val response = tester.createPatient(
              firstName,
              lastName,
              username,
              password,
              dob,
              type,
              symptoms
      )
      response.errorMessage?.let {
        call.redirect(recordTest.copy(error = it))
      } ?: run {
        // New Patient
        val patient = response.user as Patient
        val hash = hashFunction(patient.password)
        val test = tester.generateTest()
        try {
          db.createPatient(patient.copy(password = hash))
          db.createTest(
                  test,
                  patient.username,
                  tester.username,
                  kitId.toInt()
          )
          val testKits = db.getTestKits(user.center?.id!!)
          call.redirect(
                  recordTest.copy(
                          success = "A new patient and test were created",
                          testDetail = makeTestDetail(
                                  patient.firstName,
                                  patient.username,
                                  tester.firstName,
                                  testKits.single {
                                            kit -> kit.id == kitId.toInt()
                                          }.name
                          )
                  )
          )
        } catch (e: Throwable) {
          call.redirect(recordTest.copy(error = "Username \'$username\' is already taken"))
        }
      }
    }
  }

}