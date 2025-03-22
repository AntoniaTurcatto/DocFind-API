package io.github.antoniaturcatto.docfind.service

import io.github.antoniaturcatto.docfind.common.dto.PatientDTO
import io.github.antoniaturcatto.docfind.common.dto.UpdatePatientDTO
import io.github.antoniaturcatto.docfind.common.mapper.toPatientDTO
import io.github.antoniaturcatto.docfind.common.mapper.toPatientEntity
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
class PatientService(private val patientRepository: PatientRepository, private val validator: PatientValidator,
                     private val userService: UserService) {

    fun search(id: UUID?, name:String?, age:Int?, address:String?, page: Int, pageSize: Int
    ):Page<PatientDTO>{
        var specs :Specification<Patient> = Specification.where({ root, query, cb ->  cb.conjunction()})

        if (id != null)
            specs = specs.and(PatientSpecs.idEqual(id))

        if (name != null)
            specs = specs.and(PatientSpecs.nameLike(name))

        if (age != null)
            specs = specs.and(PatientSpecs.ageEqual(age))

        if (address != null)
            specs = specs.and((PatientSpecs.addressLike(address)))

        val pageRequest = PageRequest.of(page, pageSize)
        return patientRepository.findAll(specs, pageRequest).map { toPatientDTO(it) }
    }

    fun save(dto: PatientDTO): Patient {
        val patient = toPatientEntity(dto)
        validator.validate(patient)
        patient.idUser = userService.getLoggedInUser().id
        return patientRepository.save(patient)
    }

    fun save(id: UUID, dto: UpdatePatientDTO):Patient?{
        val patientOpt = patientRepository.findById(id)
        if (patientOpt.isPresent){
            dto.name?.let {
                patientOpt.get().name = it
            }

            dto.age?.let {
                patientOpt.get().age = it
            }

            dto.address?.let {
                patientOpt.get().address = it
            }
            patientOpt.get().idUser = userService.getLoggedInUser().id
            return patientRepository.save(patientOpt.get())
        }
        return null
    }

    fun delete(id: UUID){
        validator.canDelete(id)
        patientRepository.deleteById(id)
    }

    fun findById(id: UUID):Optional<Patient>{
        return patientRepository.findById(id)
    }
}