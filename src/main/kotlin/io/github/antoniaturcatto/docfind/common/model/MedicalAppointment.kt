package io.github.antoniaturcatto.docfind.common.model

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "appointment")
@EntityListeners(AuditingEntityListener::class)
class MedicalAppointment(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_patient", nullable = false)
    var patient: Patient,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_doctor", nullable = false)
    var doctor: Doctor,

    @Column(name = "time", nullable = false)
    var dateTime: LocalDateTime,

    @CreatedDate
    @Column(name = "data_register")
    var dataRegister: LocalDateTime? = null,

    @LastModifiedDate
    @Column(name = "data_update")
    var dataUpdate: LocalDateTime? = null,

    @Column(name = "id_user")
    var idUser: UUID?=null

)