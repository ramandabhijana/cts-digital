package com.sestikom.ctsdigital.repository

import com.sestikom.ctsdigital.model.*
import com.sestikom.ctsdigital.model.mapper.*
import com.sestikom.ctsdigital.model.table.*
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

    override suspend fun getAllTests(): List<CovidTest> {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(username: String, hash: String?): User? {
        val user = dbQuery {
            Users
                    .select { (Users.username eq username) }
                    .mapNotNull mapNotNullParent@ { row ->
                        if (row[Users.userCode] == UserCode.PATIENT.ordinal) {
                            return@mapNotNullParent toUser(row)
                        } else {
                            Officers.slice(position, center)
                                    .select { (Officers.username eq username) }
                                    .mapNotNull {
                                       toUser(row, it[position], it[position])
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

    override suspend fun createManager(manager: TestCenterManager) {
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
            Managers.insert {
                it[username] = manager.username
            }
            Unit
        }
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
            Testers.insert {
                it[username] = tester.username
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
                TestKits.selectAll()
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

}