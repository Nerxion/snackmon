package scenes.highScore;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class HighScoreView extends StackPane{
	
	Button returnButton;
	private static ListView<String> names = new ListView<String>();
    private static ListView<Integer> scores = new ListView<Integer>();
    private static ListView<Integer> rank = new ListView<Integer>();
	
	public HighScoreView() {
		
		rank.setItems(HighScoreController.placements);
		rank.setMouseTransparent(true);
        
		names.setItems(HighScoreController.names);
        names.setMouseTransparent(true);
        
        scores.setItems(HighScoreController.scores);
        scores.setMouseTransparent(true);
		

		Label viewLabel = new Label("Highscore");
		viewLabel.setId("label");
		viewLabel.setPadding(new Insets(0,0,100,0));
		returnButton = new Button("Return");

       

        HBox hsTable = new HBox();
        hsTable.getChildren().addAll(rank, names, scores);
        hsTable.setSpacing(5);
        hsTable.setAlignment(Pos.CENTER);
        hsTable.setPadding(new Insets(0, 10, 0, 10));

        
		VBox overlay = new VBox();
		overlay.getChildren().addAll(viewLabel, hsTable, returnButton);
		overlay.setAlignment(Pos.CENTER);
		overlay.setSpacing(30);
		overlay.setPadding(new Insets(100, 30, 100, 30));;
		overlay.setId("overlay");
		overlay.setMaxWidth(700);
		
		Pane pane = new Pane();
		pane.setId("pane");
		
		this.getChildren().addAll(pane, overlay);

		
		returnButton.setId("button");

		
	}

}
