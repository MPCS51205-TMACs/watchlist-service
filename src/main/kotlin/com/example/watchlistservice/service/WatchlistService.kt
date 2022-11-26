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

    fun getMyWatchlists(userId: UUID) : Collection<Watchlist> = watchlistRepo.getWatchlistsByUserId(userId)


    fun getUsersWatchingForItem(item: Item): Collection<UUID> {
        //TODO: startingPrice or BuyNow price
        //TODO: no categories
        return watchlistRepo.getWatchlistsByPrice(item.startingPrice)
            .intersect(watchlistRepo.getWatchlistsByCategoryId(item.categories).toSet())
            .intersect(watchlistRepo.getWatchlistsByByNowValue(item.buyNow).toSet())

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