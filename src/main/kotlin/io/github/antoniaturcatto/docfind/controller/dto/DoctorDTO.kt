package io.github.antoniaturcatto.docfind.controller.dto

import io.github.antoniaturcatto.docfind.common.validator.ValidRole
import io.github.antoniaturcatto.docfind.common.model.Role
import jakarta.persistence.Enumerated
import java.util.UUID
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

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
