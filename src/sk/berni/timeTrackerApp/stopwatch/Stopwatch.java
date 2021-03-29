package sk.berni.timeTrackerApp.stopwatch;

import static sk.berni.timeTrackerApp.utilities.Calculation.addZeroToTime;

import com.jfoenix.controls.JFXButton;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class Stopwatch {

	private final Label stopwatchLabel;
	private Timeline timeline;

	private int hour = 0, mins = 0, secs = 0, millis = 0;
	private boolean start = true;

	private final JFXButton startButton, resetButton;

	public Stopwatch(Label stopwatchCurrentTimeLabel, JFXButton startButton, JFXButton resetButton) {
		this.stopwatchLabel = stopwatchCurrentTimeLabel;
		this.startButton = startButton;
		this.resetButton = resetButton;

		change(stopwatchCurrentTimeLabel);
	}

	private void change(Label label) {
		if (millis == 1000) {
			secs++;
			millis = 0;
		}
		if (secs == 60) {
			mins++;
			secs = 0;
		}
		if (mins == 60) {
			hour++;
			mins = 0;
		}

		String hours = addZeroToTime(hour);
		String minutes = addZeroToTime(mins);
		String seconds = addZeroToTime(secs);
		String milliseconds = (((millis / 10) == 0) ? "00" : (((millis / 100) == 0) ? "0" : "")) + millis++;

		label.setText(hours + ":" + minutes + ":" + seconds + "." + milliseconds);

	}

	public void start(Label saveRuleInfoLabel, Label saveSuccessTimeLabel) {
		stopwatchLabel.setText("00:00:00.000");

		timeline = new Timeline(new KeyFrame(Duration.millis(1), event -> change(stopwatchLabel)));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setAutoReverse(false);

		startButton.setText("Start");
		startButton.setOnAction(event -> {
			if (start) {
				saveRuleInfoLabel.setVisible(false);
				saveSuccessTimeLabel.setVisible(false);
				timeline.play();
				start = false;
				startButton.setText("Stop");
			} else {
				saveRuleInfoLabel.setVisible(false);
				timeline.pause();
				start = true;
				startButton.setText("Start");
			}
		});

	}

	public void reset(Label saveRuleInfoLabel, Label saveSuccessTimeLabel) {
		resetButton.setText("Reset");
		resetButton.setOnAction(event -> {
			saveRuleInfoLabel.setVisible(false);
			saveSuccessTimeLabel.setVisible(false);
			startButton.setText("Start");
			hour = 0;
			mins = 0;
			secs = 0;
			millis = 0;
			timeline.pause();
			stopwatchLabel.setText("00:00:00.000");
			if (!start) {
				start = true;
			}
		});
	}

	public void pause() {
		timeline.pause();
		start = true;
		startButton.setText("Start");
	}

	public int getHour() {
		return hour;
	}

	public int getMin() {
		return mins;
	}

	public int getSecs() {
		return secs;
	}

}
