package com.subproblem.authorizationserver.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    companion object {
        private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    }

    @ExceptionHandler(BusinessException::class)
    fun customExceptionHandler(exception: BusinessException): ResponseEntity<String?> =
        ResponseEntity.status(exception.status)
            .body(exception.message).also {
                logger.info(exception.message)
            }

    @ExceptionHandler(Exception::class)
    fun unhandledExceptionHandler(exception: Exception): ResponseEntity<String?> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("something went wrong").also {
                logger.info(exception.message)
            }
}