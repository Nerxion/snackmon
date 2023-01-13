package scenes.options;


import application.Main;
import javafx.scene.layout.Pane;
import scenes.SceneCollection;
import scenes.game.GameController;
import scenes.game.Labygen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import business.MusicPlayer;

public class OptionsController{
	
	private OptionsView view;
	
	private Button returnButton;
	private Button playButton;
	private Button stopButton;
	private Main application;
	private ChoiceBox<String> musicChoiceBox;
	private static ChoiceBox<String> mapChoiceBox;
	private MusicPlayer player;
	private Labygen laby;
	

	
	public OptionsController(Main application) {
		view = new OptionsView();
		laby = GameController.viewLaby;
		returnButton = view.returnButton;
		playButton = view.playButton;
		stopButton = view.stopButton;
		musicChoiceBox = view.musicChoiceBox;
		
		player = new MusicPlayer();
		mapChoiceBox = view.mapChoiceBox;
		this.application = application;
		
		initialize();
	}

	//Button: return, play, stop
	private void initialize() {
	

		returnButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				//necessary game reset to change map + switch to MM
				laby.resetGame();
				laby.createMap(MusicPlayer.changeMap(mapChoiceBox));
				application.switchView(SceneCollection.MAINMENU_SCENE);
				}
			});
	
		//plays music
		playButton.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e) {
				player.playMusic(musicChoiceBox);
			}
		});
  
		//stops music
		stopButton.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e) {
	        	player.stopMusic();
	        	
			}
		});

	}

	
	public Pane getView() {
		return view;
	}
	
	//Getter for changed Map
	public static ChoiceBox<String> getMapChoiceBox() {
		return mapChoiceBox;
	}
	



}
