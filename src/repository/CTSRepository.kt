package com.sestikom.ctsdigital.repository

import com.sestikom.ctsdigital.model.*
import com.sestikom.ctsdigital.model.mapper.*
import com.sestikom.ctsdigital.model.table.*
import com.sestikom.ctsdigital.model.table.Officers.center
import com.sestikom.ctsdigital.model.table.Officers.position
import com.sestikom.ctsdigital.model.table.Officers.username
import com.sestikom.ctsdigital.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*

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

}