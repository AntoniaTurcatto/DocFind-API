package io.github.antoniaturcatto.docfind.common.security

import io.github.antoniaturcatto.docfind.service.UserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class SocialLoginSuccessHandler(private val userService: UserService) :
    SavedRequestAwareAuthenticationSuccessHandler() {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        var auth = authentication
        val oauth2AuthenticationToken: OAuth2AuthenticationToken = auth as OAuth2AuthenticationToken
        val userEmail = oauth2AuthenticationToken.principal.getAttribute<String>("email")

        var user = userService.findByEmail(userEmail?:"-1")
        if (user == null){
            user = userService.save(userEmail?:"ERROR")
        }
        auth = CustomAuthentication(user)
        SecurityContextHolder.getContext().authentication = auth
        super.onAuthenticationSuccess(request, response, authentication)
    }
}