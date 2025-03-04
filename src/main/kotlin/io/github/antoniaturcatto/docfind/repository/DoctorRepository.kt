package io.github.antoniaturcatto.docfind.repository

import io.github.antoniaturcatto.docfind.model.Doctor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID

interface DoctorRepository : JpaRepository<Doctor, UUID>, JpaSpecificationExecutor<Doctor>{
}