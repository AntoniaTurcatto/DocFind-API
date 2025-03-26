package io.github.antoniaturcatto.docfind.common.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.util.UUID

data class UserDTO(
    val id: UUID?,

    @field:Size(max = 150, message = "Invalid size")
    @field:NotBlank(message = "mandatory field")
    val login: String,

    @field:Size(max = 150, message = "Invalid size")
    @field:Email(message = "must be an email")
    @field:NotBlank(message = "mandatory field")
    val email: String,

    @field:NotBlank(message = "mandatory field")
    val pwd: String,
    val roles: List<String>
)
