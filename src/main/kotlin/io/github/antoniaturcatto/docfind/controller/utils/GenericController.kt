package io.github.antoniaturcatto.docfind.controller.utils

import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.util.UUID

interface GenericController {

    fun generateHeaderLocation(id: UUID): URI {
        return ServletUriComponentsBuilder
            .fromCurrentRequest()
            .queryParam("id", id)
            .buildAndExpand(id)
            .toUri()
    }

}