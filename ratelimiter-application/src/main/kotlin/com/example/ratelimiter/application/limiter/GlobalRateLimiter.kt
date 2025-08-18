package com.example.ratelimiter.application.limiter

import com.example.ratelimiter.domain.limiter.RateLimiter
import java.util.concurrent.atomic.AtomicInteger

class GlobalRateLimiter(private val capacity: Int) : RateLimiter {
    // AtomicInteger: 멀티스레드에서 안전하게 증가, 감소 가능
    private val counter = AtomicInteger(0)

    override fun tryAcquire(key: String?): Boolean {
        return counter.incrementAndGet() <= capacity
    }
}