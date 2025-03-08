package io.github.antoniaturcatto.docfind.common.validator

import io.github.antoniaturcatto.docfind.common.exceptions.ConflictException
import io.github.antoniaturcatto.docfind.common.model.Doctor
import io.github.antoniaturcatto.docfind.repository.AppointmentRepository
import io.github.antoniaturcatto.docfind.repository.DoctorRepository
import org.springframework.stereotype.Component

@Component
class DoctorValidator(private val doctorRepository: DoctorRepository,
    private val appointmentRepository: AppointmentRepository) {

    fun validate(doctor: Doctor){
        if (isConflictuous(doctor))
            throw ConflictException("There is already a doctor with this data")
    }

    fun canDelete(doctor: Doctor){
        if (haveAppointmentsScheduled(doctor))
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

    fun haveAppointmentsScheduled(doctor: Doctor):Boolean{
        return appointmentRepository.existsByDoctor(doctor)
    }
}