package io.github.antoniaturcatto.docfind.model

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
    private val _id: UUID?,

    @Column(length = 50, nullable = false)
    private var _name: String,

    @Column(nullable = false)
    private var age:Int,

    @Column(length = 50,nullable = false)
    private var _address:String = "Unknown", //default value if not input

    @CreatedDate
    @Column(name = "data_register")
    private var dataRegister: LocalDateTime?,

    @LastModifiedDate
    @Column(name = "data_update")
    private var dataUpdate: LocalDateTime?,

    @Column(name = "id_user")
    private var idUser: UUID?
) : Person  {

    init {
        val formatString: (String) -> String = {
            it.trim().lowercase().replaceFirstChar { c -> c.uppercase()}
        }
        _name = formatString(_name)
        _address = formatString(_address)
    }

    var address: String
        get() = _address
        set(value) {
            _address = value.trim().lowercase().replaceFirstChar { c: Char -> c.uppercase() }
        }

    override val id: UUID?
        get() = _id

    override var name: String
        get() = _name
        set(value) { _name = value }

    override fun toString(): String {
        return "Patient{id=${id}, name=${name}, age=${age}, address=${address}}"
    }

}