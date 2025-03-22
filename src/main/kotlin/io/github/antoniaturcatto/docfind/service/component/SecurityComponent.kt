package io.github.antoniaturcatto.docfind.service.component

import io.github.antoniaturcatto.docfind.common.model.User
import io.github.antoniaturcatto.docfind.service.UserService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Component
class SecurityComponent {

    fun getLoggedInUsername():String{
        val authentication = SecurityContextHolder.getContext().authentication
        return (authentication.principal as UserDetails).username
    }

}