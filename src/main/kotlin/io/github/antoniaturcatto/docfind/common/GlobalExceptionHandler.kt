package io.github.antoniaturcatto.docfind.common

import io.github.antoniaturcatto.docfind.controller.dto.ErrorDTO
import io.github.antoniaturcatto.docfind.controller.dto.ErrorFieldDTO
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    fun handleMethodArgumentsNotValidException(e: MethodArgumentNotValidException): ErrorDTO{
        println("-----------------error-----------------")
        println(e.printStackTrace())
        val fieldErrors= e.fieldErrors
        val fieldErrorsDTO = fieldErrors.stream().map { ErrorFieldDTO(it.field, it.defaultMessage) }.toList()
        return ErrorDTO(HttpStatus.UNPROCESSABLE_ENTITY.value(),
            "Validation error",
            fieldErrorsDTO)
    }

}