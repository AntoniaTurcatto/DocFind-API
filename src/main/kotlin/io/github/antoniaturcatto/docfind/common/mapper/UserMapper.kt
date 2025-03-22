package io.github.antoniaturcatto.docfind.common.mapper

import io.github.antoniaturcatto.docfind.common.dto.UserDTO
import io.github.antoniaturcatto.docfind.common.model.User

fun toUserEntity(dto: UserDTO):User{
    return User(dto.id,
        dto.login,
        dto.pwd,
        dto.roles)
}

fun toUserDto(entity: User):UserDTO{
    return UserDTO(entity.id,
        entity.login,
        entity.pwd,
        entity.roles)
}