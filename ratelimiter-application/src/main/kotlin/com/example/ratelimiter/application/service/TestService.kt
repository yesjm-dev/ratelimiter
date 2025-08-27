package com.example.ratelimiter.application.service

import org.springframework.stereotype.Service

@Service
class TestService {
    fun login(user: String) = "Login success for $user"
    fun search(query: String) = "Search results for $query"
    fun report(id: String) = "Report generated for $id"
}