package scenes.credits;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class CreditsView extends StackPane{
	
	Button returnButton;
	
	public CreditsView(){
		
		//Label: credits, programmer, names, music, borrowed, art, selfmade
		Label viewLabel = new Label("Credits");
		viewLabel.setId("label");
		viewLabel.setPadding(new Insets(100,0,150,0));
		Label programmer = new Label("Programmer:");
		programmer.setId("credits");
		Label names = new Label("Christian Kunkel, Mike Daudrich, Lara Reitz");
		names.setId("credits");
		Label music = new Label("Music:");
		music.setId("credits");
		Label borrowed = new Label ("borrowed");
		borrowed.setId("credits");
		Label art = new Label("Art:");
		art.setId("credits");
		Label selfmade = new Label("Christian Kunkel, Sandra Daudrich, Lara Reitz");
		selfmade.setId("credits");
		selfmade.setWrapText(true);
		
		//GridPane: Credits
		GridPane grid = new GridPane();
//		grid.setGridLinesVisible(true); //view gridlines
		grid.setPadding(new Insets(0,80,00,80));
		grid.setHgap(40);
		grid.setVgap(10);
		grid.setPrefHeight(1000);
		grid.setAlignment(Pos.CENTER);
		for (int i= 0; i < 3; i++) {
			RowConstraints row = new RowConstraints(20, 30, 300);
			grid.getRowConstraints().add(row);
		}
		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPercentWidth(25);
		ColumnConstraints col2 = new ColumnConstraints();
		col2.setPercentWidth(75);
		grid.getColumnConstraints().addAll(col1, col2);
		
		//adds labels to gridpane
		grid.add(programmer, 0, 0);
		grid.add(names, 1, 0);
		grid.add(music, 0, 1);
		grid.add(borrowed, 1, 1);
		grid.add(art, 0, 2);
		grid.add(selfmade, 1, 2, 1, 2);
		
		//Button: return to MM
		returnButton = new Button("Return");
		returnButton.setId("button");
		
		//VBox: returnButton
		VBox buttonPane = new VBox();
		buttonPane.getChildren().addAll(returnButton);
		buttonPane.setAlignment(Pos.CENTER);
		buttonPane.setSpacing(10);
		buttonPane.setPadding(new Insets(50,0,150,0));
		
		//Vbox: headline, gridpane(labels), button
		VBox overlay = new VBox();
		overlay.getChildren().addAll(viewLabel,grid,buttonPane);
		overlay.setAlignment(Pos.CENTER);
		overlay.setSpacing(30);
		overlay.spacingProperty().set(10);
		overlay.setId("overlay");
		overlay.setMaxWidth(700);
		
		//Pane for background
		Pane pane = new Pane();
		pane.setId("pane");
		
		//Stackpane with partly transperant overlay
		this.getChildren().addAll(pane, overlay);


	}

}
