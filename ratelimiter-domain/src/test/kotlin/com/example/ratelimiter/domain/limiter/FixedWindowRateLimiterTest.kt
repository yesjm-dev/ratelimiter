package com.example.ratelimiter.domain.limiter

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FixedWindowRateLimiterTest {

    private lateinit var fixedWindowRateLimiter: FixedWindowRateLimiter

    @BeforeEach
    fun setUp() {
        fixedWindowRateLimiter = FixedWindowRateLimiter(capacity = 3, windowMillis = 1000)
    }

    @Test
    fun `허용량 이하면 요청 허용`() {
        repeat(3) {
            assertTrue(fixedWindowRateLimiter.tryAcquire("user1"))
        }
    }

    @Test
    fun `허용량 초과하면 요청 거부`() {
        repeat(3) { fixedWindowRateLimiter.tryAcquire("user1") }
        assertFalse(fixedWindowRateLimiter.tryAcquire("user1"))
    }

    @Test
    fun `윈도우 지나면 카운트 초기화`() {
        repeat(3) { fixedWindowRateLimiter.tryAcquire("user1") }
        Thread.sleep(1000)
        assertTrue(fixedWindowRateLimiter.tryAcquire("user1"))
    }
}