package io.github.antoniaturcatto.docfind.controller

import io.github.antoniaturcatto.docfind.controller.dto.PatientDTO
import io.github.antoniaturcatto.docfind.service.PatientService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import io.github.antoniaturcatto.docfind.controller.mapper.toPatientDTO
import io.github.antoniaturcatto.docfind.controller.mapper.toPatientEntity
import io.github.antoniaturcatto.docfind.controller.utils.GenericController
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import java.util.UUID

@RestController
@RequestMapping("/patients")
class PatientController(private val service: PatientService) :GenericController {

    @GetMapping
    fun search(@RequestParam(name = "id", required = false) id: UUID?,
        @RequestParam(name = "name", required = false) name:String?,
        @RequestParam(name = "age", required = false) age:Int?,
        @RequestParam(name = "address", required = false) address:String?,
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "page-size", defaultValue = "10") pageSize: Int
    ):ResponseEntity<Page<PatientDTO>>{
        val pageObtained = service.search(id, name, age, address, page, pageSize)
        val pageInDTO = pageObtained.map { toPatientDTO(it) }
        return ResponseEntity.ok(pageInDTO)
    }

    @PostMapping
    fun save(@RequestBody @Valid dto:PatientDTO):ResponseEntity<Void>{
        var patientEntity = toPatientEntity(dto)
        patientEntity = service.save(patientEntity)
        val location = generateHeaderLocation(patientEntity.id!!)
        return ResponseEntity.created(location).build()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id:UUID): ResponseEntity<Void>{
        val patientOpt = service.findById(id)
        if (patientOpt.isPresent){
            service.delete(patientOpt.get())
            return ResponseEntity.noContent().build()
        }
        return ResponseEntity.notFound().build()
    }

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: UUID, @RequestBody @Valid dto: PatientDTO
    ):ResponseEntity<Void>{
        val patientOpt = service.findById(id)
        if (patientOpt.isPresent){
            patientOpt.get().name = dto.name
            patientOpt.get().age = dto.age
            patientOpt.get().address = dto.address?:"Unknown"
            service.save(patientOpt.get())
            return ResponseEntity.noContent().build()
        }
        return ResponseEntity.notFound().build()
    }

}