package com.sestikom.ctsdigital.model.mapper

import com.sestikom.ctsdigital.model.*
import com.sestikom.ctsdigital.model.table.*
import com.sestikom.ctsdigital.repository.*
import org.jetbrains.exposed.sql.*

fun CTSRepository.toUser(row: ResultRow,
                         officerPosition: Int? = null,
                         centerId: Int? = null
): User? {
  val center = TestCenter()
  if (centerId != null) {
    center.id = centerId
  }
  return when(row[Users.userCode]) {
    UserCode.MANAGER.ordinal
    -> TestCenterManager(
            center = center,
            username = row [Users.username],
            password = row[Users.passwordHash],
            firstName = row[Users.fullName].substringBefore(" "),
            lastName = row[Users.fullName].substringAfterLast(" "),
            position = OfficerPosition.valueFrom(officerPosition)
    )
    UserCode.TESTER.ordinal
    -> Tester(
            center = center,
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

fun CTSRepository.toKit(row: ResultRow): TestKit {
  val kit = TestKit(
          name = row[TestKits.name],
          availableStock = row[TestKits.stock]
  )
  kit.id = row[TestKits.id].value
  return kit
}



fun CTSRepository.toCenter(row: ResultRow): TestCenter {
  return TestCenter(
          name = row[TestCenters.name],
          address = row[TestCenters.address]
  )
}
