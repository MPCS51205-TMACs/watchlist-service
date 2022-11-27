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

    @Bean
    fun itemCreateQueue(): Queue = Queue("watchlist-service:item.create",true)
    @Bean
    fun itemCreateExchange(): FanoutExchange = FanoutExchange("item.create",true,false)
    @Bean
    fun itemCreateBinding(): Binding = BindingBuilder.bind(itemCreateQueue()).to(itemCreateExchange())

    @RabbitListener(queues = ["watchlist-service:item.create"])
    fun receiveItemCreate(item: Item) {
        val users = watchlistService.getUsersWatchingForItem(item)
        notificationService.notifyUsers(users, item)
    }

    @Bean
    fun userActivationQueue(): Queue = Queue("watchlist-service:user.activation",true)
    @Bean
    fun userActivationExchange(): FanoutExchange = FanoutExchange("user.activation",true,false)
    @Bean
    fun userActivationBinding(): Binding = BindingBuilder.bind(userActivationQueue()).to(userActivationExchange())

    @RabbitListener(queues = ["watchlist-service:user.activation"])
    fun receiveUserActivation(userActivation: UserActivation) {
        println("Updating user bids ${userActivation.userId} & ${userActivation.active}")
        watchlistService.updateActivationStatus(userActivation.userId, userActivation.active!!)
    }

    @Bean
    fun userDeleteQueue(): Queue = Queue("watchlist-service:user.delete",true)
    @Bean
    fun userDeleteExchange(): FanoutExchange = FanoutExchange("user.delete",true,false)
    @Bean
    fun userDeleteBinding(): Binding = BindingBuilder.bind(userDeleteQueue()).to(userDeleteExchange())

    @RabbitListener(queues = ["watchlist-service:user.delete"])
    fun receiveUserDelete(userDelete: UserDelete) {
        println("Delete user ${userDelete.userId}")
        watchlistService.deleteWatchlistsByUserId(userDelete.userId)
    }



    // https://stackoverflow.com/questions/42504883/how-to-deal-with-json-message-with-spring-rabbit-in-spring-boot-application
    @Bean
    fun jsonMessageConverter(): MessageConverter? {
        return Jackson2JsonMessageConverter()
    }
}