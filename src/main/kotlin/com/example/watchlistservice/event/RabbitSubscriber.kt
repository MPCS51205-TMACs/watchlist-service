package com.example.watchlistservice.event

import com.example.watchlistservice.model.Item
import com.example.watchlistservice.service.NotificationService
import com.example.watchlistservice.service.WatchlistService
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.FanoutExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component


@Component
class RabbitSubscriber(val watchlistService: WatchlistService, val notificationService: NotificationService) {

    @Bean
    fun itemCreateQueue(): Queue = Queue("watchlist-service:item.create",true)

    @Bean
    fun itemCreateExchange(): FanoutExchange = FanoutExchange("item.create",true,false)

    @Bean
    fun itemCreateBinding(): Binding = Binding(itemCreateQueue().name,Binding.DestinationType.QUEUE,itemCreateExchange().name,null, null)

    @RabbitListener(queues = ["watchlist-service:item.create"])
    fun receive(item: Item) {
        val users = watchlistService.getUsersWatchingForItem(item)
        notificationService.notifyUsers(users, item)
    }

    // https://stackoverflow.com/questions/42504883/how-to-deal-with-json-message-with-spring-rabbit-in-spring-boot-application
    @Bean
    fun jsonMessageConverter(): MessageConverter? {
        return Jackson2JsonMessageConverter()
    }
}