# CabMatchingService
A cab matching service based on historical data of rating provided to riders and drivers to each other.
A driver is matched to a rider based on following constraints:
1. Driver average rating should be higher than the rider's average rating.
2. If no such driver is found select driver with highest rating.
3. Rider should not be matched with a driver whom he has given 1 star in the past and vice-versa.

It also keeps multi-threading environment in mind and hence locks and synchrinisation have been used to make it thread safe.
