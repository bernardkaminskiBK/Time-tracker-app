package sk.berni.timeTrackerApp.menu_sections;

import static sk.berni.timeTrackerApp.utilities.Calculation.addZeroToTime;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.scene.control.Label;
import sk.berni.timeTrackerApp.database.Activity;
import sk.berni.timeTrackerApp.database.DataBase;
import sk.berni.timeTrackerApp.menu_anim_control.MenuSlideControl;

public class MenuAddSection {

	private final JFXTextField textFieldNameOfActivity, textFieldHours, textFieldMinutes, textFieldSeconds;

	private Label nameWarningLabel, hourWarningLabel, minuteWarningLabel, secondsWarningLabel,
			acceptSaveNameAddPaneLabel, acceptSaveTimeAddPaneLabel, saveSuccessAddLabel;

	private static final int UPPER_LIMIT_HOURS = 99;
	private static final int UPPER_LIMIT_MIN = 59;
	private static final int UPPER_LIMIT_SEC = 59;

	private static final int MAX_LENGTH_TEXT = 11;
	private static final int MAX_LENGTH_NUM = 2;

	public MenuAddSection(JFXTextField textFieldNameOfActivity, JFXTextField textFieldHours,
			JFXTextField textFieldMinutes, JFXTextField textFieldSeconds) {

		this.textFieldNameOfActivity = textFieldNameOfActivity;
		this.textFieldHours = textFieldHours;
		this.textFieldMinutes = textFieldMinutes;
		this.textFieldSeconds = textFieldSeconds;

	}

	public void addLabel(Label hourWarningLabel, Label minuteWarningLabel, Label secondsWarningLabel,
			Label nameWarningLabel, Label acceptSaveNameAddPaneLabel, Label acceptSaveTimeAddPaneLabel,
			Label saveSuccessAddLabel) {
		this.hourWarningLabel = hourWarningLabel;
		this.minuteWarningLabel = minuteWarningLabel;
		this.secondsWarningLabel = secondsWarningLabel;
		this.nameWarningLabel = nameWarningLabel;
		this.acceptSaveNameAddPaneLabel = acceptSaveNameAddPaneLabel;
		this.acceptSaveTimeAddPaneLabel = acceptSaveTimeAddPaneLabel;
		this.saveSuccessAddLabel = saveSuccessAddLabel;
	}

	public void menuAddActivities(JFXButton btnSaveDataAddOk, JFXButton btnSaveAddData, JFXButton btnCancelAddData,
			MenuSlideControl msc, DataBase db) {
		btnSaveDataAddOk.setOnAction(event -> {
			saveSuccessAddLabel.setVisible(false);

			String nameInput = validateTextInput(textFieldNameOfActivity, nameWarningLabel);
			String hourInput = validateTimeInput(textFieldHours, UPPER_LIMIT_HOURS, hourWarningLabel);
			String minuteInput = validateTimeInput(textFieldMinutes, UPPER_LIMIT_MIN, minuteWarningLabel);
			String secondsInput = validateTimeInput(textFieldSeconds, UPPER_LIMIT_SEC, secondsWarningLabel);

			if (!nameInput.isEmpty() && !hourInput.isEmpty() && !minuteInput.isEmpty() && !secondsInput.isEmpty()) {
				msc.openAddManuallyData();

				String time = addZeroToTime(Integer.parseInt(hourInput)) + ":"
						+ addZeroToTime(Integer.parseInt(minuteInput)) + ":"
						+ addZeroToTime(Integer.parseInt(secondsInput));

				acceptSaveNameAddPaneLabel.setText(nameInput);
				acceptSaveTimeAddPaneLabel.setText(time);

				btnSaveAddData.setOnAction(e -> {
					Activity activity = new Activity(nameInput, time);
					MenuStatisticsTableSection.activities.add(activity);
					db.addNewActivity(activity);
					saveSuccessAddLabel.setVisible(true);
					msc.closeAddManuallyData();
					clearTextFields();
				});

				btnCancelAddData.setOnAction(e -> msc.closeAddManuallyData());
			}

		});
	}

	private String validateTextInput(JFXTextField inputText, Label nameWarningLabel) {
		nameWarningLabel.setVisible(inputText.getText().isEmpty());

		if (inputText.getText().length() > MAX_LENGTH_TEXT) {
			return inputText.getText().substring(0, MAX_LENGTH_TEXT);
		} else {
			return inputText.getText();
		}
	}

	private String validateTimeInput(JFXTextField input, int limit, Label warning) {
		if (checkOptions(input, limit)) {
			warning.setVisible(true);
		} else {
			warning.setVisible(false);
			return input.getText();
		}
		return "";
	}

	private boolean checkOptions(JFXTextField inputText, int limit) {
		return !inputText.getText().matches("\\d+") || inputText.getText().length() > MAX_LENGTH_NUM
				|| Integer.parseInt(inputText.getText()) > limit;
	}

	public void clearTextFields() {
		textFieldNameOfActivity.clear();
		textFieldHours.clear();
		textFieldMinutes.clear();
		textFieldSeconds.clear();
	}

	public void clearWarningMessages() {
		nameWarningLabel.setVisible(false);
		hourWarningLabel.setVisible(false);
		minuteWarningLabel.setVisible(false);
		secondsWarningLabel.setVisible(false);
		saveSuccessAddLabel.setVisible(false);
	}

}
