package scenes.transitions;

import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class OutgoingTransition extends ScreenTransition {

	protected void animateTransition(Scene scene, StackPane transitionView, Pane fromView, Pane toView) {

		// moves top view to the back of the stackpane
		toView.toBack();

		// animation of Transition
		TranslateTransition animation = new TranslateTransition();
		animation.setNode(fromView);
		animation.setDuration(Duration.millis(400));

		// defines end-coordinate of animation(end of screen-width)
		animation.setToX(scene.getWidth());

		// wraps up transition
		animation.setOnFinished(event -> {
			// sets toView as root
			completeTransition(scene, fromView, toView);
			// sets coordinates of old view to zero for future transitions
			fromView.translateXProperty().set(0);
		});

		animation.playFromStart();

	}
}