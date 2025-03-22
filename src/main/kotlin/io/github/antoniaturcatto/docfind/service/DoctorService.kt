package io.github.antoniaturcatto.docfind.service

import io.github.antoniaturcatto.docfind.common.dto.DoctorDTO
import io.github.antoniaturcatto.docfind.common.dto.UpdateDoctorDTO
import io.github.antoniaturcatto.docfind.common.mapper.toDoctorDTO
import io.github.antoniaturcatto.docfind.common.mapper.toDoctorEntity
import io.github.antoniaturcatto.docfind.common.model.Doctor
import io.github.antoniaturcatto.docfind.common.model.Role
import io.github.antoniaturcatto.docfind.common.validator.DoctorValidator
import io.github.antoniaturcatto.docfind.repository.DoctorRepository
import io.github.antoniaturcatto.docfind.repository.specs.DoctorSpecs
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.util.*

@Service
class DoctorService (private val doctorRepository: DoctorRepository, private val validator: DoctorValidator){

    fun search(id:UUID?, name:String?, role: Role?, page: Int, pageSize: Int): Page<DoctorDTO> {
        var specs: Specification<Doctor> = Specification.where({ root, query, cb ->  cb.conjunction()})

        if (id != null)
            specs = specs.and(DoctorSpecs.idEqual(id))
        else { //else because id will already return just one register or none
            if (name != null)
                specs = specs.and(DoctorSpecs.nameLike(name))

            if (role != null)
                specs = specs.and(DoctorSpecs.roleEqual(role))
        }
        return doctorRepository.findAll(specs, PageRequest.of(page,pageSize)).map { toDoctorDTO(it) }
    }

    fun save(dto: DoctorDTO): Doctor {
        val doctor = toDoctorEntity(dto)
        validator.validate(doctor)
        return doctorRepository.save(doctor)
    }

    fun save(id: UUID, dto: UpdateDoctorDTO):Doctor?{
        val doctorOpt = doctorRepository.findById(id)
        if (doctorOpt.isPresent){
            dto.name?.let {
                doctorOpt.get().name = it
            }

            dto.role?.let {
                doctorOpt.get().role = Role.valueOf(it)
            }

            return doctorRepository.save(doctorOpt.get())
        }
        return null
    }

    fun delete(id: UUID){
        validator.canDelete(id)
        doctorRepository.deleteById(id)
    }

    fun findById(id: UUID): Optional<Doctor> {
        return doctorRepository.findById(id)
    }
}