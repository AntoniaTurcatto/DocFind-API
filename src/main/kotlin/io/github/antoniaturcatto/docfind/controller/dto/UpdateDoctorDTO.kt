package io.github.antoniaturcatto.docfind.controller.dto

import io.github.antoniaturcatto.docfind.common.validator.ValidRole
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.util.*

data class UpdateDoctorDTO(
    val _id : UUID?,

    @field:Size(max = 50, message = "field size away of bounds")
    val _name: String?,

    @field:ValidRole
    @field:NotBlank(message = "mandatory field")
    val _role: String?
)
