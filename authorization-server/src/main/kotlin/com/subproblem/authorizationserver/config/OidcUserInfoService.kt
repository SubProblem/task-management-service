package com.subproblem.authorizationserver.config

import com.subproblem.authorizationserver.repository.UserRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.oauth2.core.oidc.OidcUserInfo
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationContext
import org.springframework.stereotype.Service

@Service
class OidcUserInfoService(
    private val userRepository: UserRepository
) {

    /**
     * Customizes the UserInfo response to include additional claims.
     */
    fun customizeUserInfo(authenticationContext: OidcUserInfoAuthenticationContext): OidcUserInfo {
        val authentication = authenticationContext.authorization
        val email = authentication.principalName

        val user = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("User not found for email: $email")

        val claims = mutableMapOf<String, Any>()
        claims["sub"] = user.id.toString()
        claims["name"] = "${user.firstname} ${user.lastname}"
        claims["email"] = user.email

        return OidcUserInfo(claims)
        
    }
}