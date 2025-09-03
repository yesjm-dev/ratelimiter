package com.example.ratelimiter.api.controller

import com.example.ratelimiter.domain.limiter.RateLimiterKeys
import com.example.ratelimiter.domain.limiter.SlidingWindowRateLimiter
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/sliding-window")
@RestController
class SlidingWindowTestController {
    private val slidingWindowRateLimiter = SlidingWindowRateLimiter(
        capacity = 5,
        windowMillis = 1000
    )

    @GetMapping("/limit")
    fun testLimit(): ResponseEntity<String> {
        return if (slidingWindowRateLimiter.tryAcquire(RateLimiterKeys.GLOBAL)) {
            ResponseEntity.ok("Request allowed")
        } else {
            ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Rate limit exceeded")
        }
    }
}