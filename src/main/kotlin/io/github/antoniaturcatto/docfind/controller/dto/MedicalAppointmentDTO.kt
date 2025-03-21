package io.github.antoniaturcatto.docfind.controller.dto

import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime
import java.util.UUID

data class MedicalAppointmentDTO(
    val id: UUID?,

    @field:NotBlank(message = "mandatory field")
    val patient: PatientDTO,

    @field:NotBlank(message = "mandatory field")
    val doctor: DoctorDTO,

    @field:NotNull(message = "mandatory field")
    @field:Future(message = "Invalid date")
    val dateTime: LocalDateTime
)
