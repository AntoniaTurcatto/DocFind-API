package io.github.antoniaturcatto.docfind.common.dto

import io.github.antoniaturcatto.docfind.common.validator.ValidRole
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.util.*

data class DoctorDTO(
    val id : UUID?,

    @field:NotBlank(message = "mandatory field")
    @field:Size(max = 50, message = "field size away of bounds")
    val name: String?,

    @field:ValidRole
    @field:NotBlank(message = "mandatory field")
    val role: String?
)
