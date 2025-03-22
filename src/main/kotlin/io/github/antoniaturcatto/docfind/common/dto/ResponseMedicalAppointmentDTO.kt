package io.github.antoniaturcatto.docfind.common.dto

import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime
import java.util.*

data class ResponseMedicalAppointmentDTO(
    val id: UUID,
    val patient: PatientDTO,
    val doctor: DoctorDTO,
    val dateTime: LocalDateTime
)
