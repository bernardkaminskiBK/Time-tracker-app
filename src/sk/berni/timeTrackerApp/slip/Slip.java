package sk.berni.timeTrackerApp.slip;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Slip {

    public void translateTransitionX(Pane pane, double x, double duration) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(duration));
        slide.setNode(pane);
        slide.setToX(x);
        slide.play();
    }

    public void translateTransitionX(AnchorPane anchoPane, double x, double duration) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(duration));
        slide.setNode(anchoPane);
        slide.setToX(x);
        slide.play();
    }

}

