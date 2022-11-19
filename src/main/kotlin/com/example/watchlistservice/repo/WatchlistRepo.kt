package com.example.watchlistservice.repo

import com.example.watchlistservice.model.Watchlist
import com.example.watchlistservice.model.WatchlistCategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface WatchlistRepo: JpaRepository<Watchlist, UUID> {

    @Query("SELECT DISTINCT w.userId FROM Watchlist w WHERE w.maxPrice>= ?1 AND w.minPrice <= ?1")
    fun getWatchlistsByPrice(price: Double) : Collection<UUID>

    @Query("select DISTINCT wc.watchlist.userId from WatchlistCategory wc WHERE wc.categoryId IN ?1")
    fun getWatchlistsByCategoryId(categoryIds: Collection<UUID>) : Collection<UUID>

    @Query("select DISTINCT wb.watchlist.userId from WatchlistBuyNow wb where wb.value = ?1")
    fun getWatchlistsByByNowValue(buyNow: Boolean) : Collection<UUID>

    @Query
    fun getWatchlistsByUserId(userId:UUID) : Collection<Watchlist>
}