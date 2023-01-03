package ru.home.linkshorter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LinkshorterApplication

fun main(args: Array<String>) {
	runApplication<LinkshorterApplication>(*args)
}
