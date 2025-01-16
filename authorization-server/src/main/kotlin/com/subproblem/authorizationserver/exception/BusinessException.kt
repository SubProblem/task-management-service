package com.subproblem.authorizationserver.exception

import org.springframework.http.HttpStatus

open class BusinessException(message: String, val status: HttpStatus) : RuntimeException(message) {
}