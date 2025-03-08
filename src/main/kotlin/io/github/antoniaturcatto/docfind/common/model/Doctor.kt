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
    private val _id: UUID?,

    @Column(name = "name", length = 50, nullable = false)
    private var _name:String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 25, nullable = false)
    private var _role: Role,

    @CreatedDate
    @Column(name = "data_register")
    private var dataRegister: LocalDateTime?,

    @LastModifiedDate
    @Column(name = "data_update")
    private var dataUpdate: LocalDateTime?,

    @Column(name = "id_user")
    private var idUser: UUID?) : Person {

    init {
        _name = _name.lowercase().replaceFirstChar { it.uppercase() }
    }

    override val id: UUID?
        get() = _id

    override var name: String
        get() = _name
        set(value) { _name = value.lowercase().replaceFirstChar { it.uppercase() }}

    var role : Role
        get() = this._role
        set(value) {_role = value}

    override fun toString(): String {
        return "Doctor{id=${id}, name=${name}, role=${role}}"
    }
}