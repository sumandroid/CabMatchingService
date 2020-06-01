package strategies;

import domain.models.Driver;
import domain.models.Rider;
import domain.models.User;

import java.util.List;

public interface MatchingStrategy {

    String matchByAverageRating(User user);
}
