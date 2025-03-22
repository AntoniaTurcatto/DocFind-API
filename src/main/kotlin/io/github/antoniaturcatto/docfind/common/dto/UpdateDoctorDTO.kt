package io.github.antoniaturcatto.docfind.common.dto

import io.github.antoniaturcatto.docfind.common.validator.ValidRole
import io.github.antoniaturcatto.docfind.common.validator.ValidRoleForUpdate
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.util.*

data class UpdateDoctorDTO(
    val id : UUID?,

    @field:Size(max = 50, message = "field size away of bounds")
    val name: String?,

    @field:ValidRoleForUpdate
    val role: String?
)
