package com.sestikom.ctsdigital.repository

import com.sestikom.ctsdigital.model.*
import com.sestikom.ctsdigital.model.mapper.*
import com.sestikom.ctsdigital.model.table.*
import com.sestikom.ctsdigital.model.table.Officers.center
import com.sestikom.ctsdigital.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*

class CTSRepository: Repository {
    override suspend fun getTestCenters(): List<TestCenter> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllTests(): List<CovidTest> {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(username: String, hash: String?): User? {
        val user = dbQuery {
            Users
                    .select { (Users.username eq username) }
                    .mapNotNull { toUser(it) }
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
                it[position] = manager.position!!.ordinal
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
            Officers.update({ Officers.username eq managerUsername }) {
                it[Officers.center] = centerId.value
            }
            Unit
        }
    }

    override suspend fun createTester(tester: Tester) {
        TODO("Not yet implemented")
    }


}