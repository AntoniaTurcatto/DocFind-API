package io.github.antoniaturcatto.docfind.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController {

    @GetMapping("/")
    fun homeTest(authentication: Authentication):String{
        return "hello ${authentication.name}"
    }

}