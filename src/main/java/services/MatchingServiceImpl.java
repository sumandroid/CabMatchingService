package services;

import domain.models.Driver;
import domain.models.Rider;
import domain.models.User;
import exceptions.EntityNotFoundException;
import strategies.MatchingStrategy;

import java.util.List;
import java.util.Optional;

public class MatchingServiceImpl implements MatchingService {

    private List<Rider> riders;
    private List<Driver> drivers;
    private MatchingStrategy matchingStrategy;

    public MatchingServiceImpl(List<Rider> riders, List<Driver> drivers, MatchingStrategy matchingStrategy) {
        this.drivers = drivers;
        this.riders = riders;
        this.matchingStrategy = matchingStrategy;
    }

    @Override
    public String matchDriver(String riderName) {
        Optional<Rider> user = riders.stream().filter(r -> r.getName().equalsIgnoreCase(riderName)).findFirst();
        if (!user.isPresent()) throw new EntityNotFoundException("no user found with name" + riderName);
        Rider rider = user.get();
        return matchingStrategy.matchByAverageRating(rider);
    }
}
