package com.sestikom.ctsdigital.model

import com.sestikom.ctsdigital.auth.*
import io.ktor.auth.*
import java.io.Serializable

abstract class User: Serializable, Principal {
    abstract val username: String
    abstract val password: String
    abstract val firstName: String
    abstract val lastName: String

    fun login(username: String, password: String): String? {
        return when {
            password.length < MIN_PASSWORD_LENGTH
            -> "Invalid password"
            username.length < MIN_USER_ID_LENGTH || !userNameValid(username)
            -> "Invalid username"
            else
            -> null
        }
    }

    fun logout(username: String): User? {
        TODO("Not yet implemented")
    }

    abstract fun updateProfile(
        firstName: String? = null,
        lastName: String? = null,
        extraField: Map<String, String>? = null
    ): User

    protected fun incorrectCredentials(
            username: String,
            password: String,
            firstName: String
    ): UserCreation? {
        return when {
            firstName.isEmpty()
            -> UserCreation(null, "First name must not be empty")
            !userNameValid(username)
            -> UserCreation(null, "Username should consist of digits, letters, dots or underscores")
            username.length < MIN_USER_ID_LENGTH
            -> UserCreation(null, "Username should be at least $MIN_USER_ID_LENGTH characters long")
            password.length < MIN_PASSWORD_LENGTH
            -> UserCreation(null, "Password should be at least $MIN_PASSWORD_LENGTH characters long")
            else -> null
        }
    }
}

data class UserCreation(
        val user: User?,
        val errorMessage: String?,
)