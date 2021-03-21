package com.sestikom.ctsdigital.model

data class Tester(
        override val center: TestCenter? = null,
        override val position: OfficerPosition? = null,
        override val username: String = "",
        override val password: String = "",
        override val firstName: String = "",
        override val lastName: String = ""
): CenterOfficer()