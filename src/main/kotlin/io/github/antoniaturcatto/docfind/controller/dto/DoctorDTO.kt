package io.github.antoniaturcatto.docfind.controller.dto

import io.github.antoniaturcatto.docfind.model.Role
import java.util.UUID
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class DoctorDTO(
    private val _id : UUID?,

    @NotBlank(message = "mandatory field")
    @Size(min = 1, max = 50, message = "field size away of bounds")
    private val _name: String,

    @NotNull(message = "mandatory field")
    private val _role: Role
){
    val id: UUID?
        get() = _id

    val name: String
        get() = _name

    val role: Role
        get() = _role
}
