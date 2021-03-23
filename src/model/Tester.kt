package com.sestikom.ctsdigital.model

import org.joda.time.LocalDate
import org.joda.time.format.*

data class Tester(
        override val center: TestCenter? = null,
        override val position: OfficerPosition? = null,
        override val username: String = "",
        override val password: String = "",
        override val firstName: String = "",
        override val lastName: String = ""
): CenterOfficer() {

  fun createPatient(
          firstName: String,
          lastName: String,
          username: String,
          password: String,
          birthDate: String,
          type: String,
          symptoms: String,
  ): UserCreation {
    val incorrectCredential = incorrectCredentials(username, password, firstName)
    return when {
      incorrectCredential != null
      -> incorrectCredential
      else
      -> {
        try {
          val formatter = DateTimeFormat.forPattern("dd/MM/yyyy")
          val dob = LocalDate.parse(birthDate, formatter)
          val patientType = PatientType.valueFrom(type.toInt())!!
          UserCreation(
                  Patient(username,
                          password,
                          firstName,
                          lastName,
                          dob,
                          patientType,
                          symptoms),
                  null
          )
        } catch (e: Throwable) {
          UserCreation(null, "Invalid date format")
        }
      }
    }
  }

  fun generateTest(): CovidTest =
    CovidTest(
            testDate = LocalDate.now(),
            status = TestStatus.PENDING,
    )

}