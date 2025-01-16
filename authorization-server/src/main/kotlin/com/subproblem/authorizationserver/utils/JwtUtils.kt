package com.subproblem.authorizationserver.utils

import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component

@Component
class JwtUtils {
    fun extractUserId(jwt: Jwt): Int =
        jwt.claims["id"]?.toString()?.toInt()
            ?: throw IllegalStateException("User ID claim not found in token")

}