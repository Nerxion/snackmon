package scenes.highScore;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import scenes.SceneCollection;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HighScoreController {

	static ObservableList<String> names = FXCollections.observableArrayList();
	static ObservableList<Integer> scores = FXCollections.observableArrayList();
	static ObservableList<Integer> placements = FXCollections.observableArrayList();

	private Button returnButton;
	private HighScoreView view;
	private Main application;
	public static int placement = 1;
	public static HashMap<String, Integer> hashmap;

	private static int score;
	private static String name;
	private static int listLength = 0;
	private static String IAmName;
	private static String IAmScore;
	private static ArrayList<String> UserName = new ArrayList<String>();
	private static ArrayList<Integer> UserScore = new ArrayList<Integer>();

	public HighScoreController(Main application) throws IOException {
		view = new HighScoreView();
		returnButton = view.returnButton;
		this.application = application;
		initialize();

		readScore();
		hashmap = new HashMap<String, Integer>();

		// Eingeben der Hashmap Daten
		int i = 0;
		int listPosition = 0;

		hashmap.put(name, score);
		while (i < listLength) {
			hashmap.put(UserName.get(listPosition), UserScore.get(listPosition));
			listPosition++;
			i++;
		}

		Map<String, Integer> hashmapOne = sortByValue(hashmap);

		// Sortierte Hashmap wir ausgegeben
		int j = 0;
		for (Map.Entry<String, Integer> output : hashmapOne.entrySet()) {
			if (j < 10) {
				names.add(output.getKey());
				scores.add(output.getValue());
				placements.add(placement);
				placement++;
				j++;
			}
		}

	}

	public static void createHS(int score, String name) throws IOException {
		names.clear();
		scores.clear();
		placements.clear();
		placement = 1;
		writeScore(name, score);
		readScore();

		// Eingeben der Hashmap Daten
		hashmap.put(name, score);

		Map<String, Integer> hashmapOne = sortByValue(hashmap);
		int y = 0;

		// Sortierte Hashmap wird ausgegeben
		for (Map.Entry<String, Integer> output : hashmapOne.entrySet()) {
			if (y < 10) {
//				System.out.println(output.getKey() + "- - - - - - Score: " + output.getValue());
				names.add(output.getKey());
				scores.add(output.getValue());
				placements.add(placement);
				placement++;
				y++;
			}
		}
	}

	// Funktion zum Sortieren der Hashmap nach Werten
	public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hashmap) {

		// Erstellt eine Liste aus Elementen der HashMap
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(hashmap.entrySet());

		// Sortiert die Liste
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> low, Map.Entry<String, Integer> high) {
				return (high.getValue()).compareTo(low.getValue());
			}
		});

		// Daten aus der sortierten Liste in die Hashmap einfügen
		HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> content : list) {
			temp.put(content.getKey(), content.getValue());
		}
		return temp;
	}

	// Schreibt neuen Name inklusive Score in das File
	public static void writeScore(String name, int score) throws IOException {
		FileWriter writerOutput = new FileWriter("HighScore.txt", true);
		BufferedWriter writer = new BufferedWriter(writerOutput);
		writer.write("\n" + name + ":" + score);
		writer.flush();
		writer.close();
	}

	// Liest File ein und spaltet Name von Score in seperate Array Lists
	public static void readScore() throws IOException {
		FileReader readerOutput = new FileReader("HighScore.txt");
		BufferedReader reader = new BufferedReader(readerOutput);
		String line = reader.readLine();
		while (line != null) {
			String[] splitLine = line.split(":");
			IAmName = splitLine[0];
			IAmScore = splitLine[1];
			UserName.add(IAmName);
			UserScore.add(Integer.parseInt(IAmScore));
			line = reader.readLine();
			listLength++;

		}
		reader.close();
	}

	private void initialize() {
		// column setup
		returnButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				application.switchView(SceneCollection.MAINMENU_SCENE);
			}
		});

	}

	public Pane getView() {
		return view;
	}

}
