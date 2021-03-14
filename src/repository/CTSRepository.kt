package com.sestikom.ctsdigital.repository

import com.sestikom.ctsdigital.model.*
import com.sestikom.ctsdigital.model.mapper.*
import com.sestikom.ctsdigital.model.table.Managers
import com.sestikom.ctsdigital.model.table.Users
import com.sestikom.ctsdigital.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class CTSRepository: Repository {
    override suspend fun getTestCenters(): List<TestCenter> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllTests(): List<CovidTest> {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(username: String, hash: String?): User? {
      TODO("Not yet implemented")
    }

    override suspend fun createManager(manager: TestCenterManager) {
      TODO("Not yet implemented")
    }

    override suspend fun createCenter(center: TestCenter, managerUsername: String) {
        TODO("Not yet implemented")
    }

    override suspend fun createTester(tester: Tester) {
        TODO("Not yet implemented")
    }


}