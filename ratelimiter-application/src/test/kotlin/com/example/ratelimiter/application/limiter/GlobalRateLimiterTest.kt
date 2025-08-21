package com.example.ratelimiter.application.limiter

import com.example.ratelimiter.domain.limiter.RateLimiterKeys
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class GlobalRateLimiterTest {
    @Test
    fun `요청 허용 테스트`() {
        val limiter = GlobalRateLimiter(3)
        assertTrue(limiter.tryAcquire(RateLimiterKeys.GLOBAL))
        assertTrue(limiter.tryAcquire(RateLimiterKeys.GLOBAL))
        assertTrue(limiter.tryAcquire(RateLimiterKeys.GLOBAL))
        assertFalse(limiter.tryAcquire(RateLimiterKeys.GLOBAL))
    }
}