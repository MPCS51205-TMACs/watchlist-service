package com.example.watchlistservice.controller

import com.example.watchlistservice.model.Item
import com.example.watchlistservice.model.Watchlist
import com.example.watchlistservice.service.WatchlistService
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.util.*

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

    @GetMapping
    fun getMyWatchlists(authentication: Authentication) : Collection<Watchlist> = watchlistService.getMyWatchlists(UUID.fromString(authentication.name))
}