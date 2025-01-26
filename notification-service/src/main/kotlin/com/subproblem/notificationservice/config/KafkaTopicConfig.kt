package com.subproblem.notificationservice.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.subproblem.Constant.NotificationServiceConstants.KafkaConstants.ACCOUNT_VERIFICATION_TOPIC
import org.subproblem.Constant.NotificationServiceConstants.KafkaConstants.TWO_FACTOR_AUTH_TOPIC

@Configuration
class KafkaTopicConfig {

    @Bean
    fun accountVerification(): NewTopic =
        TopicBuilder.name(ACCOUNT_VERIFICATION_TOPIC)
            .build()

    @Bean
    fun twoFactorAuth(): NewTopic =
        TopicBuilder.name(TWO_FACTOR_AUTH_TOPIC)
            .build()
}