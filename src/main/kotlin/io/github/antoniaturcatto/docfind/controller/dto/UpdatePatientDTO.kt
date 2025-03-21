package io.github.antoniaturcatto.docfind.controller.dto

import jakarta.validation.constraints.*
import java.util.*

data class UpdatePatientDTO(
    val id : UUID?,

    @field:Size(max = 50, message = "field size away of bounds")
    val name :String?,

    @field:Min(value = -1, message = "field value can not be negative")
    @field:Max(value = 200, message = "invalid value")
    val age :Int?,

    @field:Size(max = 50, message = "field size away of bounds")
    val address :String?
)
