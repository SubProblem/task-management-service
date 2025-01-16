package com.subproblem.authorizationserver.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class RegistrationRequestDto(
    val firstname: String,
    val lastname: String,
    val email: String,
    val password: String,
    @JsonProperty("phone_number")
    val phoneNumber: String
)
