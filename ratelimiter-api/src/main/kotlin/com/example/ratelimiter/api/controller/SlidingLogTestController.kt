package com.example.ratelimiter.api.controller

import com.example.ratelimiter.domain.limiter.RateLimiterKeys
import com.example.ratelimiter.domain.limiter.SlidingLogRateLimiter
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/sliding-log")
@RestController
class SlidingLogTestController {
    private val slidingLogRateLimiter = SlidingLogRateLimiter(
        capacity = 5,
        windowMillis = 1000
    )

    @GetMapping("/limit")
    fun testLimit(): String {
        return if (slidingLogRateLimiter.tryAcquire(RateLimiterKeys.GLOBAL)) {
            "Request allowed"
        } else {
            "Rate limit exceeded"
        }
    }
}