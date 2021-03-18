package com.sestikom.ctsdigital.model

enum class OfficerPosition {
  MANAGER, TESTER;
  companion object {
    fun valueFrom(number: Int?): OfficerPosition? {
      return when(number) {
        0 -> OfficerPosition.MANAGER
        1 -> OfficerPosition.TESTER
        else -> null
      }
    }
  }
}

abstract class CenterOfficer: User() {
  abstract val position: OfficerPosition?

  fun generateTestReport() {
    TODO("Not yet implemented")
  }

  override fun updateProfile(firstName: String, lastName: String, vararg extraField: Map<String, String>): String? {
    TODO("Not yet implemented")
  }
}