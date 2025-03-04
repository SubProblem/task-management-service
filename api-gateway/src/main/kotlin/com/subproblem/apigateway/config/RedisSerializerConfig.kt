package com.subproblem.apigateway.config

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.beans.factory.BeanClassLoaderAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.security.jackson2.SecurityJackson2Modules
import org.springframework.security.oauth2.client.registration.ClientRegistration

@Configuration
class RedisSerializerConfig : BeanClassLoaderAware {

    private lateinit var classLoader: ClassLoader

    @Bean
    fun springSessionDefaultRedisSerializer(): RedisSerializer<Any> =
        GenericJackson2JsonRedisSerializer(objectMapper())

    private fun objectMapper(): ObjectMapper {
        return ObjectMapper().apply {
            // Register security-related modules
            registerModules(SecurityJackson2Modules.getModules(classLoader))
            // Register module for Java 8 date/time types
            registerModule(JavaTimeModule())
            // Configure serialization features
            configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

            // Build a PolymorphicTypeValidator that includes Spring Security classes
            val ptv: PolymorphicTypeValidator = BasicPolymorphicTypeValidator.builder()
                .allowIfBaseType("org.springframework.security.oauth2.")
                .allowIfBaseType("org.springframework.security.core.")
                .allowIfBaseType("org.springframework.security.authentication.")
                .allowIfBaseType(Any::class.java)
                .allowIfSubType(Collection::class.java)
                .allowIfSubType(Map::class.java)
                .allowIfSubType(Number::class.java)
                .allowIfSubType(String::class.java)
                .allowIfSubType("java.time.")
                .allowIfSubType("org.springframework.security.oauth2.client.OAuth2AuthorizedClient")
                .allowIfSubType("org.springframework.security.oauth2.client.registration.ClientRegistration")
                .allowIfSubType("org.springframework.security.oauth2.core.OAuth2AccessToken")
                .allowIfSubType("org.springframework.security.oauth2.core.OAuth2RefreshToken")
                .build()

            // Activate default typing with the custom validator
            activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY)

            // Add a module for OAuth2-specific deserialization
            registerModule(SimpleModule("OAuth2Module").apply {
                addDeserializer(ClientRegistration::class.java, object : JsonDeserializer<ClientRegistration>() {
                    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): ClientRegistration? =
                        runCatching { ctxt.readValue(p, ClientRegistration::class.java) }
                            .getOrElse { e ->
                                e.takeIf { it.message?.contains("authorizationUri cannot be empty") == true }
                                    ?.let { null } ?: throw e
                            }

                })
            })
        }
    }

    override fun setBeanClassLoader(classLoader: ClassLoader) {
        this.classLoader = classLoader
    }
}