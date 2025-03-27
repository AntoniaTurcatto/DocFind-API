package io.github.antoniaturcatto.docfind.common.model

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table
class Client(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID?,

    @Column(name = "client_id")
    val clientId: String,

    @Column(name = "client_secret")
    var clientSecret: String,

    @Column(name = "redirect_uri")
    val redirectUri: String,
    @Column
    val scope: String?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_sys_user")
    val user:User){
}