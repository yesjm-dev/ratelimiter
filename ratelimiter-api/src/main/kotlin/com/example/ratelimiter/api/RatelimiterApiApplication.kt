package com.example.ratelimiter.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.example.ratelimiter"])
class RatelimiterApiApplication

fun main() {
    runApplication<RatelimiterApiApplication>()
}