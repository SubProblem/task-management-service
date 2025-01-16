package com.subproblem.authorizationserver.config

import com.subproblem.authorizationserver.repository.UserRepository
import com.subproblem.authorizationserver.security.CustomUserDetails
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer

@Configuration
class TokenConfiguration(
    private val userRepository: UserRepository
) {
    @Bean
    fun accessTokenCustomizer(): OAuth2TokenCustomizer<JwtEncodingContext> {
        return OAuth2TokenCustomizer { context ->
            if (OAuth2TokenType.ACCESS_TOKEN == context.tokenType) {
                val authentication = context.getPrincipal<Authentication>()
                val userDetails = authentication.principal as? CustomUserDetails

                userDetails?.let { user ->
                    val retrievedUser = userRepository.findByEmail(authentication.name)
                    context.claims.claims { claims ->
                        claims["id"] = retrievedUser?.id
                        claims["email"] = retrievedUser?.email
                        claims["role"] = user.authorities
                    }
                }
            }
        }
    }
}