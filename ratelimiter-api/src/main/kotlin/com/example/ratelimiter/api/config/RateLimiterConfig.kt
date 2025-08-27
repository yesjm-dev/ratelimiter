package com.example.ratelimiter.api.config

import com.example.ratelimiter.infra.config.RateLimiterProperties
import com.example.ratelimiter.infra.limiter.RedisFixedWindowRateLimiter
import com.example.ratelimiter.infra.limiter.RedisSlidingWindowRateLimiter
import com.example.ratelimiter.infra.limiter.RedisTokenBucketRateLimiter
import com.example.ratelimiter.infra.limiter.TokenBucketRateLimiter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.script.DefaultRedisScript

@Configuration
@EnableConfigurationProperties(RateLimiterProperties::class)
class RateLimiterConfig(
    private val properties: RateLimiterProperties
) {
    @Bean
    fun redisFixedWindowRateLimiter(
        redisTemplate: StringRedisTemplate,
        @Qualifier("fixedWindowScript") fixedWindowScript: DefaultRedisScript<Long>
    ): RedisFixedWindowRateLimiter {
        return RedisFixedWindowRateLimiter(
            redisTemplate = redisTemplate,
            script = fixedWindowScript,
            capacity = properties.fixed.capacity,
            windowMillis = properties.fixed.windowMillis
        )
    }

    @Bean
    fun redisSlidingWindowRateLimiter(
        redisTemplate: StringRedisTemplate,
        @Qualifier("slidingWindowScript") slidingWindowScript: DefaultRedisScript<Long>
    ): RedisSlidingWindowRateLimiter {
        return RedisSlidingWindowRateLimiter(
            redisTemplate = redisTemplate,
            script = slidingWindowScript,
            capacity = properties.sliding.capacity,
            windowMillis = properties.sliding.windowMillis
        )
    }

    @Bean
    fun redisTokenBucketRateLimiter(
        redisTemplate: StringRedisTemplate,
        @Qualifier("tokenBucketScript") tokenBucketScript: DefaultRedisScript<Long>
    ): RedisTokenBucketRateLimiter {
        return RedisTokenBucketRateLimiter(
            redisTemplate = redisTemplate,
            script = tokenBucketScript,
            capacity = properties.token.capacity,
            refillInterval = properties.token.refillIntervalMillis,
            refillAmount = properties.token.refillAmount
        )
    }

    @Bean
    fun tokenBucketRateLimiter(redisTemplate: StringRedisTemplate): TokenBucketRateLimiter {
        return TokenBucketRateLimiter(
            redisTemplate = redisTemplate,
            capacity = 5,
            refillRatePerSecond = 1)
        }
}