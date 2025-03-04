package io.github.antoniaturcatto.docfind.model

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
    private val id: UUID?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_patient", nullable = false)
    private var patient: Patient,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_doctor", nullable = false)
    private var doctor: Doctor,

    @Column(name = "time", nullable = false)
    private var dateTime: LocalDateTime,

    @CreatedDate
    @Column(name = "data_register")
    private var dataRegister: LocalDateTime?,

    @LastModifiedDate
    @Column(name = "data_update")
    private var dataUpdate: LocalDateTime?,

    @Column(name = "id_user")
    private var idUser: UUID?

)