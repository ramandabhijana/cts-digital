package com.sestikom.ctsdigital.model

abstract class CenterOfficer: User() {
  abstract val center: TestCenter?
  abstract val position: OfficerPosition?

  fun generateTestReport() {
    TODO("Not yet implemented")
  }

  override fun updateProfile(
          firstName: String?,
          lastName: String?,
          extraField: Map<String, String>?
  ): User {
    TODO("Not yet implemented")
  }
}

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