package com.example.watchlistservice.event

import java.util.*

class UserActivation {
    lateinit var userId: UUID
    val active: Boolean = true
}

class UserDelete{
    lateinit var userId: UUID
}