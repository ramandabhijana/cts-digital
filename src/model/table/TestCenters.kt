package com.sestikom.ctsdigital.model.table

import org.jetbrains.exposed.dao.*

object TestCenters: IntIdTable() {
  val name = varchar("center_name", 256)
  val address = varchar("address", 256)
}