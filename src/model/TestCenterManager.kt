package com.sestikom.ctsdigital.model

import com.sestikom.ctsdigital.auth.*

data class TestCenterManager(
        override val center: TestCenter? = null,
        override val position: OfficerPosition? = null,
        override val username: String = "",
        override val password: String = "",
        override val firstName: String = "",
        override val lastName: String = ""
): CenterOfficer() {

  fun signup(
          username: String,
          password: String,
          firstName: String,
          lastName: String
  ): OfficerCreation {
    val incorrectCredential = incorrectCredentials(username, password, firstName)
    return when {
      incorrectCredential != null
      -> incorrectCredential
      else
      -> OfficerCreation(
              TestCenterManager(
                      username = username,
                      password = password,
                      firstName = firstName,
                      lastName = lastName
              ),
              null
      )
    }
  }

  fun registerCenter(name: String, address: String): TestCenter? {
    if (name.isBlank() || address.isBlank())
      return null
    return TestCenter(name, address)
  }

  fun recordTester(
          username: String,
          password: String,
          firstName: String,
          lastName: String
  ): OfficerCreation {
    val incorrectCredential = incorrectCredentials(username, password, firstName)
    return when {
      incorrectCredential != null
      -> incorrectCredential
      else
      -> OfficerCreation(
              Tester(
                      position = OfficerPosition.TESTER,
                      username = username,
                      password = password,
                      firstName = firstName,
                      lastName = lastName
              ),
              null
      )
    }
  }

  fun createTestKit(name: String, receivedStock: Int): TestKit {
    return TestKit(name, receivedStock)
  }

  fun createTestKit(kit: TestKit, stock: Int): TestKit {
    val newStock = kit.availableStock + stock
    return TestKit(kit.name, newStock)
  }

  private fun incorrectCredentials(
          username: String,
          password: String,
          firstName: String
  ): OfficerCreation? {
    return when {
      firstName.isEmpty()
      -> OfficerCreation(null, "First name must not be empty")
      !userNameValid(username)
      -> OfficerCreation(null, "Username should consist of digits, letters, dots or underscores")
      username.length < MIN_USER_ID_LENGTH
      -> OfficerCreation(null, "Username should be at least $MIN_USER_ID_LENGTH characters long")
      password.length < MIN_PASSWORD_LENGTH
      -> OfficerCreation(null, "Password should be at least $MIN_PASSWORD_LENGTH characters long")
      else -> null
    }
  }

}

data class OfficerCreation(
        val officer: CenterOfficer?,
        val errorMessage: String?,
)

