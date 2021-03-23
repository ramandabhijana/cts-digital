package com.sestikom.ctsdigital.model

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
  ): UserCreation {
    val incorrectCredential = incorrectCredentials(username, password, firstName)
    return when {
      incorrectCredential != null
      -> incorrectCredential
      else
      -> UserCreation(
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
  ): UserCreation {
    val incorrectCredential = incorrectCredentials(username, password, firstName)
    return when {
      incorrectCredential != null
      -> incorrectCredential
      else
      -> UserCreation(
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
}

