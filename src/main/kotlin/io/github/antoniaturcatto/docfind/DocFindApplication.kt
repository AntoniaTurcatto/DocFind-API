package io.github.antoniaturcatto.docfind

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class DocFindApplication

fun main(args: Array<String>) {
	runApplication<DocFindApplication>(*args)
}
