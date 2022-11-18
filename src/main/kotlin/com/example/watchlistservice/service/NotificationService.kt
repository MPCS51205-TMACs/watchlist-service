package com.example.watchlistservice.service

import org.springframework.stereotype.Service
import java.util.*

@Service
class NotificationService {

    fun notifyUser(users: Collection<UUID>){
        println("Notified ${users.size} users")
    }

}