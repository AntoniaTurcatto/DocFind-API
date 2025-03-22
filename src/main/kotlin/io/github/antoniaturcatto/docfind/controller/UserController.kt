package io.github.antoniaturcatto.docfind.controller

import io.github.antoniaturcatto.docfind.common.dto.UserDTO
import io.github.antoniaturcatto.docfind.service.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(private val service: UserService){

    @PostMapping
    fun save(@RequestBody @Valid dto: UserDTO):ResponseEntity<Void>{
        service.save(dto)?.let {
            return ResponseEntity.noContent().build()
        }
        return ResponseEntity.badRequest().build()
    }
}