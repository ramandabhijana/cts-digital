package com.sestikom.ctsdigital.model

import org.joda.time.LocalDate

data class Patient(
        override val username: String = "",
        override val password: String = "",
        override val firstName: String = "",
        override val lastName: String = "",
        val birthDate: LocalDate = LocalDate.now(),
        val type: PatientType = PatientType.SUSPECTED,
        val symptoms: String = ""
): User() {
  fun viewTestHistory() {
    TODO("Not yet implemented")
  }

  override fun updateProfile(
          firstName: String,
          lastName: String,
          vararg extraField: Map<String, String>
  ): String? {
    TODO("Not yet implemented")
  }
}

enum class PatientType {
  RETURNEE, QUARANTINED, CLOSE_CONTACT, INFECTED, SUSPECTED;

  companion object {
    fun valueFrom(number: Int): PatientType? {
      return when(number) {
        0 -> RETURNEE
        1 -> QUARANTINED
        2 -> CLOSE_CONTACT
        3 -> INFECTED
        4 -> SUSPECTED
        else -> null
      }
    }
  }
}


