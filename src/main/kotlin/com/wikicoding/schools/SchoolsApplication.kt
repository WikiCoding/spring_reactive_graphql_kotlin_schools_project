package com.wikicoding.schools

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SchoolsApplication

fun main(args: Array<String>) {
	runApplication<SchoolsApplication>(*args)
}
