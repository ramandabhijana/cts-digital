package com.sestikom.ctsdigital

import com.sestikom.ctsdigital.webapp.*
import io.kotest.core.spec.style.*
import io.kotest.matchers.*
import io.kotest.matchers.string.*
import io.ktor.http.*
import io.ktor.server.testing.*

class LogoutModuleTest : StringSpec({
  "Given user has a session When the user press log out Then session is removed and the user redirected to home page" {
    withTestApplication(moduleFunction = { module(testing = true) }) {
      handleRequest(
              method = HttpMethod.Get,
              uri = LOGOUT
      ) {
        addHeader(HttpHeaders.SetCookie, "SESSION=username%3D%2523sabhi180031251%2Ff3dff3fd9b45c7e04f48e14c735abd652d0a9a868037ced3a875849fa5b087c8; Max-Age=1000; Expires=Tue, 06 Apr 2021 14:38:30 GMT; Path=/; HttpOnly; \$-x-enc=URI_ENCODING")
        addHeader(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
      }.apply {
        response.headers["Set-Cookie"] shouldBe null
        response.headers["Location"] shouldBe HOME
      }
    }
  }
})