package domain.models;

import java.util.concurrent.ConcurrentHashMap;

public abstract class User {

    private String name;
    private ConcurrentHashMap<User, Float> ratings;
    private float averageRating;
    public static final Object lock = new Object();

    public User(String name){
        this.name = name;
        ratings = new ConcurrentHashMap<User, Float>();
    }

    public String getName() {
        return name;
    }

    public ConcurrentHashMap<User, Float> getRatings() {
        return ratings;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public void addRating(User user, Float rating){
        synchronized (lock){
            ratings.put(user, rating);
            calculateAverageRating();
        }
    }

    private void calculateAverageRating(){
        int ratingCount = ratings.size();
        float sumOfRatings = ratings.values().stream().reduce(0f, Float::sum);
        this.averageRating = sumOfRatings / ratingCount;
    }
}
