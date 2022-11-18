package com.example.watchlistservice.controller

import com.example.watchlistservice.model.Item
import com.example.watchlistservice.model.Watchlist
import com.example.watchlistservice.service.WatchlistService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/watchlist")
class WatchlistController(val watchlistService: WatchlistService) {

    @PostMapping
    fun addWatchlist(@RequestBody watchlist: Watchlist) : Watchlist = watchlistService.createWatchlist(watchlist)

    @GetMapping("/{watchlistId}")
    fun getWatchlistByID(@PathVariable watchlistId: UUID) = watchlistService.getWatchlistById(watchlistId)

}