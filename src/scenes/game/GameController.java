package scenes.game;


import application.Main;
import javafx.animation.AnimationTimer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import scenes.SceneCollection;

public class GameController {
	
	private Main application;
	public static Labygen viewLaby;
	private String Atlantis;
	AnimationTimer timer;
	private ObjectProperty<Boolean> gameOverBool = new SimpleObjectProperty<>();
	private boolean gameOverBool2 = false;
	
	public GameController(Main application) {
	    Atlantis = "src/resources/Atlantis.txt";
	    viewLaby = new Labygen(Atlantis);
		this.application = application;
		
		//GameOver-Timer
		gameOverBool.setValue(false);
		timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
            	gameOverBool2 = Labygen.gameOverBool2;
        		gameOverBool.setValue(gameOverBool2);
            }
        };
        timer.start();
        
		initialize();
	}
	

	private void initialize() {


        viewLaby.onKeyPressedProperty().set(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
            	if (event.getCode().equals(KeyCode.W)) {
            		viewLaby.setPlayerImagewp();
                }
                if (event.getCode().equals(KeyCode.S)) {
                    viewLaby.setPlayerImagesp();
                }
                if (event.getCode().equals(KeyCode.A)) {
                    viewLaby.setPlayerImageap();
                }
                if (event.getCode().equals(KeyCode.D)) {
                    viewLaby.setPlayerImagedp();
                }
                if(event.getCode().equals(KeyCode.ESCAPE)) {
                	viewLaby.freeze();
                	application.switchView(SceneCollection.PAUSE_SCENE);
                }
            }
            
        });
        
        gameOverBool.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                	application.switchView(SceneCollection.GAMEOVER_SCENE);
                }
            }
        });
        
        
        
        

        
        
		
	}
	
	public void gameOver() {
		application.switchView(SceneCollection.GAMEOVER_SCENE);
	}

	public Pane getView() {
		return viewLaby;
	}
	
	


}
