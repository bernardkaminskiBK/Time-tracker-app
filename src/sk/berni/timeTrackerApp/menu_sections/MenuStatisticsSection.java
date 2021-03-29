package sk.berni.timeTrackerApp.menu_sections;

import static sk.berni.timeTrackerApp.utilities.StatsCalculation.statsAverage;
import static sk.berni.timeTrackerApp.utilities.StatsCalculation.statsTotalDays;
import static sk.berni.timeTrackerApp.utilities.StatsCalculation.statsTotalTime;

import com.jfoenix.controls.JFXComboBox;

import javafx.scene.control.Label;
import sk.berni.timeTrackerApp.database.DataBase;

public class MenuStatisticsSection {

	private final DataBase db;
	private final JFXComboBox<String> comboBox;

	private Label totalDaysLabel, totalTimeLabel, averageTimeLabel, warningInfoToCalcStatsLabel;

	public MenuStatisticsSection(DataBase db, JFXComboBox<String> comboBox) {
		this.db = db;
		this.comboBox = comboBox;
	}

	public void addLabels(Label totalDaysLabel, Label totalTimeLabel, Label averageTimeLabel,
			Label warningInfoToCalcStatsLabel) {
		this.totalDaysLabel = totalDaysLabel;
		this.totalTimeLabel = totalTimeLabel;
		this.averageTimeLabel = averageTimeLabel;
		this.warningInfoToCalcStatsLabel = warningInfoToCalcStatsLabel;
	}

	public void showStats(String name) {
		warningInfoToCalcStatsLabel.setVisible(false);
		setTotalTime(name);
	}

	private void setTotalTime(String nameOfActivity) {
		totalTimeLabel.setText(statsTotalTime(db.getActivitiesByName(nameOfActivity)));
		setTotalDays(nameOfActivity);
		setAverage();
	}

	private void setAverage() {
		averageTimeLabel.setText(statsAverage());
	}

	private void setTotalDays(String nameOfActivity) {
		if (!db.getAllActivities().isEmpty()) {
			try {
				totalDaysLabel.setText(statsTotalDays(db.getActivitiesByName(nameOfActivity)));
			} catch (IndexOutOfBoundsException exception) {
				System.out.println(
						"The value of the firstActualDataFromDB variable is empty, but does not matter, it is all right. :-)");
				warningInfoToCalcStatsLabel.setVisible(true);
			}
		} else {
			totalDaysLabel.setText("0");
		}
	}

	public void clearStatsLabels() {
		totalDaysLabel.setText("0");
		totalTimeLabel.setText("00:00:00");
		averageTimeLabel.setText("00:00:00");
	}

	public void setComboBoxItems() {
		clearStatsLabels();
		comboBox.getItems().clear();
		for (String nameOfActivity : db.getAllActivityNames()) {
			comboBox.getItems().add(nameOfActivity);
		}
	}

}
