package com.sestikom.ctsdigital.model

import org.joda.time.*
import org.joda.time.format.*

data class Patient(
        override val username: String = "",
        override var password: String = "",
        override var firstName: String = "",
        override var lastName: String = "",
        var birthDate: LocalDate = LocalDate.now(),
        val type: PatientType = PatientType.SUSPECTED,
        val symptoms: String = ""
): User() {
  fun viewTestHistory() {
    TODO("Not yet implemented")
  }

  val dobString: String
  get() {
    val formatter = DateTimeFormat.forPattern("dd/MM/yyyy")
    return birthDate.toString(formatter)
  }

  val typeString: String
  get() = type.stringValue

  override fun updateProfile(
          firstName: String?,
          lastName: String?,
          extraField: Map<String, String>?
  ): User {
    firstName?.let { this.firstName = it }
    lastName?.let { this.lastName = it }
    extraField?.let {
      it["dob"]?.let { dob ->
        val formatter = DateTimeFormat.forPattern("dd/MM/yyyy")
        this.birthDate = LocalDate.parse(dob, formatter)
      }
      it["password"]?.let { this.password = it }
    }
    return this
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

  val stringValue: String
  get() {
    return when(this) {
      RETURNEE -> "Returnee"
      QUARANTINED -> "Quaratined"
      CLOSE_CONTACT -> "Close Contact"
      INFECTED -> "Infected"
      SUSPECTED -> "Suspected"
    }
  }
}


