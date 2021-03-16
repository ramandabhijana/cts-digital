package com.sestikom.ctsdigital.model

import com.sestikom.ctsdigital.auth.*

data class TestCenterManager(
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
  ): SignupResponse {
    return when {
      password.length < MIN_PASSWORD_LENGTH
      -> SignupResponse(null, "Password should be at least $MIN_PASSWORD_LENGTH characters long")
      username.length < MIN_USER_ID_LENGTH
      -> SignupResponse(null, "Username should be at least $MIN_USER_ID_LENGTH characters long")
      !userNameValid(username)
      -> SignupResponse(null, "Username should consist of digits, letters, dots or underscores")
      firstName.isEmpty()
      -> SignupResponse(null, "First name must not be empty")
      else
      -> SignupResponse(
              TestCenterManager(
                      position = OfficerPosition.MANAGER,
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

}

data class SignupResponse(
        val manager: TestCenterManager?,
        val errorMessage: String?,
)

