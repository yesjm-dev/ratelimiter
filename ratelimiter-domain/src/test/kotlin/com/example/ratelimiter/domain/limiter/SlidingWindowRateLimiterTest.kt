package com.example.ratelimiter.domain.limiter

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SlidingWindowRateLimiterTest {

    private lateinit var slidingWindowRateLimiter: SlidingWindowRateLimiter

    @BeforeEach
    fun setUp() {
        slidingWindowRateLimiter = SlidingWindowRateLimiter(capacity = 3, windowMillis = 1000)
    }

    @Test
    fun `요청 수가 허용량 이하이면 허용`() {
        repeat(3) {
            assertTrue(slidingWindowRateLimiter.tryAcquire("user1"))
        }
    }

    @Test
    fun `요청 수가 허용량 초과하면 거부`() {
        repeat(3) { slidingWindowRateLimiter.tryAcquire("user1") }
        assertFalse(slidingWindowRateLimiter.tryAcquire("user1"))
    }

    @Test
    fun `윈도우 지나면 오래된 요청 제거되어 허용`() {
        repeat(3) { slidingWindowRateLimiter.tryAcquire("user1") }
        Thread.sleep(1000)
        assertTrue(slidingWindowRateLimiter.tryAcquire("user1"))
    }
}