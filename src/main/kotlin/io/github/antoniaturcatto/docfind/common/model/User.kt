package io.github.antoniaturcatto.docfind.common.model

import io.github.antoniaturcatto.docfind.common.converters.StringListConverter
import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.util.*

@Table(name = "sys_user")
@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID?,

    @Column
    var login: String,

    @Column
    var email: String,

    @Column
    var pwd: String,

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "roles", columnDefinition = "varchar[]")
    var roles: List<String>
) {
}