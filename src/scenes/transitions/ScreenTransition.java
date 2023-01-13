package scenes.transitions;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public abstract class ScreenTransition {
	
	private StackPane transitionView;
	
	//animates SceneTransition 
	//@param scene: scene of transition
	//@param fromView: old view
	//@param toView: new view
	public void animate(Scene scene, Pane fromView, Pane toView) {
		//prepares Transition
		prepareAnimation(scene, fromView, toView);
		//animation of Transition
		animateTransition(scene, transitionView, fromView, toView);
	}
	
	//animation definition
	protected abstract void animateTransition(Scene scene, StackPane transitionView, Pane fromView, Pane toView);
	
	//sets space of transition: moves new pane over old pane within a stackpane
	private void prepareAnimation(Scene scene, Pane fromView, Pane toView) {
		transitionView = new StackPane();
		//sets stackpane as root-Scene 
		scene.setRoot(transitionView);
		//Stackpane: new view on top of old view
		transitionView.getChildren().addAll(fromView, toView);
	}
	
	//completes Transition
	protected void completeTransition(Scene scene, Pane fromView, Pane toView) {
		//removes old and new view from stackpane
		transitionView.getChildren().remove(fromView);
		transitionView.getChildren().remove(toView);
		//sets new view as root-scene
		scene.setRoot(toView);
		
	}
}

