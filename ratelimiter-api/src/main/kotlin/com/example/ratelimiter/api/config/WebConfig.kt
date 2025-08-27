package com.example.ratelimiter.api.config

import com.example.ratelimiter.api.interceptor.RateLimiterInterceptor
import com.example.ratelimiter.infra.limiter.RedisFixedWindowRateLimiter
import com.example.ratelimiter.infra.limiter.RedisSlidingWindowRateLimiter
import com.example.ratelimiter.infra.limiter.RedisTokenBucketRateLimiter
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val redisFixedWindowRateLimiter: RedisFixedWindowRateLimiter,
    private val redisSlidingWindowRateLimiter: RedisSlidingWindowRateLimiter,
    private val redisTokenBucketRateLimiter: RedisTokenBucketRateLimiter
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {

    }
}