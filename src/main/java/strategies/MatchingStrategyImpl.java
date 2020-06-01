package strategies;

import domain.models.Driver;
import domain.models.Rider;
import domain.models.User;

import java.util.List;

public class MatchingStrategyImpl implements MatchingStrategy {

    private List<Rider> riders;
    private List<Driver> drivers;

    public MatchingStrategyImpl(List<Rider> riders, List<Driver> drivers) {
        this.riders = riders;
        this.drivers = drivers;
    }

    @Override
    public String matchByAverageRating(User user) {
        String driverName = null;
        float riderAverageRating = user.getAverageRating();
        float highestRating = Float.MIN_VALUE;
        synchronized (User.lock) {
            for (Driver driver : drivers) {
                if (driver.getAverageRating() > riderAverageRating && driver.getAverageRating() > highestRating) {
                    if (isDrivereligible(user, driver)) {
                        driverName = driver.getName();
                        highestRating = driver.getAverageRating();
                    }
                }
            }
            if (driverName == null) { //if no driver found with average rating higher than rider average rating.
                Driver driver = getDriverWithHighestRatingForRider(user);
                driverName = driver.getName();
            }
        }
        return driverName;
    }


    private Driver getDriverWithHighestRatingForRider(User user) {
        float highestRating = Float.MIN_VALUE;
        Driver d = null;
        for (Driver driver : drivers) {
            if (driver.getAverageRating() > highestRating) {
                if(isDrivereligible(user, driver)) {
                    d = driver;
                    highestRating = driver.getAverageRating();
                }
            }
        }
        return d;
    }

    private boolean isDrivereligible(User user, Driver driver) {
        if (driver.getRatings().get(user) != null && user.getRatings().get(driver) != null) {
            if (driver.getRatings().get(user) == 1 || user.getRatings().get(driver) == 1) {
                return false;
            }
        }
        return true;
    }
}
