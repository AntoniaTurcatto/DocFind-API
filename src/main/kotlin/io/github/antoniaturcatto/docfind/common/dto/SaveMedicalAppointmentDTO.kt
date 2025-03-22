package io.github.antoniaturcatto.docfind.common.dto

import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime
import java.util.UUID

data class SaveMedicalAppointmentDTO(
    val id: UUID?,

    @field:NotNull(message = "mandatory field")
    val patientId: UUID,

    @field:NotNull(message = "mandatory field")
    val doctorId: UUID,

    @field:NotNull(message = "mandatory field")
    @field:Future(message = "Invalid date")
    val dateTime: LocalDateTime
)
