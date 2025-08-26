package com.example.ratelimiter.infra.limiter

import com.example.ratelimiter.domain.limiter.RateLimiter
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.script.DefaultRedisScript

class RedisSlidingWindowRateLimiter(
    private val redisTemplate: StringRedisTemplate,
    private val script: DefaultRedisScript<Long>,
    private val capacity: Int,
    private val windowMillis: Long
) : RateLimiter {
    override fun tryAcquire(key: String): Boolean {
        val redisKey = "rate:sliding:$key"
        val now = System.currentTimeMillis()
        val result = redisTemplate.execute(
            script,
            listOf(redisKey),
            now.toString(),
            windowMillis.toString(),
            capacity.toString()
        )
        return result == 1L
    }
}