package com.example.ratelimiter.domain.limiter

/**
 * 처리율 제한기 인터페이스
 */
interface RateLimiter {
    /**
     * @param key 제한 규칙 정의용 키
     */
    fun tryAcquire(key: String): Boolean
}