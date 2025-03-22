package io.github.antoniaturcatto.docfind.common.validator

import io.github.antoniaturcatto.docfind.common.exceptions.ConflictException
import io.github.antoniaturcatto.docfind.common.model.Doctor
import io.github.antoniaturcatto.docfind.repository.MedicalAppointmentRepository
import io.github.antoniaturcatto.docfind.repository.DoctorRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class DoctorValidator(private val doctorRepository: DoctorRepository,
    private val medicalAppointmentRepository: MedicalAppointmentRepository) {

    fun validate(doctor: Doctor){
        if (isConflictuous(doctor))
            throw ConflictException("There is already a doctor with this data")
    }

    fun canDelete(doctorId: UUID){
        if (haveAppointmentsScheduled(doctorId))
            throw ConflictException("This doctor still has appointments scheduled")
    }

    fun isConflictuous(doctor: Doctor):Boolean{
        val doctorOpt = doctorRepository.findByNameAndRole(
            doctor.name,doctor.role
        )

        return if (doctor.id == null)//save
            doctorOpt.isPresent
        else //update
            doctorOpt.isPresent && doctorOpt.get().id != doctor.id //for the user do not duplicate registers with different IDs
    }

    fun haveAppointmentsScheduled(doctorId: UUID):Boolean{
        return medicalAppointmentRepository.existsByDoctor_Id(doctorId)
    }
}