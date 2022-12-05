# Watchlist
The watchlist allows users to set categories, price range, and buy now enabled


## REST 
The controller allows for the adding, getting, and deleting of watchlists

## Subscriber
Watchlist subscribes to `item.create` and `item.update` to learn if any item matches the watchlist

## Publisher
When a watchlist matches to an `item.create` or `item.update` it publishes to `watchlist.match` which is consumed by the notification service
