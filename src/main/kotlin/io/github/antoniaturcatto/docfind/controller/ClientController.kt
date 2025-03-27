package io.github.antoniaturcatto.docfind.controller

import io.github.antoniaturcatto.docfind.common.dto.ClientDTO
import io.github.antoniaturcatto.docfind.service.ClientService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/client")
class ClientController(private val clientService: ClientService) {

    @PostMapping
    @PreAuthorize("hasAnyRole('DOCTOR','PATIENT')")
    fun save(@RequestBody @Valid dto: ClientDTO):ResponseEntity<Void>{
        clientService.save(dto)?.let {
            return ResponseEntity.noContent().build()
        }
        return ResponseEntity.notFound().build()
    }


}