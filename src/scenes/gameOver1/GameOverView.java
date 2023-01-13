package scenes.gameOver1;

import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import scenes.game.Labygen;

public class GameOverView extends StackPane {

	// Menu buttons
	Button continueButton;
	BooleanBinding nameEntered;
	TextField nameField;
	Label scoreLabel;

	public GameOverView() {

		// Label: gameover, score, playerName
		Label viewLabel = new Label("gameover");
		viewLabel.setId("snackmon");
		viewLabel.setPadding(new Insets(0, 0, 10, 0));
		scoreLabel = new Label("Score:    " + Labygen.getScore());
		scoreLabel.setId("label1");
		Label playerName = new Label("User Name:");
		playerName.setId("label1");

		// Textfield to enter player name
		nameField = new TextField();
		nameEntered = nameField.textProperty().isNotEmpty().and(nameField.textProperty().length().greaterThan(2));

		// Button: Continue
		continueButton = new Button("Continue");
		continueButton.disableProperty().bind(nameEntered.not());

		// GridPane: playerName, playerField
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(50);
		grid.setPadding(new Insets(180, 25, 25, 25));
		for (int i = 0; i < 2; i++) {
			RowConstraints row = new RowConstraints(20, 30, 300);
			grid.getRowConstraints().add(row);
		}
		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPercentWidth(25);
		grid.getColumnConstraints().addAll(col1);

		// Credits-Label
		grid.add(playerName, 0, 0);
		grid.add(nameField, 1, 0);

		// VBox: scoreLabel, continueButton
		VBox overlay = new VBox();
		overlay.getChildren().addAll(viewLabel, scoreLabel, grid, continueButton);
		overlay.setAlignment(Pos.CENTER);
		overlay.setSpacing(30);
		overlay.spacingProperty().set(10);
		overlay.setId("overlay");
		overlay.setMaxWidth(700);

		Pane pane = new Pane();
		pane.setId("gameOver");

		this.getChildren().addAll(pane, overlay);

		continueButton.setId("button");
	}

}
