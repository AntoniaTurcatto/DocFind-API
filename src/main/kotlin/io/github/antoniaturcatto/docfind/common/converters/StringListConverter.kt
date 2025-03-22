package io.github.antoniaturcatto.docfind.common.converters

import jakarta.persistence.AttributeConverter

class StringListConverter: AttributeConverter<List<String>, Array<String>> {
    override fun convertToDatabaseColumn(attribute: List<String>?): Array<String> {
        return attribute?.toTypedArray() ?: emptyArray()
    }

    override fun convertToEntityAttribute(dbData: Array<String>?): List<String> {
        return dbData?.toList() ?: emptyList()
    }

}