package com.sestikom.ctsdigital.model

import java.util.*

abstract class CenterOfficer: User() {
  abstract val center: TestCenter?
  abstract val position: OfficerPosition?

  fun generateTestReport(tests: List<CovidTest>): TestReport {
    return TestReport(center = center!!, tests = tests)
  }

  override fun updateProfile(
          firstName: String?,
          lastName: String?,
          extraField: Map<String, String>?
  ): User {
    TODO("Not yet implemented")
  }

  data class TestReport(
          val center: TestCenter,
          val date: String = "${Calendar.getInstance().time}",
          val tests: List<CovidTest>,
  ) {
    val total: String
    get() = "${tests.size}"

    val positiveNumber: String
    get() = tests.filter { it.result == TestResult.POSITIVE }
            .size
            .toString()

    val negativeNumber: String
    get() = tests.filter { it.result == TestResult.NEGATIVE }
            .size
            .toString()
  }
}

enum class OfficerPosition {
  MANAGER, TESTER;
  companion object {
    fun valueFrom(number: Int?): OfficerPosition? {
      return when(number) {
        0 -> MANAGER
        1 -> TESTER
        else -> null
      }
    }
  }
}