package sk.berni.timeTrackerApp.menu_sections;

import com.jfoenix.controls.JFXButton;

import javafx.scene.control.Label;
import sk.berni.timeTrackerApp.database.DataBase;
import sk.berni.timeTrackerApp.menu_anim_control.MenuSlideControl;

public class MenuSettingsSection {

	private final JFXButton btnDeleteAllRecords, btnDeleteAllRecordsNo, btnDeleteAllRecordsYes;
	private final Label deleteWasSuccessLabel, atentionDeletSettingLabel;

	public MenuSettingsSection(JFXButton btnDeleteAllRecords, JFXButton btnDeleteAllRecordsNo,
			JFXButton btnDeleteAllRecordsYes, Label deleteWasSuccessLabel, Label atentionDeletSettingLabel) {
		this.btnDeleteAllRecords = btnDeleteAllRecords;
		this.btnDeleteAllRecordsNo = btnDeleteAllRecordsNo;
		this.btnDeleteAllRecordsYes = btnDeleteAllRecordsYes;
		this.deleteWasSuccessLabel = deleteWasSuccessLabel;
		this.atentionDeletSettingLabel = atentionDeletSettingLabel;
	}

	public void deleteAllRecordsFromDb(DataBase db, MenuSlideControl msc) {
		btnDeleteAllRecords.setOnAction(event -> {
			if (db.getAllActivities().size() != 0) {
				msc.openDeleteAllRecordsPane();
			} else {
				atentionDeletSettingLabel.setVisible(true);
			}
			btnDeleteAllRecordsYes.setOnAction(e -> {
				db.deleteAllRecords();
				MenuStatisticsTableSection.activities.clear();
				deleteWasSuccessLabel.setVisible(true);
				msc.closeDeleteAllRecordsPane();
			});
			btnDeleteAllRecordsNo.setOnAction(e -> msc.closeDeleteAllRecordsPane());
		});
	}
}
