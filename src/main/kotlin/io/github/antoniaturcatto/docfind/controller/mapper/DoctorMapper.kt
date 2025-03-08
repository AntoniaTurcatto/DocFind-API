package io.github.antoniaturcatto.docfind.controller.mapper

import io.github.antoniaturcatto.docfind.controller.dto.DoctorDTO
import io.github.antoniaturcatto.docfind.common.model.Doctor
import io.github.antoniaturcatto.docfind.common.model.Role

    fun toDoctorEntity(doctorDTO: DoctorDTO): Doctor {
        return Doctor(doctorDTO.id,
            doctorDTO.name!!,
            Role.valueOf(doctorDTO.role!!),
            null,
            null,
            null)
    }

    fun toDoctorDTO(doctor : Doctor): DoctorDTO{
        return DoctorDTO(doctor.id, doctor.name, doctor.role.toString())
    }

