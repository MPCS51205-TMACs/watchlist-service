package com.example.watchlistservice.controller

import com.example.watchlistservice.model.Watchlist
import com.example.watchlistservice.service.WatchlistService
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.util.*

@CrossOrigin
@RestController
@RequestMapping("/watchlist")
class WatchlistController(val watchlistService: WatchlistService) {

    @PostMapping
    fun addWatchlist(@RequestBody watchlist: Watchlist, authentication: Authentication): Watchlist {
        // extract userId to apply to watchlist object
        // prevents a user from creating a watchlist as someone else
        watchlist.userId = UUID.fromString(authentication.name)
        return watchlistService.createWatchlist(watchlist)
    }

    @DeleteMapping("/{watchlistId}")
    fun deleteWatchlist(@PathVariable watchlistId: UUID?, authentication: Authentication) = if (watchlistId!= null) watchlistService.deleteWatchlist(watchlistId,UUID.fromString(authentication.name)) else watchlistService.deleteWatchlistsByUserId(UUID.fromString(authentication.name))

    @GetMapping
    fun getMyWatchlists(authentication: Authentication) : Collection<Watchlist> = watchlistService.getWatchlistsByUserID(UUID.fromString(authentication.name))
}