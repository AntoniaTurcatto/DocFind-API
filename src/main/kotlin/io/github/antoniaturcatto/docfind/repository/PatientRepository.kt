package io.github.antoniaturcatto.docfind.repository

import io.github.antoniaturcatto.docfind.common.model.Patient
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.Optional
import java.util.UUID

interface PatientRepository :JpaRepository<Patient, UUID>, JpaSpecificationExecutor<Patient>{
    fun findByNameAndAgeAndAddress(name:String, age:Int, address: String):Optional<Patient>
}