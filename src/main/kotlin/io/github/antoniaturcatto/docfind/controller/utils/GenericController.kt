package io.github.antoniaturcatto.docfind.controller.utils

import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.util.UUID

interface GenericController {

    fun gerarHeaderLocation(id: UUID): URI {
        return ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(id)
            .toUri()
    }

}