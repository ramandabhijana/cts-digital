package com.sestikom.ctsdigital.model

import java.time.*

data class CovidTest(
        val id: String,
        val testDate: LocalDate,
        val result: TestResult,
        val resultDate: LocalDate,
        val status: TestStatus,
        val patientName: String = ""
)

enum class TestResult {
  POSITIVE, NEGATIVE
}

enum class TestStatus {
  PENDING, COMPLETE
}