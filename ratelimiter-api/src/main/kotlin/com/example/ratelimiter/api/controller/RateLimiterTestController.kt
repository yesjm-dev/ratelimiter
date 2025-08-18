package com.example.ratelimiter.api.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import com.example.ratelimiter.application.limiter.GlobalRateLimiter

@RestController
class RateLimiterTestController {
    private val globalLimiter = GlobalRateLimiter(capacity = 5)

    @GetMapping("/test-limit")
    fun testLimit(): String {
        return if (globalLimiter.tryAcquire()) {
            "Request allowed"
        } else {
            "Rate limit exceeded"
        }
    }
}