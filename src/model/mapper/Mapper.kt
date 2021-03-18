package com.sestikom.ctsdigital.model.mapper

import com.sestikom.ctsdigital.model.*
import com.sestikom.ctsdigital.model.table.*
import com.sestikom.ctsdigital.repository.*
import org.jetbrains.exposed.sql.*

fun CTSRepository.toUser(row: ResultRow, officerPosition: Int? = null): User? {
  return when(row[Users.userCode]) {
    UserCode.MANAGER.ordinal
    -> TestCenterManager(
            username = row[Users.username],
            password = row[Users.passwordHash],
            firstName = row[Users.fullName].substringBefore(" "),
            lastName = row[Users.fullName].substringAfterLast(" "),
            position = OfficerPosition.valueFrom(officerPosition)
    )
    UserCode.TESTER.ordinal
    -> Tester(
            username = row[Users.username],
            password = row[Users.passwordHash],
            firstName = row[Users.fullName].substringBefore(" "),
            lastName = row[Users.fullName].substringAfterLast(" "),
            position = OfficerPosition.valueFrom(officerPosition)
    )
    UserCode.PATIENT.ordinal
    -> Patient(
            username = row[Users.username],
            password = row[Users.passwordHash],
            firstName = row[Users.fullName].substringBefore(" "),
            lastName = row[Users.fullName].substringAfterLast(" ")
    )
    else -> null
  }
}

fun CTSRepository.toCenter(row: ResultRow): TestCenter {
  return TestCenter(
          name = row[TestCenters.name],
          address = row[TestCenters.address]
  )
}
