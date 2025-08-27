package com.example.ratelimiter.api.limiter

import com.example.ratelimiter.api.RatelimiterApiApplication
import com.example.ratelimiter.infra.config.RateLimiterProperties
import com.example.ratelimiter.infra.config.RedisScriptConfig
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.script.DefaultRedisScript
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers


@SpringBootTest(classes = [RatelimiterApiApplication::class, RedisScriptConfig::class, RateLimiterProperties::class])
@Testcontainers
class RedisSlidingWindowIntegrationTest {

    companion object {
        @Container
        val redisContainer = GenericContainer("redis:7.0.11").apply {
            withExposedPorts(6379)
        }
    }

    @Autowired
    lateinit var redisTemplate: StringRedisTemplate

    @Autowired
    lateinit var slidingWindowScript: DefaultRedisScript<Long>

    @Autowired
    lateinit var properties: RateLimiterProperties

    private val key = "sliding-window"

    @BeforeEach
    fun setUp() {
        redisTemplate.delete(key)
    }

    @Test
    fun `허용 범위 내 요청`() {
        val now = System.currentTimeMillis().toString()
        val allowed = redisTemplate.execute(
            slidingWindowScript,
            listOf(key),
            now,
            properties.sliding.windowMillis.toString(),
            properties.sliding.capacity.toString()
        )
        assertTrue(allowed == 1L)
    }

    @Test
    fun `제한 초과 요청`() {
        val now = System.currentTimeMillis()
        // 먼저 5번 요청
        repeat(5) {
            redisTemplate.execute(
                slidingWindowScript,
                listOf(key),
                (now + it).toString(),
                properties.sliding.windowMillis.toString(),
                properties.sliding.capacity.toString()
            )
        }

        // 6번째 요청은 거부
        val allowed = redisTemplate.execute(
            slidingWindowScript,
            listOf(key),
            (now + 5).toString(),
            properties.sliding.windowMillis.toString(),
            properties.sliding.capacity.toString()
        )
        assertFalse(allowed == 1L)
    }
}