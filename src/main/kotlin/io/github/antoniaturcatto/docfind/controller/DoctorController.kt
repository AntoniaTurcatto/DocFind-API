package io.github.antoniaturcatto.docfind.controller

import io.github.antoniaturcatto.docfind.common.dto.DoctorDTO
import io.github.antoniaturcatto.docfind.common.dto.UpdateDoctorDTO
import io.github.antoniaturcatto.docfind.common.mapper.toDoctorDTO
import io.github.antoniaturcatto.docfind.common.mapper.toDoctorEntity
import io.github.antoniaturcatto.docfind.controller.utils.GenericController
import io.github.antoniaturcatto.docfind.common.model.Role
import io.github.antoniaturcatto.docfind.service.DoctorService
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
import java.util.*

@RestController
@RequestMapping("/doctors")
class DoctorController(private val doctorService: DoctorService) : GenericController{

    @GetMapping()
    fun search(
        @RequestParam(value = "id", required = false) id: UUID?,
        @RequestParam(value = "name", required = false) name: String?,
        @RequestParam(value = "role", required = false) role: Role?,
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "page-size", defaultValue = "10") pageSize: Int
    ):ResponseEntity<Page<DoctorDTO>>{
        val pageDTO = doctorService.search(id,name,role, page, pageSize)
        return ResponseEntity.ok(pageDTO)
    }

    @PostMapping
    fun save(@RequestBody @Valid doctorDTO: DoctorDTO): ResponseEntity<Any>{
        val doctor = doctorService.save(doctorDTO)
        val location = generateHeaderLocation(doctor.id!!)
        return ResponseEntity.created(location).build()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: UUID): ResponseEntity<Any>{
        doctorService.delete(id)
        return ResponseEntity.noContent().build()
    }

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: UUID, @RequestBody @Valid dto: UpdateDoctorDTO): ResponseEntity<Any>{
        doctorService.save(id, dto)?.let {
            return ResponseEntity.noContent().build()
        }
        return ResponseEntity.notFound().build()
    }

}