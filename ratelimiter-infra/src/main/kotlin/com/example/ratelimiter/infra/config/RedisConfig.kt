package com.example.ratelimiter.infra.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.StringRedisTemplate

@Configuration
@EnableConfigurationProperties(RateLimiterProperties::class)
class RedisConfig {

    @Bean
    fun redisConnectionFactory(): LettuceConnectionFactory {
        return LettuceConnectionFactory("localhost", 6379)
    }

    @Bean
    fun stringRedisTemplate(factory: LettuceConnectionFactory): StringRedisTemplate {
        return StringRedisTemplate(factory)
    }
}