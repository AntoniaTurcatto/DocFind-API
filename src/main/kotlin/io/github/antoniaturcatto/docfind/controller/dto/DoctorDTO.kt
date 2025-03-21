package io.github.antoniaturcatto.docfind.controller.dto

import io.github.antoniaturcatto.docfind.common.validator.ValidRole
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.util.*

data class DoctorDTO(
    private val _id : UUID?,

    @field:NotBlank(message = "mandatory field")
    @field:Size(max = 50, message = "field size away of bounds")
    private val _name: String?,

    @field:ValidRole
    @field:NotBlank(message = "mandatory field")
    private val _role: String?
){
    val id: UUID?
        get() = _id

    val name: String?
        get() = _name

    val role: String?
        get() = _role
}
