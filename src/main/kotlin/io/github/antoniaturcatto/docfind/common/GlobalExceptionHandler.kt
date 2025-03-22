package io.github.antoniaturcatto.docfind.common

import io.github.antoniaturcatto.docfind.common.exceptions.ConflictException
import io.github.antoniaturcatto.docfind.common.dto.ErrorDTO
import io.github.antoniaturcatto.docfind.common.dto.ErrorFieldDTO
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.lang.IllegalArgumentException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    fun handleMethodArgumentsNotValidException(e: MethodArgumentNotValidException): ErrorDTO {
        println("-----------------error-----------------")
        println(e.printStackTrace())
        val fieldErrors= e.fieldErrors
        val fieldErrorsDTO = fieldErrors.stream().map { ErrorFieldDTO(it.field, it.defaultMessage) }.toList()
        return ErrorDTO(HttpStatus.UNPROCESSABLE_ENTITY.value(),
            "Validation error",
            fieldErrorsDTO)
    }

    @ExceptionHandler(ConflictException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleConflictException(e:ConflictException): ErrorDTO {
        return ErrorDTO.conflict(e.message!!)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ErrorDTO {
        return ErrorDTO.badRequest("Invalid data")
    }

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ErrorDTO {
        return ErrorDTO.badRequest("Invalid data")
    }

}