package com.example.watchlistservice.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

class Item: Serializable {
    lateinit var id: UUID
    lateinit var userId: UUID
    lateinit var description: String
    var quantity: Int = 0
    var price: Double = 0.0
    var startPrice: Double = 0.0
    var shippingCosts: Double = 0.0

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS", shape = JsonFormat.Shape.STRING)
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @JsonSerialize(using = LocalDateTimeSerializer::class)
    lateinit var startTime: LocalDateTime

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS", shape = JsonFormat.Shape.STRING)
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @JsonSerialize(using = LocalDateTimeSerializer::class)
    lateinit var endTime: LocalDateTime

    var buyNow: Boolean = false
    var upForAuction: Boolean = false
    var counterfeit: Boolean = false
    var inappropriate: Boolean = false
    var categories = mutableListOf<Category>()
    var bookmarks = mutableListOf<Bookmark>()
}

class Bookmark: Serializable {
    lateinit var id: UUID
    lateinit var userId: UUID
}

class Category: Serializable {
    lateinit var id: UUID
    lateinit var categoryDescription: String
}
