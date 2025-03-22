package io.github.antoniaturcatto.docfind.repository

import io.github.antoniaturcatto.docfind.common.model.Doctor
import io.github.antoniaturcatto.docfind.common.model.MedicalAppointment
import io.github.antoniaturcatto.docfind.common.model.Patient
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID

interface MedicalAppointmentRepository: JpaRepository<MedicalAppointment, UUID>, JpaSpecificationExecutor<MedicalAppointment> {

    fun existsByDoctor_Id(doctorId: UUID):Boolean

    fun existsByPatient_Id(patientId: UUID):Boolean
}