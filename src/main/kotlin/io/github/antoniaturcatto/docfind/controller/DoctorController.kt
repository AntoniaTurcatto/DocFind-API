package io.github.antoniaturcatto.docfind.controller

import io.github.antoniaturcatto.docfind.controller.dto.DoctorDTO
import io.github.antoniaturcatto.docfind.controller.mapper.toDoctorDTO
import io.github.antoniaturcatto.docfind.controller.mapper.toDoctorEntity
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
        @RequestParam(value = "id", required = false) @Valid id: UUID?,
        @RequestParam(value = "name", required = false) @Valid name: String?,
        @RequestParam(value = "role", required = false) @Valid role: Role?,
        @RequestParam(value = "page", defaultValue = "0") @Valid page: Int,
        @RequestParam(value = "page-size", defaultValue = "10") @Valid pageSize: Int
    ):ResponseEntity<Page<DoctorDTO>>{
        val pageObtained = doctorService.search(id,name,role, page, pageSize)
        val pageDTO = pageObtained.map { toDoctorDTO(it) }

        return ResponseEntity.ok(pageDTO)
    }

    @PostMapping
    fun save(@RequestBody @Valid doctorDTO: DoctorDTO): ResponseEntity<Any>{
        var doctorEntity = toDoctorEntity(doctorDTO)
        doctorEntity = doctorService.save(doctorEntity)
        val location = generateHeaderLocation(doctorEntity.id!!)
        return ResponseEntity.created(location).build()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: String): ResponseEntity<Any>{
        val idUUID = UUID.fromString(id)
        val docOptional = doctorService.findById(idUUID)
        if (docOptional.isPresent){
            doctorService.delete(docOptional.get())
            return ResponseEntity.noContent().build()
        }
        return ResponseEntity.noContent().build()
    }

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: String, @RequestBody @Valid doctorDTO: DoctorDTO): ResponseEntity<Any>{
        val idUUID = UUID.fromString(id)
        val docOptional = doctorService.findById(idUUID)
        if (docOptional.isPresent){
            docOptional.get().name = doctorDTO.name!!
            docOptional.get().role = Role.valueOf(doctorDTO.role!!)
            doctorService.save(docOptional.get())
            return ResponseEntity.noContent().build()
        }
        return ResponseEntity.notFound().build()
    }

}