package com.example.ratelimiter.infra.limiter

import com.example.ratelimiter.domain.limiter.RateLimiter
import org.springframework.data.redis.core.StringRedisTemplate
import java.time.Instant

class TokenBucketRateLimiter(
    private val redisTemplate: StringRedisTemplate,
    private val capacity: Int,
    private val refillRatePerSecond: Int,
) : RateLimiter {
    override fun tryAcquire(key: String?): Boolean {
        val userKey = key ?: "global"
        val now = Instant.now().epochSecond
        val bucketKey = "token_bucket:$userKey"

        val data = redisTemplate.opsForValue().get(bucketKey)
        val (tokens, lastRefill) = if (data != null) {
            val parts = data.split(":")
            parts[0].toDouble() to parts[1].toLong()
        } else {
            capacity.toDouble() to now
        }

        val newTokens = minOf(capacity.toDouble(), tokens + (now - lastRefill) * refillRatePerSecond)

        return if (newTokens >= 1) {
            redisTemplate.opsForValue().set(bucketKey, "${newTokens - 1}:$now")
            true
        } else {
            false
        }
    }

}