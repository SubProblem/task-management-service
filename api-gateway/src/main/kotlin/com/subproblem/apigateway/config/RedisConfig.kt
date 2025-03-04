package com.subproblem.apigateway.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository
import org.springframework.security.oauth2.client.web.server.WebSessionServerOAuth2AuthorizedClientRepository
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession


@Configuration(proxyBeanMethods = false)
@EnableRedisWebSession
class RedisConfig {

    @Bean
    fun lettuceConnectionFactory(): LettuceConnectionFactory {
        return LettuceConnectionFactory()
    }

    @Bean
    fun authorizedClientRepository(): ServerOAuth2AuthorizedClientRepository {
        return WebSessionServerOAuth2AuthorizedClientRepository()
    }
}