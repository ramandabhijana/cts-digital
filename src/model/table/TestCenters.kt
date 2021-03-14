package com.sestikom.ctsdigital.model.table

import org.jetbrains.exposed.sql.*

object TestCenters: Table() {
  val centerId = integer("center_id").primaryKey()
  val name = varchar("center_name", 256)
  val address = varchar("address", 256)
}