package com.example.ratelimiter.api.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import com.example.ratelimiter.application.limiter.GlobalRateLimiter
import com.example.ratelimiter.domain.limiter.RateLimiterKeys
import com.example.ratelimiter.infra.limiter.TokenBucketRateLimiter
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/global")
@RestController
class RateLimiterTestController(
    private val tokenBucketRateLimiter: TokenBucketRateLimiter
) {
    private val globalLimiter = GlobalRateLimiter(capacity = 5)

    @GetMapping("/test")
    fun testLimit(): String {
        return if (globalLimiter.tryAcquire(RateLimiterKeys.GLOBAL)) {
            "Request allowed"
        } else {
            "Rate limit exceeded"
        }
    }
}