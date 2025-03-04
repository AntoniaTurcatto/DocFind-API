package io.github.antoniaturcatto.docfind

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DocFindApplication

fun main(args: Array<String>) {
	runApplication<DocFindApplication>(*args)
}
