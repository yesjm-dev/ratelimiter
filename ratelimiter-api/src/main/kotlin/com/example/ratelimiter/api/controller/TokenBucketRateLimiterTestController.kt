package com.example.ratelimiter.api.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import com.example.ratelimiter.infra.limiter.TokenBucketRateLimiter
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/token-bucket")
@RestController
class TokenBucketRateLimiterTestController(
    private val tokenBucketRateLimiter: TokenBucketRateLimiter
) {
    @GetMapping("/limit")
    fun testTokenLimit(): String {
        return if (tokenBucketRateLimiter.tryAcquire("user1")) {
            "Request allowed"
        } else {
            "Rate limit exceeded"
        }
    }
}