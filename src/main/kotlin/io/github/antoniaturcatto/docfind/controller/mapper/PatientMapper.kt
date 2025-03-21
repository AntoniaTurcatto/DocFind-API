package io.github.antoniaturcatto.docfind.controller.mapper

import io.github.antoniaturcatto.docfind.controller.dto.PatientDTO
import io.github.antoniaturcatto.docfind.common.model.Patient

fun toPatientEntity(dto: PatientDTO): Patient {
    return Patient(dto.id,
            dto.name,
            dto.age,
            dto.address)
}

fun toPatientDTO(patient: Patient): PatientDTO{
    return PatientDTO(patient.id,
        patient.name,
        patient.age,
        patient.address)
}