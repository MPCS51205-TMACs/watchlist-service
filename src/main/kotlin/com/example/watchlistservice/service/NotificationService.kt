package com.example.watchlistservice.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForObject
import java.util.*

@Service
class NotificationService {
    @Value("\${notification.url}")
    lateinit var url: String

    @Autowired
    lateinit var restTemplate: RestTemplate

    fun notifyUsers(users: Collection<UUID>) {
        users.forEach { user -> send(user) }
    }

    fun send(user: UUID) {
        println("Notified $user")
    }

}