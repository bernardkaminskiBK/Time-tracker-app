package sk.berni.timeTrackerApp.menu_sections;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;
import sk.berni.timeTrackerApp.database.Activity;
import sk.berni.timeTrackerApp.database.DataBase;

public class MenuStatisticsTableSection {

	private final JFXTreeTableView<Activity> treeTableView;
	private final JFXTextField searchDataTableTextField;

	private JFXTreeTableColumn<Activity, String> dateCol, activityCol, timeCol, removeCol;

	public static ObservableList<Activity> activities = FXCollections.observableArrayList();

	private final DataBase db;
	private final MenuStatisticsSection mss;

	public MenuStatisticsTableSection(JFXTreeTableView<Activity> treeTableView, JFXTextField searchDataTableTextField,
			DataBase db, MenuStatisticsSection mss) {
		this.db = db;
		this.treeTableView = treeTableView;
		this.searchDataTableTextField = searchDataTableTextField;
		this.mss = mss;
		tableView();
	}

	private void tableView() {
		dateColumn();
		activityColumn();
		timeColumn();
		removeColumn();

		addTreeItemColumns();
		searchDataTable();
	}

	private void dateColumn() {
		dateCol = new JFXTreeTableColumn<>("Date");
		dateCol.setPrefWidth(150);
		dateCol.setCellValueFactory(param -> param.getValue().getValue().getSimpleDateProperty());
	}

	private void activityColumn() {
		activityCol = new JFXTreeTableColumn<>("Activity");
		activityCol.setPrefWidth(100);
		activityCol.setCellValueFactory(param -> param.getValue().getValue().getSimpleNameOfActivityProperty());
	}

	private void timeColumn() {
		timeCol = new JFXTreeTableColumn<>("Time");
		timeCol.setPrefWidth(100);
		timeCol.setCellValueFactory(param -> param.getValue().getValue().getSimpleActivityTimeProperty());
	}

	private void removeColumn() {
		removeCol = new JFXTreeTableColumn<>("Delete");
		removeCol.setMinWidth(50);

		Callback<TreeTableColumn<Activity, String>, TreeTableCell<Activity, String>> cellFactory = new Callback<TreeTableColumn<Activity, String>, TreeTableCell<Activity, String>>() {
			@Override
			public TreeTableCell<Activity, String> call(TreeTableColumn<Activity, String> param) {
				return new TreeTableCell<Activity, String>() {

					final JFXButton btn = new JFXButton("Delete");

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						btn.setStyle("-fx-background-color: #0292b5; -fx-text-fill: white");
						if (empty) {
							setGraphic(null);
						} else {
							btn.setOnAction((ActionEvent event) -> {
								Activity activity = getTreeTableRow().getItem();
								activities.remove(activity);
								db.removeActivity(activity);
								mss.setComboBoxItems();
							});
							setGraphic(btn);
						}
						setText(null);
					}
				};
			}
		};
		removeCol.setCellFactory(cellFactory);
	}

	private void addTreeItemColumns() {
		final TreeItem<Activity> root = new RecursiveTreeItem<>(activities, RecursiveTreeObject::getChildren);
		treeTableView.getColumns().setAll(dateCol, activityCol, timeCol, removeCol);
		treeTableView.setRoot(root);
		activities.addAll(db.getAllActivities());
		treeTableView.setShowRoot(false);
	}

	private void searchDataTable() {
		searchDataTableTextField.textProperty()
				.addListener((observable, oldValue,
						newValue) -> treeTableView.setPredicate(user -> user.getValue().getDate().contains(newValue)
								|| user.getValue().getNameOfActivity().contains(newValue)
								|| user.getValue().getActivityTime().contains(newValue)));
	}

}
