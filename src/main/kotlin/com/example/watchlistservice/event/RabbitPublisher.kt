package com.example.watchlistservice.event

import com.example.watchlistservice.model.Item
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.Serializable
import java.util.*

@Component
class RabbitPublisher {

    @Autowired
    lateinit var rabbitConfig: RabbitConfig

    @Autowired
    lateinit var template: RabbitTemplate

    fun sendMatchEvent(userId: UUID, item: Item) = send(
        exchange = rabbitConfig.watchlistMatchExchange().name,
        payload = WatchlistMatch().apply { this.userId = userId; this.item = item })

    private fun send(exchange: String, payload: Serializable) {
        template.convertAndSend(exchange, "", payload)
    }
}