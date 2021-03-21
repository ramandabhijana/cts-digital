package com.sestikom.ctsdigital.model.table

import org.jetbrains.exposed.dao.*

object TestKits: IntIdTable() {
  val name = varchar("kit_name", 256)
  val stock = integer("available_stock")
  val centerId = integer("center_id")
}