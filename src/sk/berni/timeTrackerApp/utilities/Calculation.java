package sk.berni.timeTrackerApp.utilities;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import sk.berni.timeTrackerApp.database.Activity;

public class Calculation {

	private static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

	public static long calculateDistanceBetweenDates(String date) {
		return calculateTotalDistanceBetweenDates(date, sdf.format(Timestamp.from(Instant.now())));
	}

	public static long calculateDistanceBetweenFirstPutAndLastPut(String dateFirst, String dateLast) {
		return calculateTotalDistanceBetweenDates(dateFirst, dateLast);
	}

	private static long calculateTotalDistanceBetweenDates(String dateFirst, String dateLast) {
		Date firstDate;
		Date actualDate;

		long diff = 1;
		try {
			firstDate = sdf.parse(dateFirst);
			actualDate = sdf.parse(dateLast);

			long diffInMillis = Math.abs(actualDate.getTime() - firstDate.getTime());
			diff += TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return diff;
	}

	public static String getActualDateToCalculateTotalDays(ArrayList<Activity> activitiesByName) {
		String dateSTR = String.valueOf(getDateForCalculation(activitiesByName).get(0));
		if (dateSTR.length() < 8) {
			dateSTR = "0" + dateSTR;
		}
		return addChar(dateSTR, '.', 2, 5);
	}

	public static String getLastPutDateToCalculateTotalDays(ArrayList<Activity> activitiesByName) {
		String dateSTR = String.valueOf(getDateForCalculation(activitiesByName).get(activitiesByName.size() - 1));
		if (dateSTR.length() < 8) {
			dateSTR = "0" + dateSTR;
		}
		return addChar(dateSTR, '.', 2, 5);
	}

	private static ArrayList<Integer> getDateForCalculation(ArrayList<Activity> activitiesByName) {
		ArrayList<Integer> datesInt = new ArrayList<>();
		for (Activity date : activitiesByName) {
			if (date.getDate().substring(0, 10).contains(".")) {
				int dateInt = Integer.parseInt(date.getDate().substring(0, 10).replace(".", ""));
				datesInt.add(dateInt);
			}
		}
		Collections.sort(datesInt);
		return datesInt;
	}

	private static String addChar(String str, char ch, int position1, int position2) {
		StringBuilder sb = new StringBuilder(str);
		sb.insert(position1, ch);
		sb.insert(position2, ch);
		return sb.toString();
	}

	public static int calcTotalTime(ArrayList<Activity> activitiesByName) {
		int hour = 0, minute = 0, seconds = 0;
		for (Activity activities : activitiesByName) {
			String activityTime = activities.getActivityTime();

			hour += Integer.parseInt(activityTime.substring(0, 2));
			minute += Integer.parseInt(activityTime.substring(3, 5));
			seconds += Integer.parseInt(activityTime.substring(6, 8));
		}
		hour = hour * 60 * 60;
		minute = minute * 60;
		return hour + minute + seconds;
	}

	public static String convertTotalSecondsToHourMinSec(int totalTimeSeconds) {
		int hours = totalTimeSeconds / 60;
		int minutes = hours % 60;
		int seconds = totalTimeSeconds % 60;
		hours = hours / 60;
		return addZeroToTime(hours) + ":" + addZeroToTime(minutes) + ":" + addZeroToTime(seconds);
	}

	public static String addZeroToTime(int time) {
		return (((time / 10) == 0) ? "0" : "") + time;
	}

}
