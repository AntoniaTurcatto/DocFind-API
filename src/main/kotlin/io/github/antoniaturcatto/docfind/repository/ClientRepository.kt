package io.github.antoniaturcatto.docfind.repository

import io.github.antoniaturcatto.docfind.common.model.Client
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ClientRepository: JpaRepository<Client, UUID> {

    fun findByClientId(clientId: String):Client?

}