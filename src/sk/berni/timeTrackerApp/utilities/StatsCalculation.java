package sk.berni.timeTrackerApp.utilities;

import sk.berni.timeTrackerApp.database.Activity;

import java.util.ArrayList;

import static sk.berni.timeTrackerApp.utilities.Calculation.*;
import static sk.berni.timeTrackerApp.utilities.Calculation.calculateDistanceBetweenDates;

public class StatsCalculation {
    private static int totalTimeInSeconds;
    private static long totalDays;

    public static String statsTotalTime(ArrayList<Activity> nameOfActivity){
        totalTimeInSeconds = calcTotalTime(nameOfActivity);
        return convertTotalSecondsToHourMinSec(totalTimeInSeconds);
    }

    public static String statsAverage(){
        int average = 0;
        if(totalDays != 0){
             average = (int) (totalTimeInSeconds / totalDays);
        }
        return convertTotalSecondsToHourMinSec(average);
    }

    public static String statsTotalDays(ArrayList<Activity> nameOfActivity){
        String firstActualDataFromDB = getActualDateToCalculateTotalDays(nameOfActivity);
        totalDays = calculateDistanceBetweenDates(firstActualDataFromDB);
        return String.valueOf(totalDays);
    }
    
    public static String statsTotalDaysFromFirstPutToLastPutDate(ArrayList<Activity> nameOfActivity){
        String firstPutDateDataFromDB = getActualDateToCalculateTotalDays(nameOfActivity);
        String lastPutDateDataFromDB = getLastPutDateToCalculateTotalDays(nameOfActivity);
        totalDays = calculateDistanceBetweenFirstPutAndLastPut(firstPutDateDataFromDB, lastPutDateDataFromDB);
        return String.valueOf(totalDays);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
