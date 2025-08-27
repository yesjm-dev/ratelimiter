package com.example.ratelimiter.infra.limiter

import com.example.ratelimiter.domain.limiter.RateLimiter
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.script.DefaultRedisScript

class RedisFixedWindowRateLimiter(
    private val redisTemplate: StringRedisTemplate,
    private val script: DefaultRedisScript<Long>,
    private val capacity: Int,
    private val windowMillis: Long
) : RateLimiter {
    override fun tryAcquire(key: String): Boolean {
        val redisKey = "rate:fixed:$key"
        val result = redisTemplate.execute(
            script,
            listOf(redisKey),
            capacity.toString(),
            windowMillis.toString()
        )
        return result == 1L
    }
}