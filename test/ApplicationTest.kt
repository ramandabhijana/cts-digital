package com.sestikom.ctsdigital

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.string.shouldContain
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.server.testing.*

import io.kotest.matchers.string.shouldContain
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.server.testing.*
import org.junit.Test
import kotlin.test.assertEquals

//class ApplicationTest {
//    @KtorExperimentalLocationsAPI
//    @Test
//    fun testRoot() {
//        withTestApplication({ module(testing = true) }) {
//            handleRequest(HttpMethod.Get, "/").apply {
//                assertEquals(HttpStatusCode.OK, response.status())
//                response.content shouldContain "Let's connect"
//            }
//        }
//    }
//}


//@KtorExperimentalLocationsAPI
//class CTSTest: StringSpec({
//    "g" {
//        withTestApplication(moduleFunction = { module(testing = true) }) {
//            handleRequest(HttpMethod.Get, "/").apply {
//                response.content shouldContain "Lets konek"
//            }
//        }
//    }
//})
