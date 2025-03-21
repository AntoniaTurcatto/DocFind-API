package io.github.antoniaturcatto.docfind.service

import io.github.antoniaturcatto.docfind.common.model.Patient
import io.github.antoniaturcatto.docfind.common.validator.PatientValidator
import io.github.antoniaturcatto.docfind.repository.PatientRepository
import io.github.antoniaturcatto.docfind.repository.specs.PatientSpecs
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.util.*

@Service
class PatientService(private val patientRepository: PatientRepository, private val validator: PatientValidator) {

    fun search(id: UUID?, name:String?, age:Int?, address:String?, page: Int, pageSize: Int
    ):Page<Patient>{
        var specs :Specification<Patient> = Specification.where({ root, query, cb ->  cb.conjunction()})

        if (id != null){
            println("ID NOT NULL")
            specs = specs.and(PatientSpecs.idEqual(id))
        }

        if (name != null) {
            println("NAME NOT NULL")
            specs = specs.and(PatientSpecs.nameLike(name))
        }

        if (age != null) {
            println("AGE NOT NULL")
            specs = specs.and(PatientSpecs.ageEqual(age))
        }

        if (address != null) {
            println("ADDRESS NOT NULL")
            specs = specs.and((PatientSpecs.addressLike(address)))
        }

        val pageRequest = PageRequest.of(page, pageSize)
        return patientRepository.findAll(specs, pageRequest)
    }

    fun save(patient: Patient): Patient {
        validator.validate(patient)
        return patientRepository.save(patient)
    }

    fun delete(patient: Patient){
        validator.canDelete(patient)
        patientRepository.deleteById(patient.id!!)
    }

    fun findById(id: UUID):Optional<Patient>{
        return patientRepository.findById(id)
    }
}