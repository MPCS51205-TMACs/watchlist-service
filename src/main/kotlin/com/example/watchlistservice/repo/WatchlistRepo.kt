package com.example.watchlistservice.repo

import com.example.watchlistservice.model.Watchlist
import com.example.watchlistservice.model.WatchlistCategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*
import javax.transaction.Transactional

@Repository
interface WatchlistRepo: JpaRepository<Watchlist, UUID> {

    @Query("SELECT DISTINCT w.watchlistId FROM Watchlist w WHERE w.maxPrice>= ?1 AND w.minPrice <= ?1 AND w.active=true")
    fun getWatchlistsByPrice(price: Double) : Collection<UUID>

    @Query("select DISTINCT wc.watchlist.watchlistId from WatchlistCategory wc WHERE wc.categoryId IN ?1 AND wc.watchlist.active=true")
    fun getWatchlistsByCategoryId(categoryIds: Collection<UUID>) : Collection<UUID>

    @Query("select DISTINCT w.watchlistId from Watchlist w WHERE w.categories.size = 0 AND w.active=true")
    fun getWatchlistsWithZeroCategories() : Collection<UUID>

    @Query("select DISTINCT wb.watchlist.watchlistId from WatchlistBuyNow wb where wb.value = ?1 AND wb.watchlist.active=true")
    fun getWatchlistsByByNowValue(buyNow: Boolean) : Collection<UUID>

    @Transactional
    @Modifying
    @Query("UPDATE Watchlist w SET w.active=?2 WHERE w.userId=?1")
    fun updateActivationStatus(userId: UUID,active: Boolean)

    @Transactional
    @Modifying
    fun deleteWatchlistByUserId(userId: UUID)

    @Query
    fun getWatchlistsByUserId(userId:UUID) : Collection<Watchlist>
}