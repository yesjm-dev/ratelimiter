package com.example.ratelimiter.infra.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "rate-limiter")
class RateLimiterProperties {
    lateinit var fixed: Fixed
    lateinit var sliding: Sliding
    lateinit var token: Token

    class Fixed {
        var capacity: Int = 0
        var windowMillis: Long = 0
    }

    class Sliding {
        var capacity: Int = 0
        var windowMillis: Long = 0
    }

    class Token {
        var capacity: Int = 0
        var refillIntervalMillis: Long = 0
        var refillAmount: Int = 0
    }
}