package com.sestikom.ctsdigital.model.table

import org.jetbrains.exposed.sql.Table

object Users: Table() {
    val username = varchar("username", 20).primaryKey()
    val fullName = varchar("full_name", 256)
    val passwordHash = varchar("password_hash", 64)
    val userCode = integer("user_code")
}