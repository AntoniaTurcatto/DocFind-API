package io.github.antoniaturcatto.docfind.common.model

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.*

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "doctor")
class Doctor(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    override val id: UUID?,
    name:String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 25, nullable = false)
    var role: Role,

    @CreatedDate
    @Column(name = "data_register")
    var dataRegister: LocalDateTime? = null,

    @LastModifiedDate
    @Column(name = "data_update")
    var dataUpdate: LocalDateTime? = null,

    @Column(name = "id_user")
    var idUser: UUID? = null) : Person {

    @Column(name = "name", length = 50, nullable = false)
    override var name: String = name
        set(value) { field = value.lowercase().replaceFirstChar { it.uppercase() }}

    override fun toString(): String {
        return "Doctor{id=${id}, name=${name}, role=${role}}"
    }
}