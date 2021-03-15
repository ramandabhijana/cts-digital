package com.sestikom.ctsdigital.webapp

import com.sestikom.ctsdigital.model.session.CTSSession
import com.sestikom.ctsdigital.redirect
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.routing.*
import io.ktor.sessions.*

const val LOGOUT = "/logout"

@KtorExperimentalLocationsAPI
@Location(LOGOUT)
class Logout


@KtorExperimentalLocationsAPI
fun Route.logout() {
    get<Logout> {
        // clear the sessions
        call.sessions.clear<CTSSession>()
        call.redirect(Home())
    }
}
