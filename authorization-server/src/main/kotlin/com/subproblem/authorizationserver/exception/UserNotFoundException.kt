package com.subproblem.authorizationserver.exception

import org.springframework.http.HttpStatus

class UserNotFoundException() : BusinessException("User not found", HttpStatus.NOT_FOUND) {
}