package scenes.mainMenu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainMenuView extends StackPane{
		
	//Menu buttons
	Button playButton;
	Button optionsButton;
	Button highScoreButton;
	Button exitButton;
	Button creditsButton;
	
	public MainMenuView() {
		Label viewLabel = new Label("snackmon");
		viewLabel.setId("snackmon");
		viewLabel.setPadding(new Insets(0, 0, 200, 0));
		
		playButton = new Button("Play");
		optionsButton = new Button("Options");
		highScoreButton = new Button("HighScore");
		exitButton = new Button("Exit");
		creditsButton = new Button("Credits");
		
		VBox buttonPane = new VBox();
		buttonPane.getChildren().addAll(playButton, optionsButton, highScoreButton, creditsButton, exitButton);
		buttonPane.setAlignment(Pos.CENTER);
		buttonPane.setSpacing(10);
		
		VBox overlay = new VBox();
		overlay.getChildren().addAll(viewLabel, buttonPane);
		overlay.setAlignment(Pos.CENTER);
		overlay.setSpacing(30);
		overlay.spacingProperty().set(10);
		overlay.setId("overlay");
		overlay.setMaxWidth(700);
		
		Pane pane = new Pane();
		pane.setId("pane");
		
		this.getChildren().addAll(pane, overlay);
		
		playButton.setId("button");
		optionsButton.setId("button");
		highScoreButton.setId("button");
		creditsButton.setId("button");
		exitButton.setId("button");
	}

}
