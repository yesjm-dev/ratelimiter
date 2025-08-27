package com.example.ratelimiter.api.controller

import com.example.ratelimiter.application.service.TestService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/")
@RestController
class RateLimiterTestController(
    private val testService: TestService
) {
    @GetMapping("/login")
    fun login(@RequestParam user: String) = testService.login(user)

    @GetMapping("/search")
    fun search(@RequestParam query: String) = testService.search(query)

    @GetMapping("/report")
    fun report(@RequestParam id: String) = testService.report(id)
}