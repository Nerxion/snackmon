package business;

import java.io.File;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicPlayer {
	private Thread playThread;
	private File audioPath;
	private Media audioMedia;
	private static String mapPath;
	MediaPlayer audioPlayer;
	ChoiceBox<String> musicChoiceBox;
	static ChoiceBox<String> mapChoiceBox;
	String name;
	private Label playingStatus;

	public MusicPlayer() {

		playingStatus = new Label("No music at the moment");

	}

	public void playMusic(ChoiceBox<String> choiceBox, Button playButton, Button stopButton, Label playingStatus) {
		String test = choiceBox.getValue();
		System.out.println("Play Song: " + test);

		// Set up the audio
		File audioPath = new File("/resources/" + test + ".mp3");
		Media audioMedia = new Media(audioPath.toURI().toString());
		MediaPlayer audioPlayer = new MediaPlayer(audioMedia);

		String name = audioPath.getName().toString();

		// Button: play, stop
		playButton.setOnAction(e -> {
			audioPlayer.stop();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			audioPlayer.play();
			playingStatus.setText("Now replaying: " + name);
		});

		stopButton.setOnAction(e -> {
			audioPlayer.stop();
			playingStatus.setText("Audio stopped");
		});
	}

	// Gets value of choosen music from choicebox and plays it
	public void playMusic(ChoiceBox<String> choiceBox) {
		String test = choiceBox.getValue();
		audioPath = new File("src/resources/" + test + ".mp3");
		audioMedia = new Media(audioPath.toURI().toString());
		audioPlayer = new MediaPlayer(audioMedia);
		if (playThread != null && playThread.isAlive()) {
			playThread.interrupt();
		}

		playThread = new Thread() {
			public void run() {
				while (!isInterrupted()) {
					try {
						audioPlayer.play();
						playingStatus.setText("Now replaying: " + name);
						sleep(30 * 2000);
					} catch (InterruptedException e) {
						interrupt();
					}
				}
			}
		};
		playThread.setDaemon(true);
		playThread.start();
	}

	// Stops currently playing music
	public void stopMusic() {
		audioPlayer.stop();
		playingStatus.setText("Audio stopped");
	}

	// gets value of choosen map from choicebox, returns: path of map (.txt)
	public static String changeMap(ChoiceBox<String> mapChoiceBox) {
		String mapString = mapChoiceBox.getValue();
		mapPath = new String("src/resources/" + mapString + ".txt");
		return mapPath;
	}

}
