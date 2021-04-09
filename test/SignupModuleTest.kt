package com.sestikom.ctsdigital

import com.sestikom.ctsdigital.webapp.*
import io.kotest.core.spec.style.*
import io.kotest.matchers.*
import io.kotest.matchers.string.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.server.testing.*

@KtorExperimentalLocationsAPI
class SignupModuleTest : StringSpec({
    "Given a firstName parameter is empty When a manager submit sign up form Then related error should be appended in the header" {
        withTestApplication(moduleFunction = { module() }) {
            handleRequest(
                method = HttpMethod.Post,
                uri = SIGNUP
            ) {
                addHeader(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
                setBody(emptyNameBody)
            }.apply {
                response.headers["Location"] shouldContain "error=First+name+must+not+be+empty"
            }
        }
    }

    "Given a username doesnt consist any digits, letters, dots or underscore When a manager submit sign up form Then related error should be appended in the header" {
        withTestApplication(moduleFunction = { module() }) {
            handleRequest(
                method = HttpMethod.Post,
                uri = SIGNUP
            ) {
                addHeader(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
                setBody(invalidUsernameBody)
            }.apply {
                response.headers["Location"] shouldContain "error=Username+should+consist+of+digits%2C+letters%2C+dots+or+underscores"
            }
        }
    }

    "Given a password is less than minimum required When a manager submit sign up form Then related error should be appended in the header" {
        withTestApplication(moduleFunction = { module(testing = true) }) {
            handleRequest(
                method = HttpMethod.Post,
                uri = SIGNUP
            ) {
                addHeader(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
                setBody(shortPasswordBody)
            }.apply {
                response.headers["Location"] shouldContain "error=Password+should+be+at+least+6+characters+long"
            }
        }
    }

    "Given a username is less than 4 characters long When a manager submit sign up form Then related error should be appended in the header" {
        withTestApplication(moduleFunction = { module(testing = true) }) {
            handleRequest(
                method = HttpMethod.Post,
                uri = SIGNUP
            ) {
                addHeader(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
                setBody(shortUsernameBody)
            }.apply {
                response.headers["Location"] shouldContain "error=Username+should+be+at+least+4+characters+long"
            }
        }
    }

    "Given a username 'ramanda100' is already taken When a manager submit sign up form Then related error should be appended in the header" {
        withTestApplication(moduleFunction = { module(testing = true) }) {
            handleRequest(
                method = HttpMethod.Post,
                uri = SIGNUP
            ) {
                addHeader(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
                setBody(takenUsernameBody)
            }.apply {
                response.headers["Location"] shouldContain "error=Username+%27ramanda100%27+is+already+taken"
            }
        }
    }

    "Given request body contains valid data When a manager submit sign up form Then session is created, status code is 302, redirected to /manager/regcenter" {
        withTestApplication(moduleFunction = { module(testing = true) }) {
            val username = "userTest124"
            val body = listOf("firstName" to "abhijana", "lastName" to "",
                    "username" to username, "password" to "password123"
            ).formUrlEncode()
            handleRequest(
                    method = HttpMethod.Post,
                    uri = SIGNUP
            ) {
                addHeader(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
                setBody(body)
            }.apply {
                response.status()?.value shouldBe 302
                response.headers["Location"]!!.substringBefore("?error=") shouldBe "/manager/regcenter"
                response.headers["Set-Cookie"] shouldContain username
            }
        }
    }
})

// TEST DATA
private val takenUsernameBody = listOf("firstName" to "abhijana", "lastName" to "",
        "username" to "ramanda100", "password" to "password123"
).formUrlEncode()
val invalidUsernameBody = listOf("firstName" to "abhi", "lastName" to "",
        "username" to "*#&%", "password" to "1234"
).formUrlEncode()
val emptyNameBody = listOf("firstName" to "", "lastName" to "",
        "username" to "abhijana", "password" to "1234"
).formUrlEncode()
val shortUsernameBody = listOf("firstName" to "abhijana", "lastName" to "",
        "username" to "abh", "password" to "password123"
).formUrlEncode()
val shortPasswordBody = listOf("firstName" to "abhijana", "lastName" to "",
        "username" to "abhijana123", "password" to "123"
).formUrlEncode()

