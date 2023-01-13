package scenes.pause;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class PauseView extends StackPane{
		
	//Menu buttons
	Button continueButton;
	Button optionsButton;
	Button returnMMButton;
	
	public PauseView() {
		
		//label: snackmon
		Label viewLabel = new Label("snackmon");
		viewLabel.setId("snackmon");
		viewLabel.setPadding(new Insets(0, 0, 200, 0));
		
		//button
		continueButton = new Button("Continue");
		continueButton.setId("button");
		optionsButton = new Button("Options");
		optionsButton.setId("button");
		returnMMButton = new Button("Return to MM");
		returnMMButton.setId("button");
		
		//Vbox: button
		VBox buttonPane = new VBox();
		buttonPane.getChildren().addAll(continueButton, optionsButton, returnMMButton);
		buttonPane.setAlignment(Pos.CENTER);
		buttonPane.setSpacing(10);
		
		//VBox: partially transperant overlay (Label+button)
		VBox overlay = new VBox();
		overlay.getChildren().addAll(viewLabel, buttonPane);
		overlay.setAlignment(Pos.CENTER);
		overlay.setSpacing(30);
		overlay.spacingProperty().set(10);
		overlay.setId("overlay");
		overlay.setMaxWidth(700);
		
		//backgroundPane
		Pane pane = new Pane();
		pane.setId("pane");
		
		//StackPane: background, overlay
		this.getChildren().addAll(pane, overlay);
		
	}

}
