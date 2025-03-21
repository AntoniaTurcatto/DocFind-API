package io.github.antoniaturcatto.docfind.service.component

fun <T> excludeNullFromList(list : List<T?>):List<T>{
    return list.mapNotNull { it }
}