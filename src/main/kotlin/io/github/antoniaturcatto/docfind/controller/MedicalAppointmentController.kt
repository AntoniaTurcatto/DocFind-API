package io.github.antoniaturcatto.docfind.controller

import io.github.antoniaturcatto.docfind.common.model.Doctor
import io.github.antoniaturcatto.docfind.common.model.Role
import io.github.antoniaturcatto.docfind.controller.dto.MedicalAppointmentDTO
import io.github.antoniaturcatto.docfind.controller.dto.UpdateMedicalAppointmentDTO
import io.github.antoniaturcatto.docfind.controller.mapper.toDoctorEntity
import io.github.antoniaturcatto.docfind.controller.mapper.toMedicalAppointmentDTO
import io.github.antoniaturcatto.docfind.controller.mapper.toMedicalAppointmentEntity
import io.github.antoniaturcatto.docfind.controller.mapper.toPatientEntity
import io.github.antoniaturcatto.docfind.controller.utils.GenericController
import io.github.antoniaturcatto.docfind.repository.MedicalAppointmentRepository
import io.github.antoniaturcatto.docfind.service.MedicalAppointmentService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/medical-appointments")
class MedicalAppointmentController(val service: MedicalAppointmentService) : GenericController {

    @PostMapping
    fun save(@RequestBody @Valid dto: MedicalAppointmentDTO):ResponseEntity<Void>{
        var medicalAppointmentEntity = toMedicalAppointmentEntity(dto)
        medicalAppointmentEntity = service.save(medicalAppointmentEntity)
        return ResponseEntity.created(generateHeaderLocation(
            medicalAppointmentEntity.id!!
        )).build()
    }

    @GetMapping
    fun search(@RequestParam(name = "id", required = false) id: UUID?,
        @RequestParam(name = "doctor-id", required = false) doctorId: UUID?,
        @RequestParam(name = "doctor-name", required = false) doctorName: String?,
        @RequestParam(name = "doctor-role" , required = false) doctorRole: Role?,
        @RequestParam(name = "patient-id", required = false) patientId: UUID?,
        @RequestParam(name = "patient-name", required = false) patientName: String?,
        @RequestParam(name = "patient-age", required = false) patientAge: Int?,
        @RequestParam(name = "patient-address", required = false) patientAddress: String?,
        @RequestParam(name = "day", required = false) days: List<Int?>?,
        @RequestParam(name = "month", required = false) months: List<Int?>?,
        @RequestParam(name = "year", required = false) years: List<Int?>?,
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "page-size", defaultValue = "10") pageSize: Int
    ):ResponseEntity<Page<MedicalAppointmentDTO>>{
        val pageWithEntities = service.search(id, doctorId, doctorName, doctorRole,
            patientId, patientName, patientAge, patientAddress, days, months, years, page, pageSize)
        return ResponseEntity.ok(pageWithEntities.map { toMedicalAppointmentDTO(it) })
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id :UUID):ResponseEntity<Void>{
        val medicalAppointmentOpt = service.findById(id)
        if(medicalAppointmentOpt.isPresent){
            service.delete(medicalAppointmentOpt.get())
            return ResponseEntity.noContent().build()
        }
        return ResponseEntity.notFound().build()
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id:UUID, @RequestBody @Valid dto: UpdateMedicalAppointmentDTO):ResponseEntity<Void>{
        val savedMedicalAppointmentOpt = service.findById(id)
        if (savedMedicalAppointmentOpt.isPresent){
            if (dto.doctor != null)
                savedMedicalAppointmentOpt.get().doctor = toDoctorEntity(dto.doctor)

            if(dto.patient != null)
                savedMedicalAppointmentOpt.get().patient = toPatientEntity(dto.patient)

            if (dto.dateTime != null)
                savedMedicalAppointmentOpt.get().dateTime = dto.dateTime

            service.save(savedMedicalAppointmentOpt.get())
            return ResponseEntity.ok().build()
        }
        return ResponseEntity.notFound().build()
    }

}