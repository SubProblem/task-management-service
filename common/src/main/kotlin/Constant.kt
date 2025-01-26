package org.subproblem

object Constant {

    object AuthorizationClassConstants {

        object ApiEndpoints {
            const val REGISTER = "/api/v1/register"
            const val VERIFY = "/api/v1/verify"
            const val ACCOUNT = "/api/v1/account"
        }

    }

    object NotificationServiceConstants {
        object KafkaConstants {
            const val ACCOUNT_VERIFICATION_TOPIC = "account-verification-topic"
            const val TWO_FACTOR_AUTH_TOPIC = "two-factor-auth-topic"
        }
    }
}