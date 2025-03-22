package io.github.antoniaturcatto.docfind.common.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.util.UUID

data class PatientDTO(
    val id :UUID?,

    @field:NotBlank(message = "mandatory field")
    @field:Size(max = 50, message = "field size away of bounds")
    val name :String,

    @field:NotNull(message = "mandatory field")
    @field:Min(value = -1, message = "field value can not be negative")
    @field:Max(value = 200, message = "invalid value")
    val age :Int,

    @field:Size(max = 50, message = "field size away of bounds")
    val address :String?
) {
}