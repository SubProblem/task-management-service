package com.subproblem.authorizationserver.entity

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.*
import org.subproblem.enums.Role

@Entity
@Table(name = "users")
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(nullable = false, unique = true)
    lateinit var uuid: String

    @Column(nullable = false)
    lateinit var firstname: String

    @Column(nullable = false)
    lateinit var lastname: String

    @Column(nullable = false, unique = true)
    lateinit var email: String

    @Column(nullable = false)
    lateinit var password: String

    @Column(unique = true, name = "phone_number")
    var phoneNumber: String? = null

    @Column(columnDefinition = "jsonb")
    var preferences: String? = null

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var role: Role = Role.USER

    @Transient
    private val objectMapper: ObjectMapper = ObjectMapper()

    fun getPreferencesAsMap(): Map<String, Any> =
        preferences?.let {
            objectMapper.readValue(it, object : TypeReference<Map<String, Any>>() {})
        } ?: emptyMap()

    fun setPreferencesFromMap(preferences: Map<String, Any>) {

        println("preferences in Entity $preferences")
        this.preferences =objectMapper.writeValueAsString(preferences)
        println("Preferences after writing as String: ${this.preferences}")
    }

    constructor()

    constructor(
        firstname: String,
        lastname: String,
        email: String,
        password: String,
        phoneNumber: String? = null,
        preferences: String? = null,
        role: Role = Role.USER
    ) {
        this.firstname = firstname
        this.lastname = lastname
        this.email = email
        this.password = password
        this.phoneNumber = phoneNumber
        this.preferences = preferences
        this.role = role
    }
}
