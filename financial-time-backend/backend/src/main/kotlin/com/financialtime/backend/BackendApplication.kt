package com.financialtime.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.SpringApplication

@SpringBootApplication
class BackendApplication

fun main(args: Array<String>) {
    SpringApplication.run(BackendApplication::class.java, *args)
    println("Backend application is running")

}