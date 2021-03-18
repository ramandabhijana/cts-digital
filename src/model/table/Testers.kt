package com.sestikom.ctsdigital.model.table

import org.jetbrains.exposed.sql.*

object Testers: Table() {
    val username = varchar("username", 20).primaryKey()
}