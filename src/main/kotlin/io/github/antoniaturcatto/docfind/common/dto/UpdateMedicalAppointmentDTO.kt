package io.github.antoniaturcatto.docfind.common.dto

import jakarta.validation.constraints.Future
import java.time.LocalDateTime
import java.util.*

data class UpdateMedicalAppointmentDTO(
    val id: UUID?,
    val patientId: UUID?,
    val doctorId: UUID?,
    @field:Future(message = "Invalid date")
    val dateTime: LocalDateTime?
)
