package com.sestikom.ctsdigital.model

import org.joda.time.LocalDate

data class CovidTest(
        val id: Int? = null,
        val testDate: LocalDate,
        val result: TestResult? = null,
        val resultDate: LocalDate? = null,
        val status: TestStatus = TestStatus.PENDING,
        val patientUsername: String = "",
        val testerUsername: String = "",
        val kitId: Int = Int.MIN_VALUE,
        val patientName: String = "",
        val patientType: String = "",
        val testerName: String = "",
        val kitName: String = "",
) {
  val resultDateString: String
  get() = resultDate?.toString() ?: "-"

  val resultString: String
  get() = result?.stringValue ?: "-"
}

enum class TestResult {
  POSITIVE, NEGATIVE;
  companion object {
    fun valueFrom(number: Int?): TestResult? {
      return when(number) {
        0 -> POSITIVE
        1 -> NEGATIVE
        else -> null
      }
    }
  }

  val stringValue: String
  get() {
    return when(this) {
      POSITIVE -> "Positive"
      NEGATIVE -> "Negative"
    }
  }
}

enum class TestStatus {
  PENDING, COMPLETE;
  companion object {
    fun valueFrom(number: Int?): TestStatus {
      return if (number == 1) COMPLETE else PENDING
    }
  }

  val stringValue: String
  get() = if (this == COMPLETE) "Complete" else "Pending"

}