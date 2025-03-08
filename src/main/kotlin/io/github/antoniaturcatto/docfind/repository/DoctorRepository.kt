package io.github.antoniaturcatto.docfind.repository

import io.github.antoniaturcatto.docfind.common.model.Doctor
import io.github.antoniaturcatto.docfind.common.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.Optional
import java.util.UUID

interface DoctorRepository : JpaRepository<Doctor, UUID>, JpaSpecificationExecutor<Doctor>{
    fun findByNameAndRole(name:String, role: Role):Optional<Doctor>
}