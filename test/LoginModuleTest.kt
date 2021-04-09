package com.sestikom.ctsdigital

import com.sestikom.ctsdigital.webapp.*
import io.kotest.core.spec.style.*
import io.kotest.matchers.*
import io.kotest.matchers.string.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.server.testing.*

/*
@KtorExperimentalLocationsAPI
class LoginModuleTest: StringSpec({

  "Given a valid login credential of center manager that has registered at a test center When the user login Then the user redirected to record tester page" {
    withTestApplication(moduleFunction = { module(testing = true) }) {
      val username = "abhi180031251"
      handleRequest(
              method = HttpMethod.Post,
              uri = LOGIN
      ) {
        addHeader(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
        setBody(
                listOf("username" to username, "password" to "password123")
                        .formUrlEncode()
        )
      }.apply {
//        error(response.headers["Set-Cookie"].toString())
        response.status()?.value shouldBe 302
        response.headers["Location"] shouldContain RECORD_TESTER
        response.headers["Set-Cookie"] shouldContain username
      }
    }
  }

  "Given a valid login credential of center manager that has not registered at a test center When the user login Then the user redirected to center registration page" {
    withTestApplication(moduleFunction = { module(testing = true) }) {
      val username = "userTest124"
      handleRequest(
              method = HttpMethod.Post,
              uri = LOGIN
      ) {
        addHeader(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
        setBody(
                listOf("username" to username, "password" to "password123")
                        .formUrlEncode()
        )
      }.apply {
        response.status()?.value shouldBe 302
        response.headers["Location"] shouldContain REGISTRATION
        response.headers["Set-Cookie"] shouldContain username
      }
    }
  }

  "Given a valid login credential of tester When the user login Then the user redirected to record test page" {
    withTestApplication(moduleFunction = { module(testing = true) }) {
      val username = "ramanda100"
      handleRequest(
              method = HttpMethod.Post,
              uri = LOGIN
      ) {
        addHeader(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
        setBody(
                listOf("username" to username, "password" to "password123")
                        .formUrlEncode()
        )
      }.apply {
//        error(response.headers["Set-Cookie"].toString())
        response.status()?.value shouldBe 302
        response.headers["Location"] shouldContain RECORD_TEST
        response.headers["Set-Cookie"] shouldContain username
      }
    }
  }

  "Given a log in credentials have no match When a user log in Then related error should be appended in the header" {
    withTestApplication(moduleFunction = { module(testing = true) }) {
      handleRequest(
              method = HttpMethod.Post,
              uri = LOGIN
      ) {
        addHeader(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
        setBody(
                listOf("username" to "ramanda100", "password" to "password789")
                        .formUrlEncode()
        )
      }.apply {
        response.headers["Location"] shouldContain "error=Username+and+Password+do+not+match"
      }
    }
  }

  "Given a password is less than minimum required When a user log in Then related error should be appended in the header" {
    withTestApplication(moduleFunction = { module(testing = true) }) {
      handleRequest(
              method = HttpMethod.Post,
              uri = LOGIN
      ) {
        addHeader(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
        setBody(shortPasswordBody)
      }.apply {
        response.headers["Location"] shouldContain "error=Invalid+password"
      }
    }
  }

  "Given a username is less than 4 characters long When a user log in Then related error should be appended in the header" {
    withTestApplication(moduleFunction = { module(testing = true) }) {
      handleRequest(
              method = HttpMethod.Post,
              uri = LOGIN
      ) {
        addHeader(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
        setBody(shortUsernameBody)
      }.apply {
        response.headers["Location"] shouldContain "error=Invalid+username"
      }
    }
  }


})

 */
