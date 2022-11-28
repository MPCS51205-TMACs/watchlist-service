package com.example.watchlistservice.event

import com.example.watchlistservice.model.Item
import java.io.Serializable
import java.util.*

class UserActivation {
    lateinit var userId: UUID
    val active: Boolean = true
}

class UserDelete{
    lateinit var userId: UUID
}

class WatchlistMatch: Serializable{
    lateinit var userId: UUID
    lateinit var item: Item
}