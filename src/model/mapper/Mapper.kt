package com.sestikom.ctsdigital.model.mapper

import com.sestikom.ctsdigital.model.*
import com.sestikom.ctsdigital.model.table.*
import com.sestikom.ctsdigital.repository.*
import org.jetbrains.exposed.sql.*
import org.joda.time.LocalDate

fun CTSRepository.toOfficer(row: ResultRow,
                            officerPosition: Int? = null,
                            centerId: Int? = null,
                            centerName: String? = null,
                            centerAddress: String? = null
): User? {
  val center = TestCenter()
  if (centerId != null && centerName != null && centerAddress != null) {
    center.id = centerId
    center.name = centerName
    center.address = centerAddress
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
    else -> null
  }
}

fun CTSRepository.toPatient(
        row: ResultRow,
        dob: LocalDate,
        type: Int,
        symptoms: String
): User = Patient(
        username = row[Users.username],
        password = row[Users.passwordHash],
        firstName = row[Users.fullName].substringBefore(" "),
        lastName = row[Users.fullName].substringAfterLast(" "),
        birthDate = dob,
        type = PatientType.valueFrom(type)!!,
        symptoms = symptoms
)


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
