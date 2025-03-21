package io.github.antoniaturcatto.docfind.service

import io.github.antoniaturcatto.docfind.common.model.MedicalAppointment
import io.github.antoniaturcatto.docfind.common.model.Role
import io.github.antoniaturcatto.docfind.repository.MedicalAppointmentRepository
import io.github.antoniaturcatto.docfind.repository.specs.MedicalAppointmentSpecs
import io.github.antoniaturcatto.docfind.service.component.excludeNullFromList
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.util.Optional
import java.util.UUID

@Service
class MedicalAppointmentService(private val repository : MedicalAppointmentRepository) {

    fun save(medicalAppointment: MedicalAppointment): MedicalAppointment{
        return repository.save(medicalAppointment)
    }

    fun delete(medicalAppointment: MedicalAppointment){
        repository.deleteById(medicalAppointment.id!!)
    }

    fun findById(id: UUID): Optional<MedicalAppointment>{
        return repository.findById(id)
    }

    fun search(id: UUID?, doctorId: UUID?, doctorName: String?, doctorRole: Role?,
               patientId: UUID?, patientName:String?, patientAge: Int?, patientAddress: String?,
               days: List<Int?>?, months: List<Int?>?, years: List<Int?>?,
               page: Int, pageSize: Int):Page<MedicalAppointment>{

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
            for (day in excludeNullFromList(days))
                specs = specs.and(MedicalAppointmentSpecs.dayEqual(day))
        }

        if (!months.isNullOrEmpty()){
            for (month in excludeNullFromList(months))
                specs = specs.and(MedicalAppointmentSpecs.monthEqual(month))
        }

        if (!years.isNullOrEmpty()){
            for (year in excludeNullFromList(years))
                specs = specs.and(MedicalAppointmentSpecs.yearEqual(year))
        }

        val pageRequest = PageRequest.of(page, pageSize)
        return repository.findAll(specs, pageRequest)

    }

}