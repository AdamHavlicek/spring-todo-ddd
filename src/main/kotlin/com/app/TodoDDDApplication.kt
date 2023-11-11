package com.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class TodoDDDApplication

fun main(args: Array<String>) {
    runApplication<TodoDDDApplication>(*args)
}
