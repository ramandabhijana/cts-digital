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
        firstName: String,
        lastName: String,
        vararg extraField: Map<String, String>
    ): String?
}