package com.example.watchlistservice.service

import com.example.watchlistservice.model.Item
import com.example.watchlistservice.model.Watchlist
import com.example.watchlistservice.repo.WatchlistRepo
import org.springframework.stereotype.Service
import java.util.*

@Service
class WatchlistService(val watchlistRepo: WatchlistRepo) {
    fun createWatchlist(watchlist: Watchlist): Watchlist {
        watchlist.categories.forEach {
            it.watchlist = watchlist
        }

        watchlist.buyNowEnabled.forEach {
            it.watchlist = watchlist
        }
        return save(watchlist)
    }

    fun getWatchlistsByUserID(userId: UUID): Collection<Watchlist> = watchlistRepo.getWatchlistsByUserId(userId)

    fun deleteWatchlistsByUserId(userId: UUID)  = watchlistRepo.deleteWatchlistByUserId(userId)

    fun updateActivationStatus(userId: UUID, isActive: Boolean) = watchlistRepo.updateActivationStatus(userId,isActive)

    fun getUsersWatchingForItem(item: Item): Collection<UUID> {
        //TODO: startingPrice or BuyNow price
        return watchlistRepo.getWatchlistsIntersectCategories(item.categories)
            .intersect(watchlistRepo.getWatchlistsByPrice(item.startingPrice))
            .intersect(watchlistRepo.getWatchlistsByByNowValue(item.buyNow)).map { it.userId }.toSet()

    }

    private fun getReferenceById(watchlistId: UUID): Watchlist =
        watchlistRepo.getReferenceById(watchlistId)

    private fun save(watchlist: Watchlist): Watchlist {
        try {
            return watchlistRepo.save(watchlist)
        } catch (e: Exception) {
            throw e
        }
    }
}