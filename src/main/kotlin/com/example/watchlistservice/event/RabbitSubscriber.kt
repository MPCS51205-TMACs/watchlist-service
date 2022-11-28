package com.example.watchlistservice.event

import com.example.watchlistservice.model.Item
import com.example.watchlistservice.service.NotificationService
import com.example.watchlistservice.service.WatchlistService
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.FanoutExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component


@Component
class RabbitSubscriber(val watchlistService: WatchlistService, val notificationService: NotificationService) {

    @RabbitListener(queues = ["watchlist-service:item.create"])
    fun receiveItemCreate(item: Item) {
        val users = watchlistService.getUsersWatchingForItem(item)
        notificationService.notifyUsers(users, item)
    }

    @RabbitListener(queues = ["watchlist-service:user.activation"])
    fun receiveUserActivation(userActivation: UserActivation) {
        println("Updating user bids ${userActivation.userId} & ${userActivation.active}")
        watchlistService.updateActivationStatus(userActivation.userId, userActivation.active!!)
    }

    @RabbitListener(queues = ["watchlist-service:user.delete"])
    fun receiveUserDelete(userDelete: UserDelete) {
        println("Delete user ${userDelete.userId}")
        watchlistService.deleteWatchlistsByUserId(userDelete.userId)
    }

}