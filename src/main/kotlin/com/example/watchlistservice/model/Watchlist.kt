package com.example.watchlistservice.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@Entity
@Table(indexes = [Index(columnList = "userId")])
class Watchlist {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(nullable = false)
    var watchlistId: UUID? = null

    @Column
    @JsonDeserialize
    lateinit var userId: UUID

    @Column
    lateinit var name: String

    @Column
    var minPrice: Double = 0.0

    @Column
    var maxPrice: Double = Double.MAX_VALUE

    @OneToMany(mappedBy = "watchlist", cascade = [CascadeType.ALL])
    var categories: List<WatchlistCategory> = listOf()

    @OneToMany(mappedBy = "watchlist", cascade = [CascadeType.ALL])
    var buyNowEnabled: List<WatchlistBuyNow> = listOf()


}

@Entity
@Table
class WatchlistCategory {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(nullable = false)
    @JsonIgnore
    var id: UUID? = null

    @Column
    lateinit var categoryId: UUID

    @ManyToOne
    @JsonIgnore
    lateinit var watchlist: Watchlist
}

@Entity
@Table
class WatchlistBuyNow {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(nullable = false)
    @JsonIgnore
    var id: UUID? = null

    @Column
    var value: Boolean = true

    @ManyToOne
    @JsonIgnore
    lateinit var watchlist: Watchlist
}
