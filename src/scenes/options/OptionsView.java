package scenes.options;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class OptionsView extends StackPane{
	
	//Option buttons
	Button songSelectButton;
	Button mapSelectButton; 
	Button returnButton;
	Button playButton;
	Button stopButton;
	ChoiceBox<String> musicChoiceBox;
	public ChoiceBox<String> mapChoiceBox;
	
	public OptionsView(){
		//Label
		Label viewLabel = new Label("Options");
		viewLabel.setId("label");
		Label songLabel = new Label("Song");
		songLabel.setId("label1");
		Label mapLabel = new Label("Map");
		mapLabel.setId("label1");
		
		//Option-Button
		returnButton = new Button("Return");
		returnButton.setId("button");
		playButton = new Button();
		playButton.setId("playButton");
		stopButton = new Button();
		stopButton.setId("stopButton");
		
		//ChoiceBox - Music and Map
		musicChoiceBox = new ChoiceBox<>();
		mapChoiceBox = new ChoiceBox<>();
		musicChoiceBox.getItems().addAll("ANewBeginning", "Buddy", "Cute", "Dubstep", "Epic", "Happyrock");
		musicChoiceBox.setPrefWidth(150);
		mapChoiceBox.getItems().addAll("CrunchyCoral", "Atlantis", "Insectlair", "Grotto", "StarfishReef", "RockBottom");
		mapChoiceBox.setPrefWidth(150);
		mapChoiceBox.setValue("Atlantis");
		
		//Gridpane: header, songLabel, mapChoicebox, musicChoicebox, returnButton
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(50,50,50,50));
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPrefHeight(1000);
		grid.setAlignment(Pos.CENTER);
		//Rows
		for (int i= 0; i < 7; i++) {
			RowConstraints row = new RowConstraints(20, 40, 300);
			grid.getRowConstraints().add(row);
		}
		//Columns
		ColumnConstraints col1 = new ColumnConstraints(70, 100, 300);
		ColumnConstraints col2 = new ColumnConstraints(70, 180, 300);
		ColumnConstraints col3 = new ColumnConstraints(30);
		ColumnConstraints col4 = new ColumnConstraints(30);
		grid.getColumnConstraints().addAll(col1, col2, col3, col4);
//		grid.setGridLinesVisible(true);  //shows gridlines
		
		//options label
		GridPane.setHalignment(viewLabel, HPos.CENTER);
		grid.add(viewLabel, 0, 0, 4, 1);
		//music related
		grid.add(songLabel, 0, 3);
		grid.add(musicChoiceBox, 1, 3);
		grid.add(playButton, 2, 3);
		grid.add(stopButton, 3, 3);
		//map related
		grid.add(mapLabel, 0, 4);
		grid.add(mapChoiceBox, 1, 4);
		//return Button
		GridPane.setHalignment(returnButton, HPos.CENTER);
		grid.add(returnButton, 0, 7, 4, 1);
		
		
		//VBox: gridpane (transperant Overlay)
		VBox overlay = new VBox();
		overlay.getChildren().addAll(grid); //viewLabel, musicPane, returnButton
		overlay.setAlignment(Pos.CENTER);
		overlay.setSpacing(20);
		overlay.setId("overlay");
		overlay.setMaxWidth(700);

		//background pane
		Pane pane = new Pane();
		pane.setId("pane");

		//Stackpane; backgroundPane, gridpane(overlay)
		this.getChildren().addAll(pane, overlay);
		

	}

}
