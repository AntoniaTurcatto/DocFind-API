package io.github.antoniaturcatto.docfind.service

import io.github.antoniaturcatto.docfind.model.Doctor
import io.github.antoniaturcatto.docfind.model.Role
import io.github.antoniaturcatto.docfind.repository.DoctorRepository
import io.github.antoniaturcatto.docfind.repository.specs.DoctorSpecs
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.util.*

@Service
class DoctorService (private val doctorRepository: DoctorRepository){

    fun search(id:UUID?, name:String?, role: Role?, page: Int, pageSize: Int): Page<Doctor>{
        var specs: Specification<Doctor> = Specification.where({ root, query, cb ->  cb.conjunction()})

        if (id != null)
            specs = specs.and(DoctorSpecs.idEqual(id))
        else { //else because id will already return just one register or none
            if (name != null)
                specs = specs.and(DoctorSpecs.nameLike(name))

            if (role != null)
                specs = specs.and(DoctorSpecs.roleEqual(role))
        }
        return doctorRepository.findAll(specs, PageRequest.of(page,pageSize))
    }

    fun save(doctor:Doctor): Doctor{
        return doctorRepository.save(doctor)
    }

    fun delete(doctor: Doctor){
        doctorRepository.deleteById(doctor.id!!)
    }

    fun findById(id: UUID): Optional<Doctor> {
        return doctorRepository.findById(id)
    }
}