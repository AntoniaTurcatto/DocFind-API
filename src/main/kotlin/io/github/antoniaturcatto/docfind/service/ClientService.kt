package io.github.antoniaturcatto.docfind.service

import io.github.antoniaturcatto.docfind.common.dto.ClientDTO
import io.github.antoniaturcatto.docfind.common.exceptions.ConflictException
import io.github.antoniaturcatto.docfind.common.mapper.toClientEntity
import io.github.antoniaturcatto.docfind.common.model.Client
import io.github.antoniaturcatto.docfind.repository.ClientRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ClientService(private val clientRepository: ClientRepository,
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder) {

    fun save(client: Client): Client{
        validate(client)
        client.clientSecret = passwordEncoder.encode(client.clientSecret)
        return clientRepository.save(client)
    }

    fun save(dto: ClientDTO):Client?{
        userService.findById(dto.userId)?.let {
            val client = toClientEntity(dto, it)
            client.clientSecret = passwordEncoder.encode(client.clientSecret)
            return clientRepository.save(client)
        }
        return null
    }

    fun findByClientId(clientId: String): Client?{
        return clientRepository.findByClientId(clientId)
    }

    fun findById(id: UUID): Client?{
        return clientRepository.findById(id).get()
    }

    private fun validate(client: Client){
        if (isConflictuous(client))
            throw ConflictException("There is already a client with this clientId")
    }

    private fun isConflictuous(client: Client): Boolean{
        clientRepository.findByClientId(client.clientId)?.let {
            return true
        }
        return false
    }
}