package io.github.antoniaturcatto.docfind.repository

import io.github.antoniaturcatto.docfind.common.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository: JpaRepository<User,UUID> {
    fun findByLogin(login: String):User?
    fun findByEmail(email: String):User?
}