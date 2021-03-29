package sk.berni.timeTrackerApp;

import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sk.berni.timeTrackerApp.database.Activity;
import sk.berni.timeTrackerApp.database.DataBase;
import sk.berni.timeTrackerApp.menu_anim_control.MenuSlideControl;
import sk.berni.timeTrackerApp.menu_sections.MenuAddSection;
import sk.berni.timeTrackerApp.menu_sections.MenuSettingsSection;
import sk.berni.timeTrackerApp.menu_sections.MenuStatisticsSection;
import sk.berni.timeTrackerApp.menu_sections.MenuStatisticsTableSection;
import sk.berni.timeTrackerApp.menu_sections.MenuTimerSection;
import sk.berni.timeTrackerApp.stopwatch.Stopwatch;
import sk.berni.timeTrackerApp.utilities.PdfGenerator;

public class TimeTrackerController implements Initializable {

	@FXML
	private ImageView Exit, importToPdf;

	@FXML
	private AnchorPane slider;

	@FXML
	private Pane mainPane, timerPane, addPane, statisticsPane, settingsPane, saveDataPane;

	@FXML
	private Pane tableViewPane, acceptSaveDataAddPane, importStatsInfoPane, deleteAllRecordsPane;

	@FXML
	private JFXTreeTableView<Activity> treeTableView;

	@FXML
	private Label stopwatchLabel, saveRuleInfoLabel, hourWarningLabel, minuteWarningLabel, secondsWarningLabel,
			nameWarningLabel, warningInfoImportToPdfLabel, importSuccessInfoLabel, nameOfActivityToImportPdfLabel;

	@FXML
	private JFXButton btnStartStopwatch, btnResetStopwatch, btnSaveStopwatch, btnAcceptSave, btnCancelSave,
			btnCalculate;

	@FXML
	private JFXTextField nameOfActivityTextField, searchDataTableTextField;

	@FXML
	private JFXTextField textFieldNameOfActivity, textFieldHours, textFieldMinutes, textFieldSeconds;

	@FXML
	private Label acceptSaveNameAddPaneLabel, acceptSaveTimeAddPaneLabel, saveSuccessTimeLabel, saveSuccessAddLabel,
			nameWarningTimerLabel, deleteWasSuccessLabel, atentionDeletSettingLabel, warningInfoToCalcStatsLabel;

	@FXML
	private Label totalDaysLabel, totalTimeLabel, averageTimeLabel;

	@FXML
	private JFXButton btnSaveDataAddOk, btnSaveAddData, btnCancelAddData, btnYesImportToPdf, btnNoImportToPdf,
			btnDeleteAllRecords, btnDeleteAllRecordsNo, btnDeleteAllRecordsYes;

	@FXML
	private JFXComboBox<String> comboBox;

	@FXML
	private JFXRadioButton radioButtonFromFirstToLastDate, radioButtonFromFirstToActualDate;

	private MenuSlideControl msc;
	private Stopwatch stopwatch;
	private MenuTimerSection mts;
	private MenuAddSection mas;
	private MenuStatisticsSection mss;
	private MenuSettingsSection settingSection;

	private DataBase db;

	@FXML
	private void openMenuByClick() {
		msc.openMainMenu();
		searchDataTableTextField.clear();
		msc.hideMenuElements();
		mas.clearWarningMessages();
		mas.clearTextFields();
		saveRuleInfoLabel.setVisible(false);
		mts.hideSuccessSavedText();
	}

	@FXML
	private void closeMenuByClick() {
		msc.closeMainMenu();
		searchDataTableTextField.clear();
		saveRuleInfoLabel.setVisible(false);
		mas.clearWarningMessages();
		mas.clearTextFields();
		mts.hideSuccessSavedText();
	}

	@FXML
	private void openTimer() {
		msc.openTimer();
		searchDataTableTextField.clear();
		saveRuleInfoLabel.setVisible(false);
		mas.clearWarningMessages();
		mas.clearTextFields();
		mts.hideSuccessSavedText();
	}

	@FXML
	private void openAdd() {
		msc.openAdd();
		searchDataTableTextField.clear();
		saveRuleInfoLabel.setVisible(false);
		mas.clearWarningMessages();
		mas.clearTextFields();
		mts.hideSuccessSavedText();
	}

	@FXML
	private void openStatistics() {
		mss.clearStatsLabels();
		msc.openStatistics();
		searchDataTableTextField.clear();
		saveRuleInfoLabel.setVisible(false);
		warningInfoImportToPdfLabel.setVisible(false);
		importSuccessInfoLabel.setVisible(false);
		warningInfoToCalcStatsLabel.setVisible(false);
		mas.clearWarningMessages();
		mas.clearTextFields();
		mts.hideSuccessSavedText();

		mss.setComboBoxItems();
		msc.closeImportToPdfPane();
		msc.closeDeleteAllRecordsPane();
	}

	@FXML
	private void openTableView() {
		msc.openTableView();
		searchDataTableTextField.clear();
		saveRuleInfoLabel.setVisible(false);
		mas.clearWarningMessages();
		mas.clearTextFields();
		msc.closeImportToPdfPane();
		warningInfoImportToPdfLabel.setVisible(false);
		importSuccessInfoLabel.setVisible(false);
		warningInfoToCalcStatsLabel.setVisible(false);
	}

	@FXML
	private void closeTableView() {
		msc.closeTableView();
		searchDataTableTextField.clear();
		saveRuleInfoLabel.setVisible(false);
		mas.clearWarningMessages();
		mas.clearTextFields();
	}

	@FXML
	private void openSettings() {
		msc.openSettings();
		searchDataTableTextField.clear();
		saveRuleInfoLabel.setVisible(false);
		mas.clearWarningMessages();
		mas.clearTextFields();
		mts.hideSuccessSavedText();
		deleteWasSuccessLabel.setVisible(false);
		atentionDeletSettingLabel.setVisible(false);
	}

	private void menuControl() {
		msc = new MenuSlideControl(slider, mainPane, timerPane, addPane, statisticsPane, settingsPane, tableViewPane,
				acceptSaveDataAddPane, importStatsInfoPane, deleteAllRecordsPane);
		msc.hideMenuElements();
		saveRuleInfoLabel.setVisible(false);
	}

	private void timerSectionSaveData() {
		mts = new MenuTimerSection(db, slider, timerPane, saveDataPane, nameOfActivityTextField, stopwatch,
				saveSuccessTimeLabel);
		mts.openSavePane(btnSaveStopwatch, btnAcceptSave, saveRuleInfoLabel, saveSuccessTimeLabel,
				nameWarningTimerLabel);
		mts.cancelSavePane(btnCancelSave);
		saveRuleInfoLabel.setVisible(false);
	}

	private void addSection() {
		mas = new MenuAddSection(textFieldNameOfActivity, textFieldHours, textFieldMinutes, textFieldSeconds);
		mas.addLabel(hourWarningLabel, minuteWarningLabel, secondsWarningLabel, nameWarningLabel,
				acceptSaveNameAddPaneLabel, acceptSaveTimeAddPaneLabel, saveSuccessAddLabel);
		mas.menuAddActivities(btnSaveDataAddOk, btnSaveAddData, btnCancelAddData, msc, db);
	}

	private void statisticsSection() {
		mss = new MenuStatisticsSection(db, comboBox);
		mss.addLabels(totalDaysLabel, totalTimeLabel, averageTimeLabel, warningInfoToCalcStatsLabel);

		btnCalculate.setOnAction(event -> {
			mss.showStats(comboBox.getValue());
			warningInfoImportToPdfLabel.setVisible(false);
			importSuccessInfoLabel.setVisible(false);
		});
		importToPdfSection();
	}

	private void statisticsTableSection() {
		new MenuStatisticsTableSection(treeTableView, searchDataTableTextField, db, mss);
	}

	private void settingsSection() {
		settingSection = new MenuSettingsSection(btnDeleteAllRecords, btnDeleteAllRecordsNo, btnDeleteAllRecordsYes,
				deleteWasSuccessLabel, atentionDeletSettingLabel);
		settingSection.deleteAllRecordsFromDb(db, msc);
	}

	private void timer() {
		stopwatch = new Stopwatch(stopwatchLabel, btnStartStopwatch, btnResetStopwatch);
		stopwatch.start(saveRuleInfoLabel, saveSuccessTimeLabel);
		stopwatch.reset(saveRuleInfoLabel, saveSuccessTimeLabel);
	}

	private void exit() {
		Exit.setOnMouseClicked(event -> System.exit(0));
	}

	private void importToPdfSection() {
		importToPdf.setOnMouseClicked(event -> {
			warningInfoToCalcStatsLabel.setVisible(false);
			importSuccessInfoLabel.setVisible(false);
			warningInfoImportToPdfLabel.setVisible(false);
			if (comboBox.getValue() != null) {
				setRadioButtonsText();
				nameOfActivityToImportPdfLabel.setText(comboBox.getValue());
				msc.openImportToPdfPane();
				btnYesImportToPdf.setOnAction(event1 -> {
					PdfGenerator pdfGenerator = new PdfGenerator(db.getActivitiesByName(comboBox.getValue()));
					pdfGenerator.setChooseOption(radioButtonFromFirstToLastDate.isSelected());
					pdfGenerator.createPdf();
					msc.closeImportToPdfPane();
					importSuccessInfoLabel.setVisible(true);
					openFile(pdfGenerator.getFilePath());
				});
				btnNoImportToPdf.setOnAction(event1 -> msc.closeImportToPdfPane());
			} else {
				warningInfoImportToPdfLabel.setVisible(true);
			}
		});
	}

	private void setRadioButtonsText() {
		String dateFromFirst = db.getActivitiesByName(comboBox.getValue()).get(0).getDate().substring(0, 10);
		String dateToLast = db.getActivitiesByName(comboBox.getValue())
				.get(db.getActivitiesByName(comboBox.getValue()).size() - 1).getDate().substring(0, 10);
		String dateToActual = new SimpleDateFormat("dd.MM.yyyy").format(Timestamp.from(Instant.now()));

		if (!dateToLast.contentEquals(dateToActual)) {
			radioButtonFromFirstToLastDate.setVisible(true);
			radioButtonFromFirstToActualDate.setVisible(true);
			radioButtonFromFirstToLastDate.setText(dateFromFirst + " - " + dateToLast);
			radioButtonFromFirstToActualDate.setText(dateFromFirst + " - " + dateToActual);
		} else {
			radioButtonFromFirstToLastDate.setVisible(false);
			radioButtonFromFirstToActualDate.setVisible(false);
		}

	}

	private void openFile(String filePath) {
		try {
			File file = new File(filePath);
			if (!Desktop.isDesktopSupported()) {
				System.out.println("not supported");
				return;
			}
			Desktop desktop = Desktop.getDesktop();
			if (file.exists())
				desktop.open(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		db = new DataBase();
		menuControl();
		timer();
		timerSectionSaveData();
		addSection();
		statisticsSection();
		statisticsTableSection();
		settingsSection();
		exit();
	}

}
