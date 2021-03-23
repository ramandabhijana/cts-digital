package com.sestikom.ctsdigital.model

import org.joda.time.LocalDate

data class CovidTest(
        val id: Int? = null,
        val testDate: LocalDate,
        val result: TestResult? = null,
        val resultDate: LocalDate? = null,
        val status: TestStatus,
        val patientName: String = ""
)

enum class TestResult {
  POSITIVE, NEGATIVE
}

enum class TestStatus {
  PENDING, COMPLETE
}