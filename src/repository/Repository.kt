package com.sestikom.ctsdigital.repository

import com.sestikom.ctsdigital.model.*

interface Repository {
    suspend fun getTestCenters(): List<TestCenter>
    suspend fun getAllTests(): List<CovidTest>
    suspend fun getUser(username: String, hash: String? = null): User?
    suspend fun createManager(manager: TestCenterManager)
    suspend fun createCenter(center: TestCenter, managerUsername: String)
    suspend fun createTester(tester: Tester, managerUsername: String)
    suspend fun createTestKit(kit: TestKit, centerId: Int)
    suspend fun getTestKits(centerId: Int): List<TestKit>
    suspend fun updateStock(kitId: Int, newStock: Int)
    suspend fun getAllPatients(): List<Patient>
    suspend fun createTest(test: CovidTest,
                           patientUsername: String,
                           testerUsername: String,
                           kitId: Int)
    suspend fun createTest(test: CovidTest,
                           patientUsername: String,
                           patientType: Int,
                           symptoms: String,
                           testerUsername: String,
                           kitId: Int)
    suspend fun createPatient(patient: Patient)
}