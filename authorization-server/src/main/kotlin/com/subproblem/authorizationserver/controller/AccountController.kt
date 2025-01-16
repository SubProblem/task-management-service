package com.subproblem.authorizationserver.controller

import com.subproblem.authorizationserver.service.AccountService
import com.subproblem.authorizationserver.utils.JwtUtils
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*
import org.subproblem.Constant.AuthorizationClassConstants.ApiEndpoints.ACCOUNT


@RestController
@RequestMapping(ACCOUNT)
class AccountController(
    private val accountService: AccountService,
    private val jwtUtils: JwtUtils
) {

    @PutMapping("/preferences")
    fun setPreferences(
        @RequestBody preferences: Map<String, Any>,
        @AuthenticationPrincipal jwt: Jwt
    ) = accountService.setPreferences(jwtUtils.extractUserId(jwt), preferences)

    @GetMapping("/preferences")
    fun getAllPreferences(@AuthenticationPrincipal jwt: Jwt) =
        accountService.getAllPreferences(jwtUtils.extractUserId(jwt))

    @GetMapping("/preferences/key")
    fun getPreferenceByKey(
        @RequestParam("name") name: String,
        @AuthenticationPrincipal jwt: Jwt
    ) = accountService.getPreference(jwtUtils.extractUserId(jwt), name)
}
