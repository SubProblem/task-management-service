package com.subproblem.authorizationserver.service

import com.subproblem.authorizationserver.entity.User
import com.subproblem.authorizationserver.dto.RegistrationRequestDto
import com.subproblem.authorizationserver.exception.UserAlreadyExistsException
import com.subproblem.authorizationserver.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.subproblem.enums.Role
import java.util.UUID

@Service
class RegistrationService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun registerUser(request: RegistrationRequestDto) {

        userRepository.findByEmail(request.email)?.let {
            throw UserAlreadyExistsException(request.email)
        }

        val user = User().apply {
            uuid = UUID.randomUUID().toString()
            firstname = request.firstname
            lastname = request.lastname
            email = request.email
            password = passwordEncoder.encode(request.password)
            phoneNumber = request.phoneNumber
            role = Role.USER
        }

        userRepository.save(user)
    }
}