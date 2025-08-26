package com.example.ratelimiter.domain.limiter

class SlidingWindowRateLimiter(
    private val capacity: Int,
    private val windowMillis: Long
) : RateLimiter {

    private val counters = mutableMapOf<String, Pair<Long, Int>>() // start, count

    override fun tryAcquire(key: String): Boolean {
        val now = System.currentTimeMillis()
        val (start, count) = counters.getOrElse(key) { Pair(now, 0) }

        return if (now - start >= windowMillis) {
            counters[key] = Pair(now, 1)
            true
        } else if (count < capacity) {
            counters[key] = Pair(start, count + 1)
            true
        } else {
            false
        }
    }
}