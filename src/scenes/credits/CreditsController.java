package scenes.credits;

import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import scenes.SceneCollection;

public class CreditsController {

	private CreditsView view;
	private Button returnButton;
	private Main application;

	public CreditsController(Main application) {
		view = new CreditsView();
		returnButton = view.returnButton;
		this.application = application;
		initialize();
	}

	private void initialize() {

		// Button: return to MM
		returnButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				application.switchView(SceneCollection.MAINMENU_SCENE);
			}
		});
	}

	public Pane getView() {
		return view;
	}

}
