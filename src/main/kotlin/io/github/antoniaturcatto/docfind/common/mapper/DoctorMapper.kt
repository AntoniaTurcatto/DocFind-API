package io.github.antoniaturcatto.docfind.common.mapper

import io.github.antoniaturcatto.docfind.common.dto.DoctorDTO
import io.github.antoniaturcatto.docfind.common.model.Doctor
import io.github.antoniaturcatto.docfind.common.model.Role

fun toDoctorEntity(doctorDTO: DoctorDTO): Doctor {
    return Doctor(doctorDTO.id,
        doctorDTO.name!!,
        Role.valueOf(doctorDTO.role!!))
}

fun toDoctorDTO(doctor : Doctor): DoctorDTO {
    return DoctorDTO(doctor.id, doctor.name, doctor.role.toString())
}