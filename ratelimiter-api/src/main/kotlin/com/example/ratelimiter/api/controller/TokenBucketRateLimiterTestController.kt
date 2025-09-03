package com.example.ratelimiter.api.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import com.example.ratelimiter.infra.limiter.TokenBucketRateLimiter
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/token-bucket")
@RestController
class TokenBucketRateLimiterTestController(
    private val tokenBucketRateLimiter: TokenBucketRateLimiter
) {
    @GetMapping("/limit")
    fun testTokenLimit(): ResponseEntity<String> {
        return if (tokenBucketRateLimiter.tryAcquire("user1")) {
            ResponseEntity.ok("Request allowed")
        } else {
            ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Rate limit exceeded")
        }
    }
}