package com.subproblem.apigateway.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun springSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {

        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.anyRequest().authenticated()
            }
            .oauth2Login { customizer ->
                customizer.authorizationEndpoint { authorization ->
                    authorization.authorizationRequestRepository(
                        HttpSessionOAuth2AuthorizationRequestRepository()
                    )
                }
            }
            .oauth2Client { }

        return http.build()
    }
}