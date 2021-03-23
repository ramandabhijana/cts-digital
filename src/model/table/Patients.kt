package com.sestikom.ctsdigital.model.table

import org.jetbrains.exposed.sql.*

object Patients: Table() {
  val username = varchar("username", 20).primaryKey()
  val type = integer("patient_type")
  val symptoms = varchar("symptoms", 512)
  val dob = date("date_of_birth")
}