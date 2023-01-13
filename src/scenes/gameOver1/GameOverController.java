package scenes.gameOver1;

import java.io.IOException;

import application.Main;
import business.MusicPlayer;
import javafx.animation.AnimationTimer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import scenes.SceneCollection;
import scenes.game.GameController;
import scenes.game.Labygen;
import scenes.highScore.HighScoreController;
import scenes.options.OptionsController;

public class GameOverController {

	private GameOverView view;
	private Button continueButton;
	private Labygen game;
	private TextField text;
	private static String data;
	private Label scoreLabel;
	private ChoiceBox<String> mapChoiceBox;
	AnimationTimer timer;
	private ObjectProperty<Boolean> gameOverBool = new SimpleObjectProperty<>();
	private boolean gameOverBool2 = false;
	private Main application;

	public GameOverController(Main application) {
		view = new GameOverView();
		continueButton = view.continueButton;
		game = GameController.viewLaby;
		text = view.nameField;
		scoreLabel = view.scoreLabel;
		mapChoiceBox = OptionsController.getMapChoiceBox();

		gameOverBool.setValue(false);
		timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				gameOverBool2 = Labygen.gameOverBool2;
				gameOverBool.setValue(gameOverBool2);
			}
		};
		timer.start();

		this.application = application;
		initialize();

	}

	public void initialize() {

		gameOverBool.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue) {
					scoreLabel.setText("Score  " + Labygen.getScore());
				}
			}
		});

		continueButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				data = text.getText();
				text.setText(data);
				try {
					HighScoreController.createHS(Labygen.getScore(), data);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				game.resetGame();
				game.createMap(MusicPlayer.changeMap(mapChoiceBox));
				application.switchView(SceneCollection.MAINMENU_SCENE);
			}

		});

	}

	public static String getData() {
		return data;
	}

	public Pane getView() {
		return view;
	}

}
