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
class RedisTokenBucketIntegrationTest {

    companion object {
        @Container
        val redisContainer = GenericContainer("redis:7.0.11").apply {
            withExposedPorts(6379)
        }
    }

    @Autowired
    lateinit var redisTemplate: StringRedisTemplate

    @Autowired
    lateinit var tokenBucketScript: DefaultRedisScript<Long>

    @Autowired
    lateinit var properties: RateLimiterProperties

    private val key = "token-bucket"

    @BeforeEach
    fun setUp() {
        println("Injected script class: ${tokenBucketScript::class.simpleName}")
        println("Lua script source: ${tokenBucketScript.scriptAsString}")

        redisTemplate.delete(key)
        val hashValues = mapOf(
            "tokens" to properties.token.capacity.toString(),
            "lastRefill" to System.currentTimeMillis().toString()
        )
        redisTemplate.opsForHash<String, String>().putAll(key, hashValues)
    }

    @Test
    fun `토큰이 남아있으면 요청 허용`() {
        val allowed = redisTemplate.execute(
            tokenBucketScript,
            listOf(key),
            "1",
            properties.token.refillIntervalMillis.toString()
        )
        assertTrue(allowed == 1L)
    }

    @Test
    fun `토큰이 없으면 요청 거부`() {
        redisTemplate.opsForHash<String, String>().put(key, "tokens", "0") // 0 토큰으로 초기화

        val allowed = redisTemplate.execute(
            tokenBucketScript,
            listOf(key),
            "1",
            properties.token.refillIntervalMillis.toString()
        )
        assertFalse(allowed == 1L)
    }
}