package com.example.watchlistservice.service

import com.example.watchlistservice.event.RabbitPublisher
import com.example.watchlistservice.model.Item
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForObject
import java.util.*

@Service
class NotificationService(val rabbitPublisher: RabbitPublisher) {

    fun notifyUsers(users: Collection<UUID>, item: Item) {
        users.forEach { user -> send(user, item) }
    }

    fun send(userId: UUID, item: Item) {
        rabbitPublisher.sendMatchEvent(userId, item)
    }

}