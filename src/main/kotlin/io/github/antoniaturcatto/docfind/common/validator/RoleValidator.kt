package io.github.antoniaturcatto.docfind.common.validator

import io.github.antoniaturcatto.docfind.common.model.Role
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.stereotype.Component

@Component
class RoleValidator : ConstraintValidator<ValidRole, String> {

    override fun initialize(constraintAnnotation: ValidRole?) {
        super.initialize(constraintAnnotation)
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (value == null) return false
        return try {
            Role.valueOf(value)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}