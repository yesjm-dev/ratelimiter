package com.example.ratelimiter.domain.limiter

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SlidingLogRateLimiterTest {

    private lateinit var slidingLogRateLimiter: SlidingLogRateLimiter

    @BeforeEach
    fun setUp() {
        slidingLogRateLimiter = SlidingLogRateLimiter(capacity = 3, windowMillis = 1000)
    }

    @Test
    fun `요청 수가 허용량 이하이면 허용`() {
        repeat(3) {
            assertTrue(slidingLogRateLimiter.tryAcquire("user1"))
        }
    }

    @Test
    fun `요청 수가 허용량 초과하면 거부`() {
        repeat(3) { slidingLogRateLimiter.tryAcquire("user1") }
        assertFalse(slidingLogRateLimiter.tryAcquire("user1"))
    }

    @Test
    fun `윈도우 지나면 오래된 요청 제거되어 허용`() {
        repeat(3) { slidingLogRateLimiter.tryAcquire("user1") }
        Thread.sleep(1000)
        assertTrue(slidingLogRateLimiter.tryAcquire("user1"))
    }
}