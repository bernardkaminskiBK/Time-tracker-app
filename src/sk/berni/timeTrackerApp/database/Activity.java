package sk.berni.timeTrackerApp.database;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;

public class Activity extends RecursiveTreeObject<Activity> implements Comparable<Activity> {

	private final SimpleStringProperty date;
	private final SimpleStringProperty nameOfActivity;
	private final SimpleStringProperty activityTime;
	private SimpleStringProperty id;

	public Activity(String date, String activity, String time) {
		this.date = new SimpleStringProperty(date);
		this.nameOfActivity = new SimpleStringProperty(activity);
		this.activityTime = new SimpleStringProperty(time);
	}

	public Activity(String activity, String time) {
		this.date = new SimpleStringProperty(
				new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(Timestamp.from(Instant.now())));
		this.nameOfActivity = new SimpleStringProperty(activity);
		this.activityTime = new SimpleStringProperty(time);
	}

	public Activity(Integer id, String date, String activity, String time) {
		this.date = new SimpleStringProperty(date);
		this.nameOfActivity = new SimpleStringProperty(activity);
		this.activityTime = new SimpleStringProperty(time);
		this.id = new SimpleStringProperty(String.valueOf(id));
	}

	public String getDate() {
		return date.get();
	}

	public SimpleStringProperty getSimpleDateProperty() {
		return date;
	}

	public String getNameOfActivity() {
		return nameOfActivity.get();
	}

	public SimpleStringProperty getSimpleNameOfActivityProperty() {
		return nameOfActivity;
	}

	public String getActivityTime() {
		return activityTime.get();
	}

	public SimpleStringProperty getSimpleActivityTimeProperty() {
		return activityTime;
	}

	public String getId() {
		return id.get();
	}

	public Integer getIdInt() {
		return Integer.parseInt(id.get());
	}

	@Override
	public String toString() {
		return "Activity [date=" + date + ", nameOfActivity=" + nameOfActivity + ", activityTime=" + activityTime
				+ ", id=" + id + "]";
	}

	@Override
	public int compareTo(Activity o) {
		return this.getIdInt().compareTo(o.getIdInt());
	}

}
