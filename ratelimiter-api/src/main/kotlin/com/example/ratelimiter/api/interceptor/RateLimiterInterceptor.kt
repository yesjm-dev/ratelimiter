package com.example.ratelimiter.api.interceptor

import com.example.ratelimiter.domain.limiter.RateLimiter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.web.servlet.HandlerInterceptor

class RateLimiterInterceptor(
    private val rateLimiter: RateLimiter
) : HandlerInterceptor {

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        val key = request.requestURI + ":" + request.remoteAddr

        if (!rateLimiter.tryAcquire(key)) {
            response.status = HttpStatus.TOO_MANY_REQUESTS.value()
            response.writer.write("Too Many Requests (${request.requestURI})")
            return false
        }

        return true
    }
}