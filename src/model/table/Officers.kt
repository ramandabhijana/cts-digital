package com.sestikom.ctsdigital.model.table

import org.jetbrains.exposed.sql.*

object Officers: Table() {
  val username = varchar("username", 20).primaryKey()
  val position = integer("position").nullable()
  val center = integer("center").nullable()
}