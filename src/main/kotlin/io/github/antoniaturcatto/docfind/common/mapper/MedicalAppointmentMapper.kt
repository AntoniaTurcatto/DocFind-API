package io.github.antoniaturcatto.docfind.common.mapper

import io.github.antoniaturcatto.docfind.common.model.Doctor
import io.github.antoniaturcatto.docfind.common.model.MedicalAppointment
import io.github.antoniaturcatto.docfind.common.model.Patient
import io.github.antoniaturcatto.docfind.common.dto.ResponseMedicalAppointmentDTO
import io.github.antoniaturcatto.docfind.common.dto.SaveMedicalAppointmentDTO

fun toMedicalAppointmentEntity(dto: SaveMedicalAppointmentDTO, patient: Patient, doctor: Doctor): MedicalAppointment{
    return MedicalAppointment(dto.id,
        patient,
        doctor,
        dto.dateTime,null,null,null
    )
}

fun toResponseMedicalAppointmentDTO(entity: MedicalAppointment): ResponseMedicalAppointmentDTO {
    return ResponseMedicalAppointmentDTO(
        entity.id!!,
        toPatientDTO(entity.patient),
        toDoctorDTO(entity.doctor),
        entity.dateTime
    )
}