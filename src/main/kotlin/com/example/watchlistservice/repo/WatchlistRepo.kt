package com.example.watchlistservice.repo

import com.example.watchlistservice.model.Watchlist
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*
import javax.transaction.Transactional

@Repository
interface WatchlistRepo: JpaRepository<Watchlist, UUID> {

    @Query("SELECT DISTINCT w FROM Watchlist w WHERE w.maxPrice>= ?1 AND w.minPrice <= ?1 AND w.active=true")
    fun getWatchlistsByPrice(price: Double) : Collection<Watchlist>

    @Query("select DISTINCT wc.watchlist from WatchlistCategory wc WHERE wc.categoryId IN ?1 AND wc.watchlist.active=true")
    fun getWatchlistsByCategoryId(categoryIds: Collection<UUID>) : Collection<Watchlist>

    @Query("select DISTINCT w from Watchlist w WHERE w.categories.size = 0 AND w.active=true")
    fun getWatchlistsWithZeroCategories() : Collection<Watchlist>

    @Query("select DISTINCT wb.watchlist from WatchlistBuyNow wb where wb.value = ?1 AND wb.watchlist.active=true")
    fun getWatchlistsByByNowValue(buyNow: Boolean) : Collection<Watchlist>

    @Transactional
    @Modifying
    @Query("UPDATE Watchlist w SET w.active=?2 WHERE w.userId=?1")
    fun updateActivationStatus(userId: UUID,active: Boolean)

    @Transactional
    @Modifying
    fun deleteWatchlistByUserId(userId: UUID)

    @Query
    fun getWatchlistsByUserId(userId:UUID) : Collection<Watchlist>

    fun getWatchlistsIntersectCategories(categoryIds: Collection<UUID>) = getWatchlistsWithZeroCategories().plus(getWatchlistsByCategoryId(categoryIds))
}