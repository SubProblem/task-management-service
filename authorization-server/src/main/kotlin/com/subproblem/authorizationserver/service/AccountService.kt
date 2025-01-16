package com.subproblem.authorizationserver.service

import com.subproblem.authorizationserver.exception.GlobalExceptionHandler
import com.subproblem.authorizationserver.exception.UserNotFoundException
import com.subproblem.authorizationserver.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val userRepository: UserRepository
) {

    companion object {
        private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    }

    fun getPreference(userId: Int, key: String): String =
        userRepository.findById(userId)
            .orElseThrow { UserNotFoundException() }
            .let {
                val preferences = it.getPreferencesAsMap()
                preferences[key]?.toString() ?: "Empty Value"
            }


    fun getAllPreferences(userId: Int): Map<String, Any> =
        userRepository.findById(userId)
            .orElseThrow { UserNotFoundException() }
            .getPreferencesAsMap()


    fun setPreferences(userId: Int, preferences: Map<String, Any>) =
        userRepository.findById(userId)
            .orElseThrow { UserNotFoundException() }
            .also { user ->
                val existingPreferences = user.getPreferencesAsMap().toMutableMap()
                preferences.forEach {
                    if (!existingPreferences.containsKey(it.key)) {
                        existingPreferences[it.key] = it.value
                    }
                }
                logger.info("Updated Preferences  $existingPreferences")
                user.setPreferencesFromMap(existingPreferences)
                userRepository.save(user)
            }.preferences ?: emptyMap<String, Any>()


}


