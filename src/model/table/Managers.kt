package com.sestikom.ctsdigital.model.table

import org.jetbrains.exposed.sql.Table

object Managers: Table() {
    val username = varchar("username", 20).primaryKey()
}