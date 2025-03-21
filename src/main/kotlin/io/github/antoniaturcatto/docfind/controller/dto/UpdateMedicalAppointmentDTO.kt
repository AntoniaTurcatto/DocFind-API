package io.github.antoniaturcatto.docfind.controller.dto

import jakarta.validation.constraints.Future
import java.time.LocalDateTime
import java.util.*

data class UpdateMedicalAppointmentDTO(
    val id: UUID?,
    val patient: PatientDTO?,
    val doctor: DoctorDTO?,
    @field:Future(message = "Invalid date")
    val dateTime: LocalDateTime?
)
