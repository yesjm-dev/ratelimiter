package com.example.ratelimiter.infra.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.data.redis.core.script.DefaultRedisScript

@Configuration
class RedisScriptConfig {

    @Bean
    fun fixedWindowScript(): DefaultRedisScript<Long> {
        val script = DefaultRedisScript<Long>()
        script.setLocation(ClassPathResource("scripts/fixed_window.lua"))
        script.resultType = Long::class.java
        return script
    }

}