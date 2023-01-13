package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import scenes.highScore.HighScoreController;
import scenes.mainMenu.MenuController;
import scenes.options.OptionsController;
import scenes.pause.PauseController;
import scenes.transitions.IncomingTransition;
import scenes.transitions.OutgoingTransition;
import scenes.transitions.ScreenTransition;
import scenes.SceneCollection;
import scenes.credits.CreditsController;
import scenes.game.GameController;
import scenes.gameOver1.GameOverController;

public class Main extends Application {
	private Scene scene;
	private MenuController controlMM;
	private OptionsController controlO;
	private HighScoreController controlHS;
	private GameController controlGS;
	private CreditsController controlC;
	private PauseController controlP;
	private GameOverController controlGO;
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void init(){
	}

	@Override
	public void start(Stage primaryStage){
		try {
			controlMM = new MenuController(this);
			controlHS = new HighScoreController(this);
			controlC = new CreditsController(this);
			controlGS = new GameController(this);
			controlO = new OptionsController(this);
			controlGO = new GameOverController(this);
			controlP = new PauseController(this);
			
			Pane root = controlMM.getView();
			root.setId("pane");
			
			scene = new Scene(root,1440,900);
			scene.getStylesheets().addAll(this.getClass().getResource("applications.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	@Override
	public void stop() {
	}
	
	
	public void switchView(SceneCollection viewName) {
		ScreenTransition screenTransition = null;
		
		Pane fromView = (Pane) scene.getRoot();
		Pane toView = null;
		
		switch(viewName) {
		case MAINMENU_SCENE:
			toView = controlMM.getView();
			screenTransition = new OutgoingTransition();
			break;
		case OPTIONS_SCENE:
			toView = controlO.getView();
			screenTransition = new IncomingTransition();
			break;
		case HIGHSCORE_SCENE:
			toView = controlHS.getView();
			screenTransition = new IncomingTransition();
			break;
		case GAME_SCENE:
			toView = controlGS.getView();
			screenTransition = new IncomingTransition();
			break; 
		case CREDITS_SCENE:
			toView = controlC.getView();
			screenTransition = new IncomingTransition();
			break;
		case PAUSE_SCENE:
			toView = controlP.getView();
			screenTransition = new IncomingTransition();
			break;
		case GAMEOVER_SCENE:
			toView = controlGO.getView();
			screenTransition = new IncomingTransition();
			break;
		} if (screenTransition != null)
			screenTransition.animate(scene, fromView, toView);
			toView.requestFocus();
			
	}
	

	
	
}
	
