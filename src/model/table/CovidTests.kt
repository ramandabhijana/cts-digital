package com.sestikom.ctsdigital.model.table

import org.jetbrains.exposed.dao.*

object CovidTests: IntIdTable() {
  val testDate = date("test_date")
  val result = integer("result_code").nullable()
  val resultDate = date("result_date").nullable()
  val status = integer("status")
  val patientId = varchar("patient_id", 20)
  val testerId = varchar("tester_id", 20)
  val kitId = integer("kit_id")
}