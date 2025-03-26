package io.github.antoniaturcatto.docfind.service

import io.github.antoniaturcatto.docfind.common.dto.UserDTO
import io.github.antoniaturcatto.docfind.common.mapper.toUserEntity
import io.github.antoniaturcatto.docfind.common.model.User
import io.github.antoniaturcatto.docfind.repository.UserRepository
import io.github.antoniaturcatto.docfind.service.component.SecurityComponent
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.security.SecureRandom

@Service
class UserService(private val repository: UserRepository,
                  private val encoder: PasswordEncoder,
                  private val securityComponent: SecurityComponent
) {

    fun findByLogin(login: String):User?{
        return repository.findByLogin(login)
    }

    fun findByEmail(email: String): User?{
        return repository.findByEmail(email)
    }

    fun save(dto: UserDTO):User?{
        return repository.save(
            toUserEntity(
                UserDTO(dto.id, dto.login, dto.email, encoder.encode(dto.pwd), dto.roles)
            )
        )
    }

    fun save(email: String): User{
        return repository.save(
            User(null,
                email,
                email,
                //encoder.encode(generateRandomPassword())
                encoder.encode("123")
                ,listOf("PATIENT"))
        )
    }

    fun getLoggedInUser():User{
        return securityComponent.getLoggedInUser()
    }

    private fun generateRandomPassword(length: Int = 12): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        val random = SecureRandom()
        return (1..length)
            .map { chars[random.nextInt(chars.length)] }
            .joinToString("")
    }
}