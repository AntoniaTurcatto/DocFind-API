package io.github.antoniaturcatto.docfind.service

import io.github.antoniaturcatto.docfind.common.dto.UserDTO
import io.github.antoniaturcatto.docfind.common.mapper.toUserEntity
import io.github.antoniaturcatto.docfind.common.model.User
import io.github.antoniaturcatto.docfind.repository.UserRepository
import io.github.antoniaturcatto.docfind.service.component.SecurityComponent
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(private val repository: UserRepository,
                  private val encoder: PasswordEncoder,
                  private val securityComponent: SecurityComponent
) {

    fun findByLogin(login: String):User?{
        return repository.findByLogin(login)
    }

    fun save(dto: UserDTO):User?{
        return repository.save(
            toUserEntity(
                UserDTO(dto.id, dto.login, encoder.encode(dto.pwd), dto.roles)
            )
        )
    }

    fun getLoggedInUser():User{
        return repository.findByLogin(securityComponent.getLoggedInUsername())!!
    }
}