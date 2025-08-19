package com.example.ratelimiter.api.config

import com.example.ratelimiter.infra.limiter.TokenBucketRateLimiter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.StringRedisTemplate

@Configuration
class RateLimiterConfig {
    @Bean
    fun tokenBucketRateLimiter(redisTemplate: StringRedisTemplate): TokenBucketRateLimiter {
        return TokenBucketRateLimiter(
            redisTemplate = redisTemplate,
            capacity = 5,
            refillRatePerSecond = 1
        )
    }

}