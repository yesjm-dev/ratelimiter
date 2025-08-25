package com.example.ratelimiter.domain.limiter

class FixedWindowRateLimiter(
    private val capacity: Int,
    private val windowMillis: Long
): RateLimiter {

    private val requests  = mutableMapOf<String, MutableList<Long>>()

    override fun tryAcquire(key: String): Boolean {
        val now = System.currentTimeMillis()
        val windowStart = now - windowMillis
        val list = requests.getOrPut(key) { mutableListOf() }

        // 요청 시간에서 윈도우 시작 이전의 요청 제거
        list.removeAll { it < windowStart }

        return if (list.size < capacity) {
            list.add(now)
            true
        } else {
            false
        }
    }
}