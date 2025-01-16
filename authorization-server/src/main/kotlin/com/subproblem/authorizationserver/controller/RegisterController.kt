package com.subproblem.authorizationserver.controller

import com.subproblem.authorizationserver.dto.RegistrationRequestDto
import com.subproblem.authorizationserver.service.RegistrationService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.subproblem.Constant.AuthorizationClassConstants.ApiEndpoints.REGISTER


@RestController
@RequestMapping(REGISTER)
class RegisterController(
    private val registrationService: RegistrationService
) {

    @PostMapping
    fun registerUser(@RequestBody registrationRequest: RegistrationRequestDto) : ResponseEntity<HttpStatusCode> =
        registrationService.registerUser(registrationRequest).let {
            ResponseEntity.status(HttpStatus.CREATED)
                .build()
        }
}