package scenes.transitions;

import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class IncomingTransition extends ScreenTransition{
	
	protected void animateTransition(Scene scene, StackPane transitionView, Pane fromView, Pane toView) {

		toView.toFront();
		toView.translateXProperty().set(scene.getWidth());	

		TranslateTransition animation = new TranslateTransition();
		animation.setNode(toView);
		animation.setDuration(Duration.millis(400));
		animation.setToX(0);

		animation.setOnFinished(event -> {
			completeTransition(scene, fromView, toView);
		});

		animation.playFromStart();
	}
}