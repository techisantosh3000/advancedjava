package org.map.analysis;

import java.util.HashMap;
import java.util.Map;

public class RunnerApplication {
    public static void main(String[] args) {
        Map<String,Integer> sampleMap = new HashMap<>();

        sampleMap.put("AAA",111111);
        sampleMap.put("BBB",111112);
        sampleMap.put("CCC",111113);
        sampleMap.put("DDD",111114);

        Map<String,Integer> sampleMap1 = new HashMap<>();

        // map null analysis
        System.out.println("Print the map "+(sampleMap1.get("AAA") != null ? sampleMap1.get("AAA") : " "));

        HashMap<String,String> weekDays = new HashMap<>();

        // Add data to the HashMap
        weekDays.put("Monday", "Working Day");
        weekDays.put("Tuesday", "Working Day");
        weekDays.put("Wednesday", "Working Day");
        weekDays.put("Thursday", "Working Day");
        weekDays.put("Friday", "Working Day");
        weekDays.put("Saturday", "Off Day");
        weekDays.put("Sunday", "Off Day");

        // Print the data in the HashMap
        System.out.println("Working Schedule : " + weekDays + "\n");

        // Check if the given key is present in the Map
        // IF yes, its value will be returned
        String sunday = weekDays.getOrDefault("KDMAL", "No Announcements Yet.");
        System.out.println("Is Sunday a working day?  " + sunday);

        // IF not, the default value passed will be returned
        String christmas = weekDays.getOrDefault("Christmas", "National Holiday");
        System.out.println("Is Christmas a working day?  " + christmas);
        
    }
}
