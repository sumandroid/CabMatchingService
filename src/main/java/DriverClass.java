import domain.models.Driver;
import domain.models.Rider;
import domain.models.User;
import exceptions.DriverNotFoundException;
import services.MatchingService;
import services.MatchingServiceImpl;
import strategies.MatchingStrategy;
import strategies.MatchingStrategyImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class DriverClass {

    public static void main(String[] args) {
        System.out.println("*******Welcome to Cab matching service*********");
        Scanner scanner = new Scanner(System.in);
        List<Rider> riders = new ArrayList<>();
        List<Driver> drivers = new ArrayList<>();
        HashMap<String, Rider> nameToRiderMap = new HashMap<>();
        HashMap<String, Driver> nameToDriverMap = new HashMap<>();
        List<String> ratingInputs = new ArrayList<>();
        ratingInputs.add("Ram 3 Bheem 5");
        ratingInputs.add("Laxman 5 Nakul 2");
        ratingInputs.add("Ram 4 Sehdev 2");
        ratingInputs.add("Bharat 3 Bheem 5");
        ratingInputs.add("Ram 3 Bheem 1");
        ratingInputs.add("Laxman 5 Sehdev 3");
        ratingInputs.add("Bharat 5 Nakul 5");
        for(String input : ratingInputs){
            try{
                String[] tokens = input.split(" ");
                Rider rider = null;
                Driver driver = null;
                if(!nameToRiderMap.containsKey(tokens[0])){
                    rider = new Rider(tokens[0]);
                    nameToRiderMap.put(rider.getName(), rider);
                    riders.add(rider);
                }else {
                    rider = nameToRiderMap.get(tokens[0]);
                }
                if(!nameToDriverMap.containsKey(tokens[2])){
                    driver = new Driver(tokens[2]);
                    nameToDriverMap.put(driver.getName(), driver);
                    drivers.add(driver);
                }else {
                    driver = nameToDriverMap.get(tokens[2]);
                }
                rider.addRating(driver, Float.parseFloat(tokens[1]));
                driver.addRating(rider, Float.parseFloat(tokens[3]));
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        MatchingStrategy matchingStrategy = new MatchingStrategyImpl(riders, drivers);
        MatchingService matchingService = new MatchingServiceImpl(riders, drivers, matchingStrategy);
        System.out.println("enter rider name to find cab for");
        String riderName = scanner.nextLine();
        findDriver(matchingService, riderName);
    }

    private static void findDriver(MatchingService matchingService, String riderName) {
        try {
            String driver = matchingService.matchDriver(riderName);
            if(driver == null) throw new DriverNotFoundException("No driver found for :" + riderName);
            System.out.println("Driver " + driver + " is matched for " + riderName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
