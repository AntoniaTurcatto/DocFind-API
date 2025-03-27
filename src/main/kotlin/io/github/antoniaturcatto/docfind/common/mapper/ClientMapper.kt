package io.github.antoniaturcatto.docfind.common.mapper

import io.github.antoniaturcatto.docfind.common.dto.ClientDTO
import io.github.antoniaturcatto.docfind.common.model.Client
import io.github.antoniaturcatto.docfind.common.model.User

fun toClientEntity(dto: ClientDTO, user: User): Client{
    return Client(dto.id,
        dto.clientId,
        dto.clientSecret,
        dto.redirectUri,
        dto.scope,
        user)
}

fun toClientDTO(entity: Client): ClientDTO{
    return ClientDTO(
        entity.id,
        entity.clientId,
        entity.clientSecret,
        entity.redirectUri,
        entity.scope,
        entity.user.id!!
    )
}