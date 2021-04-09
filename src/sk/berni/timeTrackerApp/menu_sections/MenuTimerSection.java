package sk.berni.timeTrackerApp.menu_sections;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sk.berni.timeTrackerApp.database.Activity;
import sk.berni.timeTrackerApp.database.DataBase;
import sk.berni.timeTrackerApp.stopwatch.Stopwatch;
import sk.berni.timeTrackerApp.utilities.Calculation;

public class MenuTimerSection {

	private final AnchorPane slider;
	private final Pane timerPane, saveDataPane;
	private final JFXTextField nameOfActivity;

	private final Stopwatch stopwatch;
	private final DataBase db;
	private final Label saveSuccessTimeLabel;

	private Label nameWarningTimerLabel;

	private static final int MAX_LENGTH_TEXT = 11;

	public MenuTimerSection(DataBase db, AnchorPane slider, Pane timerPane, Pane saveDataPane,
			JFXTextField nameOfActivity, Stopwatch stopwatch, Label saveSuccessTimeLabel) {
		this.db = db;
		this.slider = slider;
		this.timerPane = timerPane;
		this.saveDataPane = saveDataPane;
		this.nameOfActivity = nameOfActivity;
		this.stopwatch = stopwatch;
		this.saveSuccessTimeLabel = saveSuccessTimeLabel;
	}

	public void openSavePane(JFXButton btnSaveStopwatch, JFXButton btnAcceptSave, Label saveRuleInfo,
			Label saveSuccessTimeLabel, Label nameWarningTimerLabel) {
		this.nameWarningTimerLabel = nameWarningTimerLabel;

		btnSaveStopwatch.setOnAction(event -> {
			saveSuccessTimeLabel.setVisible(false);
			saveRuleInfo.setVisible(true);
			if (stopwatch.getHour() != 0 || stopwatch.getMin() != 0 || stopwatch.getSecs() != 0) {
				disablePanes(true);
				stopwatch.pause();
				saveRuleInfo.setVisible(false);
			}
		});

		btnAcceptSave.setOnAction(event -> {
			if (nameOfActivity.getText().isEmpty()) {
				nameWarningTimerLabel.setVisible(true);
				disablePanes(true);
			} else {
				nameWarningTimerLabel.setVisible(false);
				disablePanes(false);
				addActivityToDB();
				saveSuccessTimeLabel.setVisible(true);
			}
		});

	}

	public void cancelSavePane(JFXButton btnCancelSave) {
		btnCancelSave.setOnAction(event -> {
			nameWarningTimerLabel.setVisible(false);
			disablePanes(false);
			nameOfActivity.clear();
		});
	}

	public void hideSuccessSavedText() {
		saveSuccessTimeLabel.setVisible(false);
	}

	private void disablePanes(boolean val) {
		saveDataPane.setVisible(val);
		timerPane.setDisable(val);
		slider.setDisable(val);
	}

	private void addActivityToDB() {
		String time = Calculation.addZeroToTime(stopwatch.getHour()) + ":"
				+ Calculation.addZeroToTime(stopwatch.getMin()) + ":" + Calculation.addZeroToTime(stopwatch.getSecs());

		if (nameOfActivity.getText().length() > MAX_LENGTH_TEXT) {
			String activityName = nameOfActivity.getText().substring(0, MAX_LENGTH_TEXT);
			textInput(activityName, time);
		} else {
			textInput(nameOfActivity.getText(), time);
		}

	}

	private void textInput(String inputText, String time) {
		Activity activity = new Activity(inputText.trim(), time);
		MenuStatisticsTableSection.activities.add(activity);
		db.addNewActivity(activity);
		nameOfActivity.clear();
	}

}
