package io.github.antoniaturcatto.docfind.common.dto

import org.springframework.http.HttpStatus

data class ErrorDTO(val status:Int, val message: String, val errors:List<ErrorFieldDTO>){

    companion object{
        fun badRequest(message: String): ErrorDTO {
            return ErrorDTO(HttpStatus.BAD_REQUEST.value(), message, listOf())
        }

        fun conflict(message: String): ErrorDTO {
            return ErrorDTO(HttpStatus.CONFLICT.value(), message, listOf())
        }
    }

}
