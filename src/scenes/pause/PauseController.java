package scenes.pause;

import application.Main;
import business.MusicPlayer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import scenes.SceneCollection;
import scenes.game.GameController;
import scenes.game.Labygen;
import scenes.options.OptionsController;

public class PauseController {
	
	private PauseView view;
	private Button returnButton;
	private Button optionsButton;
	private Button returnMMButton;
	private Labygen game;
	private ChoiceBox<String> mapChoiceBox;
	private Main application;
	
	
	
	public PauseController(Main application) {
		view = new PauseView();
		game = GameController.viewLaby;
		returnButton = view.continueButton;
		optionsButton = view.optionsButton;
		returnMMButton = view.returnMMButton;
		mapChoiceBox = OptionsController.getMapChoiceBox();
		
		this.application = application;
		initialize();

	}

	//Button; returnGame, options, returnMainMenu
	public void initialize() {
		
		//return to GS and unfreeze Game
		returnButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
		public void handle(ActionEvent e) {
			game.melt();
			application.switchView(SceneCollection.GAME_SCENE);
			}
		});
		
		optionsButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e) {
				application.switchView(SceneCollection.OPTIONS_SCENE);
				}
			});
		
		//resets game on return to MainMenu, sets map to last chosen map from choicebox/ default map
		returnMMButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				game.resetGame();
				game.createMap(MusicPlayer.changeMap(mapChoiceBox));
				application.switchView(SceneCollection.MAINMENU_SCENE);
				}
			});
	}

	public Pane getView() {
		return view;
	}
	


}
