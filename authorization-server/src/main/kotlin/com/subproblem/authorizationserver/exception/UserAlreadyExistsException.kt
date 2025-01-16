package com.subproblem.authorizationserver.exception

import org.springframework.http.HttpStatus

class UserAlreadyExistsException(message: String) : BusinessException("User with email $message already exists", HttpStatus.BAD_REQUEST) {
}