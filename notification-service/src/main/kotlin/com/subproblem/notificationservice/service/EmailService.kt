package com.subproblem.notificationservice.service

import org.slf4j.LoggerFactory
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

@Service
class EmailService(
    private val mailSender: JavaMailSender,
    private val templateEngine: TemplateEngine,
) {

    private val logger = LoggerFactory.getLogger(EmailService::class.java)

    fun sendEmail(to: String, subject: String, template: String, variables: Map<String, Any>) {
        val context = Context()
        context.setVariables(variables)

        val body = templateEngine.process(template, context)

        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)

        helper.setTo(to)
        helper.setSubject(subject)
        helper.setText(body, true)

        logger.info("Mail: {}", message)
        mailSender.send(message)
    }
}