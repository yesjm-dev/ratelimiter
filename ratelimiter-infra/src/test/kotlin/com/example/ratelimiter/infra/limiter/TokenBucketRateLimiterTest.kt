package com.example.ratelimiter.infra.limiter

import com.example.ratelimiter.domain.limiter.RateLimiter
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.ValueOperations

class TokenBucketRateLimiterTest {
    private val redisTemplate: StringRedisTemplate = mock()
    private val valueOps: ValueOperations<String, String> = mock()

    private val rateLimiter: RateLimiter = TokenBucketRateLimiter(
        redisTemplate = redisTemplate,
        capacity = 2,
        refillRatePerSecond = 1
    )

    @Test
    fun `토큰이 남아있으면 요청 허용`() {
        whenever(redisTemplate.opsForValue()).thenReturn(valueOps)
        whenever(valueOps.get(any())).thenReturn("2:1690000000")
        whenever(valueOps.set(any(), any())).then {}

        val allowed = rateLimiter.tryAcquire("user1")
        assertTrue(allowed)
    }

    @Test
    fun `토큰이 없으면 요청 거부`() {
        whenever(redisTemplate.opsForValue()).thenReturn(valueOps)

        // 마지막 갱신 시간을 현재보다 미래로 세팅 -> 토큰 refill 안 됨
        val futureTimestamp = (System.currentTimeMillis() / 1000) + 1000
        whenever(valueOps.get(any())).thenReturn("0:$futureTimestamp")
        whenever(valueOps.set(any(), any())).then {}

        val allowed = rateLimiter.tryAcquire("user1")
        assertFalse(allowed)
    }
}