package scenes.mainMenu;

import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import scenes.SceneCollection;

public class MenuController {
	
	private MainMenuView view;
	private Button playButton;
	private Button optionsButton;
	private Button highScoreButton;
	private Button exitButton;
	private Button creditsButton;

	
	private Main application;
	
	public MenuController(Main application) {
		view = new MainMenuView();
		//reference to MMButtons
		playButton = view.playButton;
		optionsButton = view.optionsButton;
		highScoreButton = view.highScoreButton;
		creditsButton = view.creditsButton;
		exitButton = view.exitButton;
		this.application = application;
		initialize();

	}

	//Button: play, options, highScore, credits, exit
	public void initialize() {
		
		playButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
		public void handle(ActionEvent e) {
			application.switchView(SceneCollection.GAME_SCENE);
			}
		});
		
		optionsButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e) {
				application.switchView(SceneCollection.OPTIONS_SCENE);
				}
			});
		
		highScoreButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e) {
				application.switchView(SceneCollection.HIGHSCORE_SCENE);
				}
			});
		
		creditsButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e) {
				application.switchView(SceneCollection.CREDITS_SCENE);
				}
			});
		
		exitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				application.stop();
				((Stage)(((Button)e.getSource()).getScene().getWindow())).close();
			}
		});
	}

	public Pane getView() {
		return view;
	}
	


}
