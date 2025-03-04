package io.github.antoniaturcatto.docfind.controller.mapper

import io.github.antoniaturcatto.docfind.controller.dto.DoctorDTO
import io.github.antoniaturcatto.docfind.model.Doctor
import org.springframework.stereotype.Component

@Component
class DoctorMapper {

    fun toEntity(doctorDTO: DoctorDTO): Doctor {
        return Doctor(doctorDTO.id,
            doctorDTO.name,
            doctorDTO.role,
            null,
            null,
            null)
    }

    fun toDTO(doctor : Doctor): DoctorDTO{
        return  DoctorDTO(doctor.id, doctor.name, doctor.role)
    }

}