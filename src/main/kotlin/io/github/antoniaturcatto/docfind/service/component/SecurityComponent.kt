package io.github.antoniaturcatto.docfind.service.component

import io.github.antoniaturcatto.docfind.common.model.User
import io.github.antoniaturcatto.docfind.common.security.CustomAuthentication
import io.github.antoniaturcatto.docfind.service.UserService
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Component
class SecurityComponent {

    fun getLoggedInUser():User{
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication is CustomAuthentication)
            return authentication.user
        throw AccessDeniedException("Access denied")
    }

}