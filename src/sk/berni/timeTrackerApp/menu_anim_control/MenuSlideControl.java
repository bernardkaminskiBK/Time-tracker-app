package sk.berni.timeTrackerApp.menu_anim_control;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sk.berni.timeTrackerApp.slip.Slip;

public class MenuSlideControl {

	private final AnchorPane slider;
	private final Pane mainPane, timerPane, addPane, statisticsPane, settingsPane, tableViewPane, acceptSaveDataAddPane,
			importStatsInfoPane, deleteAllRecordsPane;

	private static final double SLIDE_SPEED_OPEN = 0.28;
	private static final double SLIDE_SPEED_CLOSE = 0.1;
	private static final double PANE_ITEM_HIDE = 525;
	private static final double PANE_ITEM_SHOW = -2.5;
	private static final double SLIDER_ITEM_SHOW = -176;

	private final Slip slip = new Slip();

	public MenuSlideControl(AnchorPane slider, Pane mainPane, Pane timerPane, Pane addPane, Pane statisticsPane,
			Pane settingsPane, Pane tableViewPane, Pane acceptSaveDataAddPane, Pane importStatsInfoPane,
			Pane deleteAllRecordsPane) {
		this.tableViewPane = tableViewPane;
		this.slider = slider;
		this.mainPane = mainPane;
		this.timerPane = timerPane;
		this.addPane = addPane;
		this.statisticsPane = statisticsPane;
		this.settingsPane = settingsPane;
		this.acceptSaveDataAddPane = acceptSaveDataAddPane;
		this.importStatsInfoPane = importStatsInfoPane;
		this.deleteAllRecordsPane = deleteAllRecordsPane;
	}

	public void hideMenuElements() {
		slider.setTranslateX(SLIDER_ITEM_SHOW);
		timerPane.setTranslateX(PANE_ITEM_HIDE);
		addPane.setTranslateX(PANE_ITEM_HIDE);
		statisticsPane.setTranslateX(PANE_ITEM_HIDE);
		settingsPane.setTranslateX(PANE_ITEM_HIDE);
		tableViewPane.setTranslateX(PANE_ITEM_HIDE);
		acceptSaveDataAddPane.setTranslateX(PANE_ITEM_HIDE);
		importStatsInfoPane.setTranslateX(PANE_ITEM_HIDE);
		deleteAllRecordsPane.setTranslateX(PANE_ITEM_HIDE);
	}

	public void openMainMenu() {
		slip.translateTransitionX(timerPane, PANE_ITEM_SHOW, SLIDE_SPEED_OPEN);
		slip.translateTransitionX(mainPane, 702, SLIDE_SPEED_CLOSE);
		slip.translateTransitionX(slider, 0, SLIDE_SPEED_CLOSE);
		slip.translateTransitionX(tableViewPane, PANE_ITEM_HIDE, SLIDE_SPEED_CLOSE);
		closeAddManuallyData();
		closeDeleteAllRecordsPane();
	}

	public void closeMainMenu() {
		slip.translateTransitionX(mainPane, PANE_ITEM_SHOW, SLIDE_SPEED_OPEN);
		slip.translateTransitionX(slider, SLIDER_ITEM_SHOW, SLIDE_SPEED_OPEN);
		slip.translateTransitionX(timerPane, PANE_ITEM_HIDE, SLIDE_SPEED_CLOSE);
		slip.translateTransitionX(addPane, PANE_ITEM_HIDE, SLIDE_SPEED_CLOSE);
		slip.translateTransitionX(statisticsPane, PANE_ITEM_HIDE, SLIDE_SPEED_CLOSE);
		slip.translateTransitionX(settingsPane, PANE_ITEM_HIDE, SLIDE_SPEED_CLOSE);
		slip.translateTransitionX(tableViewPane, PANE_ITEM_HIDE, SLIDE_SPEED_CLOSE);
		slip.translateTransitionX(tableViewPane, PANE_ITEM_HIDE, SLIDE_SPEED_CLOSE);
		closeAddManuallyData();
		closeDeleteAllRecordsPane();
	}

	public void openTimer() {
		slip.translateTransitionX(timerPane, PANE_ITEM_SHOW, SLIDE_SPEED_OPEN);
		slip.translateTransitionX(statisticsPane, PANE_ITEM_HIDE, SLIDE_SPEED_CLOSE);
		slip.translateTransitionX(addPane, PANE_ITEM_HIDE, SLIDE_SPEED_CLOSE);
		slip.translateTransitionX(settingsPane, PANE_ITEM_HIDE, SLIDE_SPEED_CLOSE);
		slip.translateTransitionX(tableViewPane, PANE_ITEM_HIDE, SLIDE_SPEED_CLOSE);
		closeAddManuallyData();
		closeDeleteAllRecordsPane();
	}

	public void openAdd() {
		slip.translateTransitionX(addPane, PANE_ITEM_SHOW, SLIDE_SPEED_OPEN);
		slip.translateTransitionX(statisticsPane, PANE_ITEM_HIDE, SLIDE_SPEED_CLOSE);
		slip.translateTransitionX(timerPane, PANE_ITEM_HIDE, SLIDE_SPEED_CLOSE);
		slip.translateTransitionX(settingsPane, PANE_ITEM_HIDE, SLIDE_SPEED_CLOSE);
		slip.translateTransitionX(tableViewPane, PANE_ITEM_HIDE, SLIDE_SPEED_CLOSE);
		closeAddManuallyData();
		closeDeleteAllRecordsPane();
	}

	public void openStatistics() {
		slip.translateTransitionX(statisticsPane, PANE_ITEM_SHOW, SLIDE_SPEED_OPEN);
		slip.translateTransitionX(timerPane, PANE_ITEM_HIDE, SLIDE_SPEED_CLOSE);
		slip.translateTransitionX(addPane, PANE_ITEM_HIDE, SLIDE_SPEED_CLOSE);
		slip.translateTransitionX(settingsPane, PANE_ITEM_HIDE, SLIDE_SPEED_CLOSE);
		slip.translateTransitionX(tableViewPane, PANE_ITEM_HIDE, SLIDE_SPEED_CLOSE);
		closeAddManuallyData();
		closeDeleteAllRecordsPane();
	}

	public void openSettings() {
		slip.translateTransitionX(settingsPane, PANE_ITEM_SHOW, SLIDE_SPEED_OPEN);
		slip.translateTransitionX(timerPane, PANE_ITEM_HIDE, SLIDE_SPEED_CLOSE);
		slip.translateTransitionX(addPane, PANE_ITEM_HIDE, SLIDE_SPEED_CLOSE);
		slip.translateTransitionX(statisticsPane, PANE_ITEM_HIDE, SLIDE_SPEED_CLOSE);
		slip.translateTransitionX(tableViewPane, PANE_ITEM_HIDE, SLIDE_SPEED_CLOSE);
		closeAddManuallyData();
		closeDeleteAllRecordsPane();
	}

	public void openAddManuallyData() {
		slip.translateTransitionX(acceptSaveDataAddPane, PANE_ITEM_SHOW, SLIDE_SPEED_OPEN);
	}

	public void closeAddManuallyData() {
		slip.translateTransitionX(acceptSaveDataAddPane, PANE_ITEM_HIDE, SLIDE_SPEED_CLOSE);
	}

	public void openTableView() {
		slip.translateTransitionX(tableViewPane, PANE_ITEM_SHOW, SLIDE_SPEED_OPEN);
	}

	public void closeTableView() {
		slip.translateTransitionX(tableViewPane, PANE_ITEM_HIDE, SLIDE_SPEED_CLOSE);
	}

	public void openImportToPdfPane() {
		slip.translateTransitionX(importStatsInfoPane, PANE_ITEM_SHOW, SLIDE_SPEED_OPEN);
	}

	public void closeImportToPdfPane() {
		slip.translateTransitionX(importStatsInfoPane, PANE_ITEM_HIDE, SLIDE_SPEED_CLOSE);
	}

	public void openDeleteAllRecordsPane() {
		slip.translateTransitionX(deleteAllRecordsPane, PANE_ITEM_SHOW, SLIDE_SPEED_OPEN);
	}

	public void closeDeleteAllRecordsPane() {
		slip.translateTransitionX(deleteAllRecordsPane, PANE_ITEM_HIDE, SLIDE_SPEED_CLOSE);
	}

}
