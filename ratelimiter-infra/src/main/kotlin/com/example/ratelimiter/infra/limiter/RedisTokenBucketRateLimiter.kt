package com.example.ratelimiter.infra.limiter

import com.example.ratelimiter.domain.limiter.RateLimiter
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.script.DefaultRedisScript

class RedisTokenBucketRateLimiter(
    private val redisTemplate: StringRedisTemplate,
    private val script: DefaultRedisScript<Long>,
    private val capacity: Int,
    private val refillInterval: Long,
    private val refillAmount: Int,
) : RateLimiter {
    override fun tryAcquire(key: String): Boolean {
        val redisKey = "rate:token:$key"
        val now = System.currentTimeMillis()
        val result = redisTemplate.execute(
            script,
            listOf(redisKey),
            now.toString(),
            refillInterval.toString(),
            refillAmount.toString(),
            capacity.toString()
        )
        return result == 1L
    }

}