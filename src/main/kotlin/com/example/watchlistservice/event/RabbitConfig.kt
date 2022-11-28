package com.example.watchlistservice.event

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.FanoutExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitConfig {

    // https://stackoverflow.com/questions/42504883/how-to-deal-with-json-message-with-spring-rabbit-in-spring-boot-application
    @Bean
    fun jsonMessageConverter(): MessageConverter? {
        return Jackson2JsonMessageConverter()
    }

    @Bean
    fun itemCreateQueue(): Queue = Queue("watchlist-service:item.create",true)
    @Bean
    fun itemCreateExchange(): FanoutExchange = FanoutExchange("item.create",true,false)
    @Bean
    fun itemCreateBinding(): Binding = BindingBuilder.bind(itemCreateQueue()).to(itemCreateExchange())


    @Bean
    fun userActivationQueue(): Queue = Queue("watchlist-service:user.activation",true)
    @Bean
    fun userActivationExchange(): FanoutExchange = FanoutExchange("user.activation",true,false)
    @Bean
    fun userActivationBinding(): Binding = BindingBuilder.bind(userActivationQueue()).to(userActivationExchange())


    @Bean
    fun userDeleteQueue(): Queue = Queue("watchlist-service:user.delete",true)
    @Bean
    fun userDeleteExchange(): FanoutExchange = FanoutExchange("user.delete",true,false)
    @Bean
    fun userDeleteBinding(): Binding = BindingBuilder.bind(userDeleteQueue()).to(userDeleteExchange())

    @Bean
    fun watchlistMatchExchange(): FanoutExchange = FanoutExchange("watchlist.match",true,false)
}