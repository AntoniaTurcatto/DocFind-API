package io.github.antoniaturcatto.docfind.controller.mapper

import io.github.antoniaturcatto.docfind.controller.dto.PatientDTO
import io.github.antoniaturcatto.docfind.common.model.Patient

fun toPatientEntity(dto: PatientDTO): Patient {
    return if (dto.address != null){
        Patient(dto.id,
            dto.name,
            dto.age,
            dto.address)
    } else {
        Patient(dto.id,
            dto.name,
            dto.age
        )
    }

}

fun toPatientDTO(patient: Patient): PatientDTO{
    return PatientDTO(patient.id,
        patient.name,
        patient.age,
        patient.address)
}