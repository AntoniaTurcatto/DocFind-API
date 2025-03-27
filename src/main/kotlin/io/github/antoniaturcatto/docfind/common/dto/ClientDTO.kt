package io.github.antoniaturcatto.docfind.common.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.util.UUID

data class ClientDTO(
    val id: UUID?,
    @NotBlank(message = "mandatory field")
    @Size(max = 150, message = "Invalid size")
    val clientId: String,

    @NotBlank(message = "mandatory field")
    @Size(max = 400, message = "Invalid size")
    val clientSecret: String,

    @NotBlank(message = "mandatory field")
    @Size(max = 200, message = "Invalid size")
    val redirectUri: String,

    @Size(max = 50, message = "Invalid size")
    val scope: String?,

    @NotNull(message = "mandatory field")
    val userId: UUID)
