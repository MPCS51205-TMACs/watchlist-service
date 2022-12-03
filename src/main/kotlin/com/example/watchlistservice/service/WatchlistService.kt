package com.example.watchlistservice.service

import com.example.watchlistservice.model.Item
import com.example.watchlistservice.model.Watchlist
import com.example.watchlistservice.repo.WatchlistRepo
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.server.ResponseStatusException
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

    fun deleteWatchlist(watchlistId: UUID, requester: UUID){
        val watchlist = getReferenceById(watchlistId)

        if (requester!= watchlist.userId){
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
        }
        watchlistRepo.delete(watchlist)
    }

    fun updateActivationStatus(userId: UUID, isActive: Boolean) = watchlistRepo.updateActivationStatus(userId,isActive)

    fun getUsersWatchingForItem(item: Item): Collection<UUID> {
        return getWatchlistsByCategoriesFilter(item.categories.map { it.id })
            .intersect(watchlistRepo.getWatchlistsByPrice(item.price))
            .intersect(watchlistRepo.getWatchlistsByByNowValue(item.buyNow)).map { getReferenceById(it).userId }.toSet()

    }

    fun getWatchlistsByCategoriesFilter(categoryIds: Collection<UUID>) : List<UUID> = watchlistRepo.getWatchlistsWithZeroCategories().plus(watchlistRepo.getWatchlistsByCategoryId(categoryIds))

    private fun getReferenceById(watchlistId: UUID): Watchlist  = watchlistRepo.getReferenceById(watchlistId)



    private fun save(watchlist: Watchlist): Watchlist {
        try {
            return watchlistRepo.save(watchlist)
        } catch (e: Exception) {
            throw e
        }
    }
}