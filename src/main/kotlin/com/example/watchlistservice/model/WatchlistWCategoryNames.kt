package com.example.watchlistservice.model

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

class WatchlistWCategoryNames(
    val watchlistId: UUID,
    val userId: UUID,
    val name: String,
    val minPrice: Double,
    val maxPrice: Double,
    val active: Boolean,
    val buyNowEnabled: List<WatchlistBuyNow>,
) {

    lateinit var categories: List<Category>

}