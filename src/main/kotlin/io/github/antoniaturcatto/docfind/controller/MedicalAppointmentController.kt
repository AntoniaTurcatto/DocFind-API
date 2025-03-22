package io.github.antoniaturcatto.docfind.controller

import io.github.antoniaturcatto.docfind.common.dto.ResponseMedicalAppointmentDTO
import io.github.antoniaturcatto.docfind.common.dto.SaveMedicalAppointmentDTO
import io.github.antoniaturcatto.docfind.common.dto.UpdateMedicalAppointmentDTO
import io.github.antoniaturcatto.docfind.common.mapper.toResponseMedicalAppointmentDTO
import io.github.antoniaturcatto.docfind.common.model.Role
import io.github.antoniaturcatto.docfind.controller.utils.GenericController
import io.github.antoniaturcatto.docfind.service.DoctorService
import io.github.antoniaturcatto.docfind.service.MedicalAppointmentService
import io.github.antoniaturcatto.docfind.service.PatientService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/medical-appointments")
class MedicalAppointmentController(val medicalAppointmentService: MedicalAppointmentService
) : GenericController {

    @PostMapping
    @PreAuthorize("hasRole('DOCTOR')")
    fun save(@RequestBody @Valid dto: SaveMedicalAppointmentDTO):ResponseEntity<Void>{
        medicalAppointmentService.save(dto)?.let {
            return ResponseEntity.created(generateHeaderLocation(it.id!!)).build()
        }
        return ResponseEntity.notFound().build()
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
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
    ):ResponseEntity<Page<ResponseMedicalAppointmentDTO>>{
        val pageDTO = medicalAppointmentService.search(id, doctorId, doctorName, doctorRole,
            patientId, patientName, patientAge, patientAddress, days, months, years, page, pageSize)
        return ResponseEntity.ok(pageDTO)
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    fun delete(@PathVariable id :UUID):ResponseEntity<Void>{
        medicalAppointmentService.delete(id)
        return ResponseEntity.noContent().build()
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    fun update(@PathVariable id:UUID, @RequestBody @Valid dto: UpdateMedicalAppointmentDTO):ResponseEntity<Void>{
        medicalAppointmentService.save(id, dto)?.let {
            return ResponseEntity.noContent().build()
        }
        return ResponseEntity.notFound().build()
    }

}