package com.example.ratelimiter.api.controller

import com.example.ratelimiter.domain.limiter.RateLimiterKeys
import com.example.ratelimiter.domain.limiter.SlidingWindowRateLimiter
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
    fun testLimit(): String {
        return if (slidingWindowRateLimiter.tryAcquire(RateLimiterKeys.GLOBAL)) {
            "Request allowed"
        } else {
            "Rate limit exceeded"
        }
    }
}