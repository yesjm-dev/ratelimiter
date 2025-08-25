package com.example.ratelimiter.domain.limiter

class SlidingLogRateLimiter(
    private val capacity: Int,
    private val windowMillis: Long
) : RateLimiter {

    private val logs = mutableMapOf<String, MutableList<Long>>()

    override fun tryAcquire(key: String): Boolean {
        val now = System.currentTimeMillis()
        val list = logs.getOrPut(key) { mutableListOf() }

        list.removeAll { it <= now - windowMillis }

        return if (list.size < capacity) {
            list.add(now)
            true
        } else {
            false
        }
    }
}