package com.sestikom.ctsdigital.repository

import com.sestikom.ctsdigital.model.*
import com.sestikom.ctsdigital.model.mapper.*
import com.sestikom.ctsdigital.model.table.*
import com.sestikom.ctsdigital.model.table.CovidTests.kitId
import com.sestikom.ctsdigital.model.table.CovidTests.patientId
import com.sestikom.ctsdigital.model.table.CovidTests.status
import com.sestikom.ctsdigital.model.table.CovidTests.testerId
import com.sestikom.ctsdigital.model.table.Officers.center
import com.sestikom.ctsdigital.model.table.Officers.position
import com.sestikom.ctsdigital.model.table.Officers.username
import com.sestikom.ctsdigital.model.table.Patients.dob
import com.sestikom.ctsdigital.model.table.Patients.symptoms
import com.sestikom.ctsdigital.model.table.Patients.type
import com.sestikom.ctsdigital.model.table.TestKits.stock
import com.sestikom.ctsdigital.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.joda.time.*

class CTSRepository: Repository {
    override suspend fun getTestCenters(): List<TestCenter> =
        dbQuery {
            Officers
                    .innerJoin(Users, { username }, { username })
                    .innerJoin(TestCenters, { center }, { TestCenters.id })
                    .slice(
                            Users.username,
                            TestCenters.id,
                            Users.fullName,
                            TestCenters.name
                    )
                    .select { position eq OfficerPosition.MANAGER.ordinal }
                    .map {
                        val center = TestCenter(
                                name = it[TestCenters.name],
                                address = ""
                        )
                        center.id = it[TestCenters.id].value
                        center.managerNames = arrayOf(it[Users.fullName])
                        return@map center
                    }
        }

    override suspend fun getAllTests(): List<CovidTest> =
        dbQuery {
            CovidTests
                    .innerJoin(Users, { patientId }, { username } )
                    .selectAll()
                    .map {
                        CovidTest(
                                id = it[CovidTests.id].value,
                                testDate = it[CovidTests.testDate].toLocalDate(),
                                patientUsername = it[patientId],
                                patientName = it[Users.fullName],
                                status = TestStatus.valueFrom(it[status]),
                        )
                    }
        }


    override suspend fun getUser(username: String, hash: String?): User? {
        val user = dbQuery {
            Users
                    .select { (Users.username eq username) }
                    .mapNotNull { row ->
                        if (row[Users.userCode] == UserCode.PATIENT.ordinal) {
                            Patients.slice(dob, type, symptoms)
                                    .select { (Patients.username eq username) }
                                    .mapNotNull {
                                        toPatient(
                                                row,
                                                it[dob].toLocalDate(),
                                                it[type],
                                                it[symptoms]
                                        )
                                    }
                                    .singleOrNull()
                        } else {
                            Officers
                                    .leftJoin(TestCenters, { center }, { id })
                                    .select { (Officers.username eq username) }
                                    .mapNotNull {
                                       toOfficer(
                                               row,
                                               it[position],
                                               it[center],
                                               it[TestCenters.name],
                                               it[TestCenters.address]
                                       )
                                    }
                                    .singleOrNull()
                        }
                    }
                    .singleOrNull()
        }
        return when {
            user == null -> null
            hash == null -> user
            user.password == hash -> user
            else -> null
        }
    }

    override suspend fun createManager(manager: TestCenterManager): Boolean {
        if (getUser(manager.username) != null) return false
        dbQuery {
            Users.insert {
                it[username] = manager.username
                it[fullName] = "${manager.firstName} ${manager.lastName}"
                it[passwordHash] = manager.password
                it[userCode] = UserCode.MANAGER.ordinal
            }
            Officers.insert {
                it[username] = manager.username
                it[position] = null
                it[center] = null
            }
        }
        return true
    }

    override suspend fun createCenter(center: TestCenter, managerUsername: String) {
        dbQuery {
            val centerId = TestCenters.insertAndGetId {
                it[name] = center.name
                it[address] = center.address
            }
            Officers.update({ username eq managerUsername }) {
                it[Officers.center] = centerId.value
                it[position] = OfficerPosition.MANAGER.ordinal
            }
            Unit
        }
    }

    override suspend fun createTester(tester: Tester, managerUsername: String) {
        dbQuery {
            val center = Officers
                    .slice(center)
                    .select { (username eq managerUsername) }
                    .mapNotNull { it[center] }
                    .singleOrNull()
            Users.insert {
                it[username] = tester.username
                it[fullName] = "${tester.firstName} ${tester.lastName}"
                it[passwordHash] = tester.password
                it[userCode] = UserCode.TESTER.ordinal
            }
            Officers.insert {
                it[username] = tester.username
                it[position] = tester.position!!.ordinal
                it[Officers.center] = center!!
            }
            Unit
        }

    }

    override suspend fun createTestKit(kit: TestKit, centerId: Int) {
        dbQuery {
            TestKits.insert {
                it[name] = kit.name
                it[stock] = kit.availableStock
                it[TestKits.centerId] = centerId
            }
        }
    }

    override suspend fun getTestKits(centerId: Int): List<TestKit> =
            dbQuery {
                TestKits.select { TestKits.centerId eq centerId }
                        .mapNotNull { toKit(it) }
            }

    override suspend fun updateStock(kitId: Int, newStock: Int) {
        dbQuery {
            TestKits.update({ TestKits.id eq kitId }) {
                it[stock] = newStock
            }
            Unit
        }
    }

    override suspend fun getAllPatients(): List<Patient> =
        dbQuery {
            Users
                    .innerJoin(Patients, { username }, { username })
                    .select { Users.userCode eq UserCode.PATIENT.ordinal }
                    .mapNotNull {
                        toPatient(
                                it,
                                it[dob].toLocalDate(),
                                it[type],
                                it[symptoms]
                        ) as Patient
                    }
        }

    override suspend fun createTest(test: CovidTest,
                                    patientUsername: String,
                                    testerUsername: String,
                                    kitId: Int
    ) {
        dbQuery {
            val previousStock = TestKits
                    .slice(stock)
                    .select { (TestKits.id eq kitId) }
                    .mapNotNull {
                        it[stock]
                    }
                    .single()
            TestKits.update({ TestKits.id eq kitId }) {
                it[stock] = previousStock - 1
            }
            CovidTests.insert {
                it[testDate] = test.testDate.toDateTime(LocalTime.now())
                it[result] = test.result?.ordinal
                it[resultDate] = null
                it[status] = test.status.ordinal
                it[patientId] = patientUsername
                it[testerId] = testerUsername
                it[CovidTests.kitId] = kitId
            }
            Unit
        }
    }

    override suspend fun createPatient(patient: Patient) =
        dbQuery {
            Users.insert {
                it[username] = patient.username
                it[fullName] = "${patient.firstName} ${patient.lastName}"
                it[passwordHash] = patient.password
                it[userCode] = UserCode.PATIENT.ordinal
            }
            Patients.insert {
                it[username] = patient.username
                it[type] = patient.type.ordinal
                it[symptoms] = patient.symptoms
                it[dob] = patient.birthDate.toDateTime(LocalTime.now())
            }
            Unit
        }

    override suspend fun createTest(
            test: CovidTest,
            patientUsername: String,
            patientType: Int,
            symptoms: String,
            testerUsername: String,
            kitId: Int
    ) {
        dbQuery {
            Patients.update({ Patients.username eq patientUsername }) {
                it[type] = patientType
                it[Patients.symptoms] = symptoms
            }
            val previousStock = TestKits
                    .slice(stock)
                    .select { (TestKits.id eq kitId) }
                    .mapNotNull {
                        it[stock]
                    }
                    .single()
            TestKits.update({ TestKits.id eq kitId }) {
                it[stock] = previousStock - 1
            }
            CovidTests.insert {
                it[testDate] = test.testDate.toDateTime(LocalTime.now())
                it[result] = test.result?.ordinal
                it[resultDate] = null
                it[status] = test.status.ordinal
                it[patientId] = patientUsername
                it[testerId] = testerUsername
                it[CovidTests.kitId] = kitId
            }
            Unit
        }
    }

    override suspend fun getPendingTests(testerUsername: String): List<CovidTest> {
        return dbQuery {
            CovidTests
                    .innerJoin(TestKits, { kitId }, { id })
                    .innerJoin(Users, { patientId }, { username } )
                    .select { (status eq TestStatus.PENDING.ordinal) and
                              (testerId eq testerUsername)
                    }
                    .map {
                        CovidTest(
                                id = it[CovidTests.id].value,
                                testDate = it[CovidTests.testDate].toLocalDate(),
                                patientUsername = it[patientId],
                                testerUsername = it[testerId],
                                kitId = it[kitId],
                                patientName = it[Users.fullName],
                                kitName = it[TestKits.name]
                        )
                    }
        }
    }

    override suspend fun updateTestResult(
            result: Int,
            resultDate: DateTime,
            testId: Int
    ) {
        dbQuery {
            CovidTests.update({ CovidTests.id eq testId }) {
                it[CovidTests.result] = result
                it[CovidTests.resultDate] = resultDate
                it[status] = TestStatus.COMPLETE.ordinal
            }
        }
    }

    override suspend fun updatePatientProfile(
            username: String,
            firstName: String?,
            lastName: String?,
            dob: LocalDate?,
            password: String?
    ) {
        dbQuery {
            if (password != null)
                Users.update({Users.username eq username}) {
                    it[passwordHash] = password
                }
            if (dob != null)
                Patients.update({ Patients.username eq username }) {
                    it[Patients.dob] = dob.toDateTime(LocalTime.now())
                }
            val previousName = Users
                    .slice(Users.fullName)
                    .select { Users.username eq username }
                    .map { it[Users.fullName] }
                    .single()
            if (previousName != "$firstName $lastName")
                Users.update({ Users.username eq username }) {
                    it[fullName] = "$firstName $lastName"
                }
        }
    }

    override suspend fun getTestsHistoryByCenterId(centerId: Int): List<CovidTest> =
        dbQuery {
            CovidTests
                    .innerJoin(TestKits, { kitId }, { TestKits.id })
                    .innerJoin(Users, { patientId }, { username } )
                    .innerJoin(Patients, { patientId }, { username })
                    .innerJoin(Officers, { testerId }, { username })
                    .select { (center eq centerId) }
                    .map {
                        CovidTest(
                                id = it[CovidTests.id].value,
                                testDate = it[CovidTests.testDate].toLocalDate(),
                                result = TestResult.valueFrom(it[CovidTests.result]),
                                resultDate = it[CovidTests.resultDate]?.toLocalDate(),
                                status = TestStatus.valueFrom(it[status]),
                                patientUsername = it[patientId],
                                testerUsername = it[testerId],
                                kitId = it[kitId],
                                patientName = it[Users.fullName],
                                patientType = PatientType.valueFrom(it[type])!!.stringValue,
                                kitName = it[TestKits.name]
                        )
                    }
        }
}