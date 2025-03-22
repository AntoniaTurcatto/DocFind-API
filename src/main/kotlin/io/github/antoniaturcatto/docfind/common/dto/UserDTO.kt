package io.github.antoniaturcatto.docfind.common.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.util.UUID

data class UserDTO(
    val id: UUID?,

    @field:Size(max = 20, message = "Invalid size")
    @field:NotBlank(message = "mandatory field")
    val login: String,

    @field:NotBlank(message = "mandatory field")
    val pwd: String,
    val roles: List<String>
)
