package io.github.antoniaturcatto.docfind.controller.mapper

import io.github.antoniaturcatto.docfind.common.model.MedicalAppointment
import io.github.antoniaturcatto.docfind.controller.dto.MedicalAppointmentDTO

fun toMedicalAppointmentEntity(dto: MedicalAppointmentDTO): MedicalAppointment{
    return MedicalAppointment(dto.id,
        toPatientEntity(dto.patient),
        toDoctorEntity(dto.doctor),
        dto.dateTime,null,null,null
    )
}

fun toMedicalAppointmentDTO(entity: MedicalAppointment): MedicalAppointmentDTO{
    return MedicalAppointmentDTO(
        entity.id,
        toPatientDTO(entity.patient),
        toDoctorDTO(entity.doctor),
        entity.dateTime
    )
}