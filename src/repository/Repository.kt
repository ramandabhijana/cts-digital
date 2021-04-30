package com.sestikom.ctsdigital.repository

import com.sestikom.ctsdigital.model.*
import org.joda.time.*

interface Repository {
    suspend fun getTestCenters(): List<TestCenter>
    suspend fun getAllTests(): List<CovidTest>
    suspend fun getUser(username: String, hash: String? = null): User?
    suspend fun createManager(manager: TestCenterManager): Boolean
    suspend fun createCenter(center: TestCenter, managerUsername: String)
    suspend fun createTester(tester: Tester, managerUsername: String)
    suspend fun createTestKit(kit: TestKit, centerId: Int)
    suspend fun getTestKits(centerId: Int): List<TestKit>
    suspend fun sumOfTestKitStock(centerId: Int): Int
    suspend fun getAllTesters(centerId: Int): List<Map<String, String>>
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
    suspend fun getPendingTests(testerUsername: String): List<CovidTest>?
    suspend fun updateTestResult(result: Int, resultDate: DateTime, testId: Int)
    suspend fun updatePatientProfile(
            username: String,
            firstName: String? = null,
            lastName: String? = null,
            dob: LocalDate? = null,
            password: String? = null
    )
    suspend fun getAllTestsPerformedBy(testerUsername: String): List<Map<String, String>>
    suspend fun getTestsHistoryByCenterId(centerId: Int): List<CovidTest>
    suspend fun getTestHistory(patientUsername: String): List<Map<String, Any?>>
    suspend fun updateOfficerProfile(
            username: String,
            firstName: String? = null,
            lastName: String? = null,
            password: String? = null
    )
}