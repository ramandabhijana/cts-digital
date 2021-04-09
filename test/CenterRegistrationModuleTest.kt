package com.sestikom.ctsdigital

import com.sestikom.ctsdigital.model.*
import com.sestikom.ctsdigital.model.session.*
import com.sestikom.ctsdigital.repository.*
import com.sestikom.ctsdigital.webapp.*
import io.kotest.core.spec.style.*
import io.kotest.matchers.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.server.testing.*
import io.ktor.sessions.*
import io.mockk.*

@KtorExperimentalLocationsAPI
class CenterRegistrationModuleTest : StringSpec({

  "Given user is not an officer manager When accessing route Then Disallow access" {
    withTestApplication(moduleFunction = { module(testing = true) }) {
      val session = mockk<CTSSession>()
      val repository = mockk<Repository>()
      coEvery { getUserFromSession(session, repository) } returns TestCenterManager(username = "ramanda100")
      cookiesSession {
        handleRequest(
                method = HttpMethod.Get,
                uri = REGISTRATION
        ) {

//          addHeader(HttpHeaders.SetCookie, "SESSION=username%3D%2523sramanda100%2Fe4cdf054e7f562239dd715c711dc92f364bece5f787324030970538e9977f3ad; Max-Age=1000; Expires=Tue, 30 Apr 2021 15:30:06 GMT; Path=/; HttpOnly; \$x-enc=URI_ENCODING")
        }.apply {
          error(response.headers.allValues())
//          error(sessions)
//          response.content shouldBe "Manager only"
        }
      }
    }
  }

  "Given manager has been registered at center When accessing route Then redirect to record tester page" {
    withTestApplication(moduleFunction = { module(testing = true) }) {

      handleRequest(
              method = HttpMethod.Get,
              uri = REGISTRATION
      ) {
        addHeader(HttpHeaders.SetCookie, "SESSION=username%3D%2523sramanda100%2Fe4cdf054e7f562239dd715c711dc92f364bece5f787324030970538e9977f3ad; Max-Age=1000; Expires=Tue, 30 Apr 2021 15:30:06 GMT; Path=/; HttpOnly; \$x-enc=URI_ENCODING")

//        addHeader("Set-Cookie", "SESSION=username%3D%2523sabhi180031251%2Ff3dff3fd9b45c7e04f48e14c735abd652d0a9a868037ced3a875849fa5b087c8; Max-Age=1000; Expires=Tue, 06 Apr 2021 15:38:29 GMT; Path=/; HttpOnly; \$x-enc=URI_ENCODING")
//        addHeader(HttpHeaders.SetCookie, "SESSION=username%3D%2523sabhi180031251%2Ff3dff3fd9b45c7e04f48e14c735abd652d0a9a868037ced3a875849fa5b087c8; Max-Age=1000; Expires=Tue, 06 Apr 2021 15:38:29 GMT; Path=/; HttpOnly; \$x-enc=URI_ENCODING")
      }.apply {
//        error(response.headers.allValues())
//        response.headers["Location"] shouldBe RECORD_TESTER
      }
    }
  }

  /*
  "Given valid test center name and address When submitting center registration Then redirected to Record tester" {
    withTestApplication(moduleFunction = { module(testing = true) }) {
      handleRequest(
              method = HttpMethod.Post,
              uri = REGISTRATION
      ) {

//        addHeader(HttpHeaders.SetCookie, "SESSION=username%3D%2523sabhi180031251%2Ff3dff3fd9b45c7e04f48e14c735abd652d0a9a868037ced3a875849fa5b087c8; Max-Age=1000; Expires=Tue, 06 Apr 2021 14:38:30 GMT; Path=/; HttpOnly; \$-x-enc=URI_ENCODING")
                addHeader(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
        setBody(listOf("centerName" to "Prima Medic", "centerAddress" to "Denpasar Selatan").formUrlEncode())
      }.apply {

//        response.headers["Location"] shouldBe RECORD_TESTER
      }
    }
  }

   */


})