package com.example.ratelimiter.api.controller

import com.example.ratelimiter.domain.limiter.FixedWindowRateLimiter
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/fixed-window")
@RestController
class FixedWindowTestController {

    private val fixedWindowLimiter = FixedWindowRateLimiter(
        capacity = 5,
        windowMillis = 1000
    )

    @GetMapping("/limit")
    fun testFixedWindowLimit(): ResponseEntity<String> {
        return if (fixedWindowLimiter.tryAcquire("user1")) {
            ResponseEntity.ok("Request allowed")
        } else {
            ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Rate limit exceeded")
        }
    }
}