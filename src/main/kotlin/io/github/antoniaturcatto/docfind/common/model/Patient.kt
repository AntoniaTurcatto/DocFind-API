package io.github.antoniaturcatto.docfind.common.model

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "patient")
@EntityListeners(AuditingEntityListener::class)
class Patient(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    override val id: UUID?,

    name: String,

    @Column(nullable = false)
    var age:Int,

    address:String? = "Unknown", //default value if not input

    @CreatedDate
    @Column(name = "data_register")
    var dataRegister: LocalDateTime? = null,

    @LastModifiedDate
    @Column(name = "data_update")
    var dataUpdate: LocalDateTime? = null,

    @Column(name = "id_user")
    var idUser: UUID? = null
) : Person {

    @Column(length = 50,nullable = false, name = "address")
    var address: String? = address?.trim()?.lowercase()?.replaceFirstChar { c: Char -> c.uppercase() } ?: "Unknown"
        set(value) {
            field = value?.trim()?.lowercase()?.replaceFirstChar { c: Char -> c.uppercase() } ?: "Unknown"
        }

    @Column(length = 50, nullable = false, name = "name")
    override var name: String = name.lowercase().replaceFirstChar { it.uppercase() }
        set(value) { field = value.lowercase().replaceFirstChar { it.uppercase() }}

    override fun toString(): String {
        return "Patient{id=${id}, name=${name}, age=${age}, address=${address}}"
    }

}