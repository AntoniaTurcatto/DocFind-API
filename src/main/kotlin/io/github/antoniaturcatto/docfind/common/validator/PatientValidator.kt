package io.github.antoniaturcatto.docfind.common.validator

import io.github.antoniaturcatto.docfind.common.exceptions.ConflictException
import io.github.antoniaturcatto.docfind.common.model.Patient
import io.github.antoniaturcatto.docfind.repository.MedicalAppointmentRepository
import io.github.antoniaturcatto.docfind.repository.PatientRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class PatientValidator(private val patientRepository: PatientRepository,
    private val medicalAppointmentRepository: MedicalAppointmentRepository) {

    fun validate(patient: Patient){
        if (isConflictuous(patient))
            throw ConflictException("There is already a patient with this data")
    }

    fun canDelete(patientId: UUID){
        if (haveAppointmentsScheduled(patientId))
            throw ConflictException("This patient still has appointments scheduled")
    }

    fun isConflictuous(patient: Patient):Boolean{
        val patientOpt = patientRepository.findByNameAndAgeAndAddress(
            patient.name, patient.age, patient.address!!
        )

        return if (patient.id == null){
            patientOpt.isPresent
        } else {
            patientOpt.isPresent && patient.id != patientOpt.get().id
        }
    }

    fun haveAppointmentsScheduled(patientId: UUID):Boolean{
        return medicalAppointmentRepository.existsByPatient_Id(patientId)
    }
}