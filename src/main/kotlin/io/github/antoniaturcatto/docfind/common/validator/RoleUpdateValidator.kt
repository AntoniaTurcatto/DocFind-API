package io.github.antoniaturcatto.docfind.common.validator

import io.github.antoniaturcatto.docfind.common.model.Role
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class RoleUpdateValidator:ConstraintValidator<ValidRoleForUpdate, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (value == null) return true
        return try {
            Role.valueOf(value)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}