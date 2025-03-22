package io.github.antoniaturcatto.docfind.service

import io.github.antoniaturcatto.docfind.common.dto.ResponseMedicalAppointmentDTO
import io.github.antoniaturcatto.docfind.common.dto.SaveMedicalAppointmentDTO
import io.github.antoniaturcatto.docfind.common.dto.UpdateMedicalAppointmentDTO
import io.github.antoniaturcatto.docfind.common.mapper.toMedicalAppointmentEntity
import io.github.antoniaturcatto.docfind.common.mapper.toResponseMedicalAppointmentDTO
import io.github.antoniaturcatto.docfind.common.model.MedicalAppointment
import io.github.antoniaturcatto.docfind.common.model.Role
import io.github.antoniaturcatto.docfind.repository.MedicalAppointmentRepository
import io.github.antoniaturcatto.docfind.repository.specs.MedicalAppointmentSpecs
import io.github.antoniaturcatto.docfind.service.component.excludeNullFromList
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.Optional
import java.util.UUID

@Service
class MedicalAppointmentService(private val medicalAppointmentRepository : MedicalAppointmentRepository,
    private val doctorService: DoctorService,
    private val patientService: PatientService,
    private val userService: UserService) {


    fun save(dto: SaveMedicalAppointmentDTO):MedicalAppointment?{
        val patient = patientService.findById(dto.patientId)
        val doctor = doctorService.findById(dto.doctorId)
        if (patient.isPresent && doctor.isPresent){
            val entity = toMedicalAppointmentEntity(dto, patient.get(), doctor.get())
            entity.idUser = userService.getLoggedInUser().id
            return medicalAppointmentRepository.save(entity)
        }
        return null
    }

    fun save(id: UUID, dto: UpdateMedicalAppointmentDTO): MedicalAppointment?{
        val savedMedicalAppointmentOpt = medicalAppointmentRepository.findById(id)
        if (savedMedicalAppointmentOpt.isPresent) {
            dto.doctorId?.let {
                val doctorOpt = doctorService.findById(it)
                if (doctorOpt.isPresent)
                    savedMedicalAppointmentOpt.get().doctor = doctorOpt.get()
            }

            dto.patientId?.let {
                val patientOpt = patientService.findById(it)
                if (patientOpt.isPresent)
                    savedMedicalAppointmentOpt.get().patient = patientOpt.get()
            }


            dto.dateTime?.let {
                savedMedicalAppointmentOpt.get().dateTime = it
            }
            savedMedicalAppointmentOpt.get().idUser = userService.getLoggedInUser().id
            return medicalAppointmentRepository.save(savedMedicalAppointmentOpt.get())
        }
        return null
    }

    fun delete(id: UUID){
        val medicalAppointmentOpt = medicalAppointmentRepository.findById(id)
        if(medicalAppointmentOpt.isPresent){
            medicalAppointmentRepository.delete(medicalAppointmentOpt.get())
        }
    }

    fun findById(id: UUID): Optional<MedicalAppointment>{
        return medicalAppointmentRepository.findById(id)
    }

    fun search(id: UUID?, doctorId: UUID?, doctorName: String?, doctorRole: Role?,
               patientId: UUID?, patientName:String?, patientAge: Int?, patientAddress: String?,
               days: List<Int?>?, months: List<Int?>?, years: List<Int?>?,
               page: Int, pageSize: Int):Page<ResponseMedicalAppointmentDTO>{

        var specs : Specification<MedicalAppointment> = Specification { root, query, cb -> cb.conjunction()}

        if (id != null)
            specs = specs.and(MedicalAppointmentSpecs.idEqual(id))

        if (doctorId!= null)
            specs = specs.and(MedicalAppointmentSpecs.doctorIdEqual(doctorId))

        if (doctorName != null && doctorName.trim().isNotBlank())
            specs = specs.and(MedicalAppointmentSpecs.doctorNameLike(doctorName))

        if (doctorRole != null)
            specs = specs.and(MedicalAppointmentSpecs.doctorRoleEqual(doctorRole))

        if (patientId != null)
            specs = specs.and(MedicalAppointmentSpecs.patientIdEqual(patientId))

        if (patientName != null && patientName.trim().isNotBlank())
            specs = specs.and(MedicalAppointmentSpecs.patientNameLike(patientName))

        if (patientAge != null)
            specs = specs.and(MedicalAppointmentSpecs.patientAgeEqual(patientAge))

        if (patientAddress != null && patientAddress.trim().isNotBlank())
            specs = specs.and(MedicalAppointmentSpecs.patientAddressLike(patientAddress))



        if (!days.isNullOrEmpty()){
            specs = specs.and(MedicalAppointmentSpecs.dayIn(excludeNullFromList(days)))
        }

        if (!months.isNullOrEmpty()){
            specs = specs.and(MedicalAppointmentSpecs.monthIn(excludeNullFromList(months)))
        }

        if (!years.isNullOrEmpty()){
            specs = specs.and(MedicalAppointmentSpecs.yearIn(excludeNullFromList(years)))
        }

        val pageRequest = PageRequest.of(page, pageSize)
        return medicalAppointmentRepository.findAll(specs, pageRequest).map { toResponseMedicalAppointmentDTO(it) }

    }

}