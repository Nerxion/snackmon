package scenes.game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.animation.AnimationTimer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Labygen extends StackPane{
	// Bewegungsgeschwindigkeit Spieler + Gegner (E = Enemy)
	private int step = 2, tempstep = step;
	private int stepE = 1, wstempstepE1 = step, adtempstepE1 = step, wstempstepE2 = step, adtempstepE2 = step, wstempstepE3 = step, adtempstepE3 = step;
	
	// Sonstiges wichtiges Zeug
	private Scanner sc;
	private int zeilen = 19, spalten = 29;
	private int size = 40;
	private Pane mapPain;
	
	// Dateipfade
    private String playerImageSrcW = "file:src/resources/otter_model2_small_w.png";
    private String playerImageSrcA = "file:src/resources/otter_model2_small_a.png";
    private String playerImageSrcS = "file:src/resources/otter_model2_small_s.png";
    private String playerImageSrcD = "file:src/resources/otter_model2_small_d.png";
    private String enemy1Src = "file:src/resources/shark.png";
    private String labyWallSrc = "file:src/resources/algen_thick.png";
    private String sSnackImageSrc = "file:src/resources/smalClam.png";
    private String bSnackImageSrc = "file:src/resources/seeStar.png";
    private String snackImageESrc = "file:src/resources/snack_model_empty.png";
    private Image labyWallImage;
    private ImagePattern labyWallImagep;
    private Image sSnackImage, snackImageE, bSnackImage;
    private Image playerImageW, playerImageA, playerImageS, playerImageD, enemy1Image;
    private ImagePattern playerImageWp, playerImageAp, playerImageSp, playerImageDp, sSnackImagep, snackImageEp, bSnackImagep, enemy1Imagep;
	private Image heartImage = new Image("/resources/pixel-heart-2779422_640.png");
	
    // Alles fuer den Spieler
    private boolean wPress, sPress, dPress, aPress;
    private Rectangle player, playerHb, playerHbHalf;
	private int[] xyPlayer, xyPlayerHb, xyPlayerHbHalf;
	private int leben = 3;
	private static boolean gameOver = false;
	
	// Alles fuer die Gegner
	private Rectangle enemy1, enemy2, enemy3;
	private int[] xy1, xy2, xy3;
	private int rand1, rand2, rand3, randWsAd1, randWsAd2, randWsAd3;
	private boolean first1, first2, first3;
	
	// Arrays (Waende, Snacks)
	private int[][] fileArray;
	private Boolean[] sSnacksBool, bSnacksBool;
	private Rectangle[] wallsFile;
	private Rectangle[] sSnacksFile, bSnacksFile;
	
	// Score, Zaehler und sonstiges
	private int currSScore = 0, currBScore = 0, currtempMaxScore, currMaxScore;
	private static int endScore = 0;
	private int counter = 0, counterof1 = 0, counterof0 = 0, counterof2 = 0, counterofSSnacks = 0, counterofBSnacks = 0, counterofSSnacksMax = 0, counterofBSnacksMax = 0, counterAllSnacks = 0;
	private int xwertfilewalls = 0, ywertfilewalls = 0;
	private int bSnackScoreAmplifier = 5;
	private int viewScore;
	private Label scoreLabel;
	
	// Herzchen
	private Rectangle[] heart;
	
	// Timer
	AnimationTimer timer;
	Timer timerE;
	TimerTask timerTask;
	
	// GameOver
	public static ObjectProperty<Boolean> gameOverBool = new SimpleObjectProperty<>();
	public static boolean gameOverBool2 = false;
	
	// Constructor - alles wird erstellt etc
    public Labygen(String labySrc) {
    	rand1 = 4; randWsAd1 = 1; rand2 = 1; randWsAd2 = 2; rand3 = 2; randWsAd3 = 1;
    	first1 = true; first2 = true; first2 = true;
    	
    	// Text-Datei in zweidimensionales Array speichern
    	fileArray = new int[zeilen][spalten];
		
		// Images und Imagepattern erstellen
		sSnackImage = new Image(sSnackImageSrc);
		sSnackImagep = new ImagePattern(sSnackImage);
		bSnackImage = new Image(bSnackImageSrc);
		bSnackImagep = new ImagePattern(bSnackImage);
		snackImageE = new Image(snackImageESrc);
		snackImageEp = new ImagePattern(snackImageE);
		labyWallImage = new Image(labyWallSrc);
		labyWallImagep = new ImagePattern(labyWallImage);
		enemy1Image = new Image(enemy1Src);
        enemy1Imagep = new ImagePattern(enemy1Image);
		playerImageW = new Image(playerImageSrcW);
        playerImageA = new Image(playerImageSrcA);
        playerImageS = new Image(playerImageSrcS);
        playerImageD = new Image(playerImageSrcD);
        playerImageWp = new ImagePattern(playerImageW);
        playerImageAp = new ImagePattern(playerImageA);
        playerImageSp = new ImagePattern(playerImageS);
        playerImageDp = new ImagePattern(playerImageD);
        
        // Spieler erstellen
		player = new Rectangle(0, 0, size, size);
		xyPlayer = new int[2];
		
		// Hitboxen des Spielers erstellen
		playerHb = new Rectangle(0, 0, size-2, size-2);
        xyPlayerHb = new int[2];
		playerHbHalf = new Rectangle(0, 0, size/4, size/4);
		xyPlayerHbHalf = new int[2];
		
		setPlayer(3);
		
		// Gegner erstellen
		enemy1 = new Rectangle(0, 0, size-2, size-2);
		enemy2 = new Rectangle(0, 0, size-2, size-2);
		enemy3 = new Rectangle(0, 0, size-2, size-2);
		xy1 = new int[2];
		xy2 = new int[2];
		xy3 = new int[2];
		
		// Herzen erstellen
		heart = new Rectangle[3];
		for(int i = 0; i < 3; i++) {
			heart[i] = new Rectangle();
			heart[i].setId("heart");
			heart[i].setHeight(BASELINE_OFFSET_SAME_AS_HEIGHT);
	        heart[i].setWidth(50);
	        heart[i].setHeight(50);
	        heart[i].setFill(new ImagePattern(heartImage));
	        
		}
		
        mapPain = new Pane();
        createMap(labySrc);
        
        mapPain.setId("mapBG");
        mapPain.setMaxSize(spalten*size, zeilen*size);
        
		Pane pane = new Pane();
		pane.setId("gamePane");
		
		scoreLabel = new Label("Score" + viewScore);
		scoreLabel.setAlignment(Pos.CENTER_RIGHT);
		scoreLabel.setId("label1");
		
		HBox heartPane = new HBox();
		heartPane.getChildren().addAll(heart[0], heart[1], heart[2]);
		heartPane.setPadding(new Insets(10,0,0,20));
		heartPane.setAlignment(Pos.TOP_LEFT);
		
		HBox scorePane = new HBox();
		scorePane.getChildren().addAll(scoreLabel);
		scorePane.setPadding(new Insets(10,40,0,0));
		scorePane.setAlignment(Pos.TOP_RIGHT);
		
		this.getChildren().addAll(heartPane, scorePane, mapPain);
		this.setStyle("-fx-background-color:rgb(0,0,0);");
    }

	// Hilfsmethoden für die Bewegungsaktionen
	public void setPlayerImagewp() {
        wPress = true;
        sPress = false;
    	player.setFill(playerImageWp);
    }
    public void setPlayerImagesp() {
        sPress = true;
        wPress = false;
    	player.setFill(playerImageSp);
    }
    public void setPlayerImageap() {
        aPress = true;
        dPress = false;
    	player.setFill(playerImageAp);
    }
    public void setPlayerImagedp() {
        dPress = true;
        aPress = false;
    	player.setFill(playerImageDp);
    }
    
    // Mitgegebene Textdatei einlesen und in Array speichern
    public void scanFile(String labySrc) {
    	try {
			sc = new Scanner(new BufferedReader(new FileReader(labySrc)));
	        while(sc.hasNextLine()) {
	           for (int i = 0; i < fileArray.length; i++) {
	              String[] line = sc.nextLine().trim().split(" ");
	              for (int j = 0; j < line.length; j++) {
	                 fileArray[i][j] = Integer.parseInt(line[j]);
	              }
	           }
	        }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
    
    // Menge der Zahlen zaehlen, um danach die Rectangle-Array mit der Anzahl zu erstellen
    public void mengenzaehlen(int[][] fileArray) {
		for (int zeile = 0; zeile < fileArray.length; zeile++) {
            for (int spalte = 0; spalte < fileArray[0].length; spalte++) {
               if (fileArray[zeile][spalte] == 1) counterof1++;
               else if (fileArray[zeile][spalte] == 0) counterof0++;
               else if (fileArray[zeile][spalte] == 2) counterof2++;
            }
        }
    }
    
    // Aus dem zweidimensionalen Array Labyrinth erstellen
    public void makeMazeFile(Rectangle[] rectArray, ImagePattern currImage, int currNumber) {
    	// Jedes Zeichen im zweidimensionalen Array durchlaufen
    	for (int zeile = 0; zeile < fileArray.length; zeile++) {
            for (int spalte = 0; spalte < fileArray[0].length; spalte++) {
            	if (fileArray[zeile][spalte] == currNumber) {
            		if (zeile == 0 && spalte == 0) fillMazespot(rectArray, currImage);
            		else if (zeile >= 0 && spalte > 0) {
            			xwertfilewalls += size;
            		   	fillMazespot(rectArray, currImage);
            		}
            		else if (zeile > 0 && spalte == 0) {
            			xwertfilewalls = 0;
            			ywertfilewalls += size;
            			fillMazespot(rectArray, currImage);
            		}
            	}
            	else {
            		if (zeile >= 0 && spalte > 0) xwertfilewalls += size;
            		else if (zeile > 0 && spalte == 0) {
            			xwertfilewalls = 0;
            			ywertfilewalls += size;
            		}
            	}
            }
        }
    	counter = 0;
		xwertfilewalls = 0;
		ywertfilewalls = 0;
    }
    
    // Fuer jeden Inhalt ein Rectangle erstellen und Bild, Position und Groesse geben
    public  void fillMazespot(Rectangle[] rectArray, ImagePattern currImage) {
    	rectArray[counter] = new Rectangle();
    	rectArray[counter].setFill(currImage);
    	rectArray[counter].setX(xwertfilewalls);
    	rectArray[counter].setY(ywertfilewalls);
    	rectArray[counter].setWidth(size);
    	rectArray[counter].setHeight(size);
    	counter++;
    }
    
    // Spieler Position ermitteln
    public void setPlayer(int currNumber) {
    	for (int zeile = 0; zeile < fileArray.length; zeile++) {
            for (int spalte = 0; spalte < fileArray[0].length; spalte++) {
            	if (fileArray[zeile][spalte] == currNumber) {
            		if (zeile == 0 && spalte == 0) givePlayerPosition();
            		else if (zeile >= 0 && spalte > 0) {
            			xwertfilewalls += size;
            			givePlayerPosition();
            		}
            		else if (zeile > 0 && spalte == 0) {
            			xwertfilewalls = 0;
            			ywertfilewalls += size;
            			givePlayerPosition();
            		}
            	}
            	else {
            		if (zeile >= 0 && spalte > 0) xwertfilewalls += size;
            		else if (zeile > 0 && spalte == 0) {
            			xwertfilewalls = 0;
            			ywertfilewalls += size;
            		}
            	}
            }
        }
    	counter = 0;
		xwertfilewalls = 0;
		ywertfilewalls = 0;
    }
    
    // Spieler und Hitboxen Position geben/abspeichern
    public void givePlayerPosition() {
    	int playerHbHalfX = xwertfilewalls+((size-(size/4))/2);
    	int playerHbHalfY = ywertfilewalls+((size-(size/4))/2);
    	player.setFill(playerImageDp);
    	player.setTranslateX(xwertfilewalls);
    	player.setTranslateY(ywertfilewalls);
        playerHb.setFill(Color.TRANSPARENT);
		playerHb.setTranslateX(xwertfilewalls+1);
        playerHb.setTranslateY(ywertfilewalls+1);
        playerHbHalf.setFill(Color.TRANSPARENT);
        playerHbHalf.setTranslateX(playerHbHalfX);
        playerHbHalf.setTranslateY(playerHbHalfY);
    	xyPlayer[0] = xwertfilewalls;
    	xyPlayer[1] = ywertfilewalls;
    	xyPlayerHb[0] = xwertfilewalls+1;
    	xyPlayerHb[1] = ywertfilewalls+1;
    	xyPlayerHbHalf[0] = playerHbHalfX;
    	xyPlayerHbHalf[1] = playerHbHalfY;
    }
    
    // Gegner Position ermitteln
    public int[] setEnemy(Rectangle enemy, ImagePattern currImage, int currNumber, int[] xy) {
    	// Jedes Zeichen im zweidimensionalen Array durchlaufen
    	for (int zeile = 0; zeile < fileArray.length; zeile++) {
            for (int spalte = 0; spalte < fileArray[0].length; spalte++) {
            	if (fileArray[zeile][spalte] == currNumber) {
            		if (zeile == 0 && spalte == 0) xy = giveEnemyPosition(enemy, currImage, xy);
            		else if (zeile >= 0 && spalte > 0) {
            			xwertfilewalls += size;
            			xy = giveEnemyPosition(enemy, currImage, xy);
            		}
            		else if (zeile > 0 && spalte == 0) {
            			xwertfilewalls = 0;
            			ywertfilewalls += size;
            			xy = giveEnemyPosition(enemy, currImage, xy);
            		}
            	}
            	else {
            		if (zeile >= 0 && spalte > 0) xwertfilewalls += size;
            		else if (zeile > 0 && spalte == 0) {
            			xwertfilewalls = 0;
            			ywertfilewalls += size;
            		}
            	}
            }
        }
    	counter = 0;
		xwertfilewalls = 0;
		ywertfilewalls = 0;
		return xy;
    }
    
    // Gegner Position geben/abspeichern
    public  int[] giveEnemyPosition(Rectangle enemy, ImagePattern currImage, int[] xy) {
    	enemy.setFill(currImage);
    	enemy.setTranslateX(xwertfilewalls+1);
    	enemy.setTranslateY(ywertfilewalls+1);
    	xy[0] = xwertfilewalls+1;
    	xy[1] = ywertfilewalls+1;
    	return xy;
    }
    
    // Spieler bewegen, abhaengig davon, welche Taste gedrueckt wird; jeweils bevor irgendwas gemacht wird nach Kollision pruefen
    public void movePlayer() {
    	if (wPress || sPress) {
    		// Bewegung negieren, damit es die richtige Richtung kontrolliert
    		if (wPress) tempstep = -step;
    		// Collision mit Waenden und Gegnern ueberpruefen, ggf Bewegung auf 0 setzen
    		wscollision();
    		enemyCollision(enemy1, xy1);
    		enemyCollision(enemy2, xy2);
    		enemyCollision(enemy3, xy3);
    		// Spieler in die Richtung bewegen und die Position der Hitbox aktualisieren
        	player.setTranslateY(player.getTranslateY()+tempstep);
            playerHb.setTranslateY(player.getTranslateY()+1);
            playerHbHalf.setTranslateY(player.getTranslateY()+((size-size/4)/2));
            snackCollision(sSnacksFile, sSnacksBool);
            snackCollision(bSnacksFile, bSnacksBool);
            // Bewegung wieder auf normales Step stellen, falls Wand war
            tempstep = step;
        }
        if (aPress || dPress) {
        	if (aPress) tempstep = -step;
        	adcollision();
        	enemyCollision(enemy1, xy1);
        	enemyCollision(enemy2, xy2);
        	enemyCollision(enemy3, xy3);
        	player.setTranslateX(player.getTranslateX()+tempstep);
            playerHb.setTranslateX(player.getTranslateX()+1);
            playerHbHalf.setTranslateX(player.getTranslateX()+((size-size/4)/2));
            snackCollision(sSnacksFile, sSnacksBool);
            snackCollision(bSnacksFile, bSnacksBool);
            tempstep = step;
        }
    }
    
    // Spieler - Vertikale Collision mit den Wänden checken
    public void wscollision() {
    	// Hitbox in Bewegungsrichtung um Step (Bewegung) bewegen
        playerHb.setTranslateY((playerHb.getTranslateY())+((tempstep)));
		for(Rectangle rect: wallsFile) {
			// Kontrollieren, ob HitBox mit Rect der Waende kollidiert (Rect-Array durchlaufen, also fuer jedes Rect pruefen ^)
			if (playerHb.getBoundsInParent().intersects(rect.getBoundsInParent())) {
				// Wenn ja, dann HitBox wieder auf normal setzen
    			playerHb.setTranslateY(playerHb.getTranslateY()-((tempstep)));
    			// Hitbox solange weiter bewegen, bis mit Rect kollidiert, danach wieder um Step (Bewegung) zuruecksetzen, damit nicht mehr kollidiert
    			while(!playerHb.getBoundsInParent().intersects(rect.getBoundsInParent())) playerHb.setTranslateY(playerHb.getTranslateY()+((tempstep)));
    			playerHb.setTranslateY(playerHb.getTranslateY()-((tempstep)));
    			// Bewegung auf 0 setzen, damit man Spieler nicht in die Wand bewegen kann und Spieler zur Hitbox bewegen, damit komplett an der Wand
    			tempstep = 0;
    			player.setTranslateY(playerHb.getTranslateY()-1);
    		}
		}
		// Hitbox wieder (zurueck) auf Position des Spielers setzen, falls nicht kollidiert
		playerHb.setTranslateY(player.getTranslateY());
    }
    
    // Spieler - Horizontale Collision mit den Wänden checken
    public void adcollision() {
        playerHb.setTranslateX((playerHb.getTranslateX())+((tempstep)));
		for(Rectangle rect: wallsFile) {
			if (playerHb.getBoundsInParent().intersects(rect.getBoundsInParent())) {
    			playerHb.setTranslateX(playerHb.getTranslateX()-((tempstep)));
    			while(!playerHb.getBoundsInParent().intersects(rect.getBoundsInParent())) playerHb.setTranslateX(playerHb.getTranslateX()+((tempstep)));
    			playerHb.setTranslateX(playerHb.getTranslateX()-((tempstep)));
    			tempstep = 0;
    			player.setTranslateX(playerHb.getTranslateX()-1);
    		}
		}
		playerHb.setTranslateX(player.getTranslateX());
    }
    
    // Gegner 1 bewegen, mit zwei randomized Zahlen für die Bewegungsrichtungen
    public void moveEnemy1() {
    	// Startbewegungsrichtungen nur zu Beginn festlegen
    	if (first1) {
    		rand1 = 4;
        	randWsAd1 = 1;
        	first1 = false;
    	}
    	// Abhaengig von Zufallszahl von 1 bis 4 Bewegungsrichtung festlegen
    	if (rand1 == 1) {
    		wstempstepE1 = -stepE;
    		// Zweite Bewegungsrichtung abhaengig von Zufallszahl von 1 oder 2
        	if (randWsAd1 == 1) adtempstepE1 = -stepE;
        	wscollisionEnemy1();
        	adcollisionEnemy1();
        	enemy1.setTranslateY(enemy1.getTranslateY()+wstempstepE1);
        	enemy1.setTranslateX(enemy1.getTranslateX()+adtempstepE1);
        	if (wstempstepE1 == 0) rand1 = (int)(Math.random() * 4 + 1);
    	}
    	if (rand1 == 2) {
    		adtempstepE1 = -stepE;
    		if (randWsAd1 == 1) wstempstepE1 = -stepE;
    		wscollisionEnemy1();
        	adcollisionEnemy1();
        	enemy1.setTranslateY(enemy1.getTranslateY()+wstempstepE1);
        	enemy1.setTranslateX(enemy1.getTranslateX()+adtempstepE1);
        	if (adtempstepE1 == 0) rand1 = (int)(Math.random() * 4 + 1);
    	}
    	if (rand1 == 3) {
    		if (randWsAd1 == 1) adtempstepE1 = -stepE;
    		wscollisionEnemy1();
        	adcollisionEnemy1();
        	enemy1.setTranslateY(enemy1.getTranslateY()+wstempstepE1);
        	enemy1.setTranslateX(enemy1.getTranslateX()+adtempstepE1);
        	if (wstempstepE1 == 0) rand1 = (int)(Math.random() * 4 + 1);
    	}
    	if (rand1 == 4) {
    		if (randWsAd1 == 1) wstempstepE1 = -stepE;
    		wscollisionEnemy1();
        	adcollisionEnemy1();
        	enemy1.setTranslateY(enemy1.getTranslateY()+wstempstepE1);
        	enemy1.setTranslateX(enemy1.getTranslateX()+adtempstepE1);
        	if (adtempstepE1 == 0)rand1 = (int)(Math.random() * 4 + 1);
    	}
    	wstempstepE1 = stepE;
    	adtempstepE1 = stepE;
    }
    
    // Gegner 2 bewegen, mit zwei randomized Zahlen für die Bewegungsrichtungen
    public void moveEnemy2() {
    	if (first2) {
    		rand2 = 4;
        	randWsAd2 = 1;
        	first2 = false;
    	}
    	if (rand2 == 1) {
    		wstempstepE2 = -stepE;
        	if (randWsAd2 == 1) adtempstepE2 = -stepE;
        	wscollisionEnemy2();
        	adcollisionEnemy2();
        	enemy2.setTranslateY(enemy2.getTranslateY()+wstempstepE2);
        	enemy2.setTranslateX(enemy2.getTranslateX()+adtempstepE2);
        	if (wstempstepE2 == 0) rand2 = (int)(Math.random() * 4 + 1);
    	}
    	if (rand2 == 2) {
    		adtempstepE2 = -stepE;
    		if (randWsAd2 == 1) wstempstepE2 = -stepE;
    		wscollisionEnemy2();
        	adcollisionEnemy2();
        	enemy2.setTranslateY(enemy2.getTranslateY()+wstempstepE2);
        	enemy2.setTranslateX(enemy2.getTranslateX()+adtempstepE2);
        	if (adtempstepE2 == 0) rand2 = (int)(Math.random() * 4 + 1);
    	}
    	if (rand2 == 3) {
    		if (randWsAd2 == 1) adtempstepE2 = -stepE;
    		wscollisionEnemy2();
        	adcollisionEnemy2();
        	enemy2.setTranslateY(enemy2.getTranslateY()+wstempstepE2);
        	enemy2.setTranslateX(enemy2.getTranslateX()+adtempstepE2);
        	if (wstempstepE2 == 0) rand2 = (int)(Math.random() * 4 + 1);
    	}
    	if (rand2 == 4) {
    		if (randWsAd2 == 1) wstempstepE2 = -stepE;
    		wscollisionEnemy2();
        	adcollisionEnemy2();
        	enemy2.setTranslateY(enemy2.getTranslateY()+wstempstepE2);
        	enemy2.setTranslateX(enemy2.getTranslateX()+adtempstepE2);
        	if (adtempstepE2 == 0) rand2 = (int)(Math.random() * 4 + 1);
    	}
    	wstempstepE2 = stepE;
    	adtempstepE2 = stepE;
    }
    
    // Gegner 3 bewegen, mit zwei randomized Zahlen für die Bewegungsrichtungen
    public void moveEnemy3() {
    	if (first3) {
    		rand3 = 2;
        	randWsAd3 = 1;
        	first3 = false;
    	}
    	if (rand3 == 1) {
    		wstempstepE3 = -stepE;
        	if (randWsAd3 == 1) adtempstepE3 = -stepE;
        	wscollisionEnemy3();
        	adcollisionEnemy3();
        	enemy3.setTranslateY(enemy3.getTranslateY()+wstempstepE3);
        	enemy3.setTranslateX(enemy3.getTranslateX()+adtempstepE3);
        	if (wstempstepE3 == 0) rand3 = (int)(Math.random() * 4 + 1);
    	}
    	if (rand3 == 2) {
    		adtempstepE3 = -stepE;
    		if (randWsAd3 == 1) wstempstepE3 = -stepE;
    		wscollisionEnemy3();
        	adcollisionEnemy3();
        	enemy3.setTranslateY(enemy3.getTranslateY()+wstempstepE3);
        	enemy3.setTranslateX(enemy3.getTranslateX()+adtempstepE3);
        	if (adtempstepE3 == 0) rand3 = (int)(Math.random() * 4 + 1);
    	}
    	if (rand3 == 3) {
    		if (randWsAd3 == 1) adtempstepE3 = -stepE;
    		wscollisionEnemy3();
        	adcollisionEnemy3();
        	enemy3.setTranslateY(enemy3.getTranslateY()+wstempstepE3);
        	enemy3.setTranslateX(enemy3.getTranslateX()+adtempstepE3);
        	if (wstempstepE3 == 0) rand3 = (int)(Math.random() * 4 + 1);
    	}
    	if (rand3 == 4) {
    		if (randWsAd3 == 1) wstempstepE3 = -stepE;
    		wscollisionEnemy3();
        	adcollisionEnemy3();
        	enemy3.setTranslateY(enemy3.getTranslateY()+wstempstepE3);
        	enemy3.setTranslateX(enemy3.getTranslateX()+adtempstepE3);
        	if (adtempstepE3 == 0) rand3 = (int)(Math.random() * 4 + 1);
    	}
    	wstempstepE3 = stepE;
    	adtempstepE3 = stepE;
    }
    
    // Gegner 1 - Vertikale Collision mit den Wänden checken
    public void wscollisionEnemy1() {
        enemy1.setTranslateY((enemy1.getTranslateY())+((wstempstepE1)));
		for(Rectangle rect: wallsFile) {
			if (enemy1.getBoundsInParent().intersects(rect.getBoundsInParent())) {
				enemy1.setTranslateY(enemy1.getTranslateY()-((wstempstepE1)));
    			wstempstepE1 = 0;
    		}
		}
    }
    
    // Gegner 1 - Horizontale Collision mit den Wänden checken
    public void adcollisionEnemy1() {
    	enemy1.setTranslateX((enemy1.getTranslateX())+((adtempstepE1)));
		for(Rectangle rect: wallsFile) {
			if (enemy1.getBoundsInParent().intersects(rect.getBoundsInParent())) {
				enemy1.setTranslateX(enemy1.getTranslateX()-((adtempstepE1)));
    			adtempstepE1 = 0;
    		}
		}
    }
    
    // Gegner 2 - Vertikale Collision mit den Wänden checken
    public void wscollisionEnemy2() {
        enemy2.setTranslateY((enemy2.getTranslateY())+((wstempstepE2)));
		for(Rectangle rect: wallsFile) {
			if (enemy2.getBoundsInParent().intersects(rect.getBoundsInParent())) {
				enemy2.setTranslateY(enemy2.getTranslateY()-((wstempstepE2)));
    			wstempstepE2 = 0;
    		}
		}
    }
    
    //Gegner 2 - Horizontale Collision mit den Wänden checken
    public void adcollisionEnemy2() {
    	enemy2.setTranslateX((enemy2.getTranslateX())+((adtempstepE2)));
		for(Rectangle rect: wallsFile) {
			if (enemy2.getBoundsInParent().intersects(rect.getBoundsInParent())) {
				enemy2.setTranslateX(enemy2.getTranslateX()-((adtempstepE2)));
    			adtempstepE2 = 0;
    		}
		}
    }
    
    // Gegner 3 - Vertikale Collision mit den Wänden checken
    public void wscollisionEnemy3() {
        enemy3.setTranslateY((enemy3.getTranslateY())+((wstempstepE3)));
		for(Rectangle rect: wallsFile) {
			if (enemy3.getBoundsInParent().intersects(rect.getBoundsInParent())) {
				enemy3.setTranslateY(enemy3.getTranslateY()-((wstempstepE3)));
    			wstempstepE3 = 0;
    		}
		}
    }
    
    // Gegner 3 - Horizontale Collision mit den Wänden checken
    public void adcollisionEnemy3() {
    	enemy3.setTranslateX((enemy3.getTranslateX())+((adtempstepE3)));
		for(Rectangle rect: wallsFile) {
			if (enemy3.getBoundsInParent().intersects(rect.getBoundsInParent())) {
				enemy3.setTranslateX(enemy3.getTranslateX()-((adtempstepE3)));
    			adtempstepE3 = 0;
    		}
		}
    }
    
    // Spieler Collision mit Snacks checken
    public void snackCollision(Rectangle[] snacksFile, Boolean[] snacksBool) {
    	for (int i = 0; i < snacksFile.length; i++) {
    		if (playerHbHalf.getBoundsInParent().intersects(snacksFile[i].getBoundsInParent())) {
    			snacksFile[i].setFill(snackImageEp);
    			snacksBool[i] = false;
			}
        }
    }
    
    // Spieler Collision mit Gegnern checken
    public boolean enemyCollision(Rectangle enemy, int[] xy) {
    	if (playerHb.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
    		if (leben > 1) {
    			resetPositions();
	            leben--;
	            if(leben == 2) heart[2].setFill(snackImageEp);
	            else if(leben == 1) heart[1].setFill(snackImageEp);
	            else if(leben == 0) heart[0].setFill(snackImageEp);
	            tempstep = 0;
    		}
    		else {
    			tempstep = 0;
    			try {
					gameover();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    		return true;
		}
    	return false;
    }
    
    // Nach Spielende: Finalen Score festlegen und Spieler bewegungsunfähig machen
    public void gameover() throws IOException{
    	gameOverBool.setValue(true);
    	gameOverBool2 = true;
    	step = 0;
    	endScore = currMaxScore;
    	gameOver = true;
    }
    
    // Spieler und Gegner bewegungsunfähig machen
    public void freeze() {
    	step = 0;
    	stepE = 0;
    }
    
    // Spieler und Gegner wieder Moeglichkeit zum Bewegen geben
    public void melt() {
    	step = 2;
    	stepE = 1;
    }
    
    // Berechnung, wie viele Snacks noch da sind und Weiterleitung zum Snacks zuruecksetzen, falls alle Snacks weg
    public void snacksLeft() {
    	counterofSSnacks = 0;
    	counterofBSnacks = 0;
    	currSScore = calcSnackScore(sSnacksBool, counterofSSnacks, counterofSSnacksMax, currSScore);
    	currBScore = calcSnackScore(bSnacksBool, counterofBSnacks, counterofBSnacksMax, currBScore);
    	currBScore *= bSnackScoreAmplifier;
    	currtempMaxScore = currSScore + currBScore;
    	if (currtempMaxScore > currMaxScore) currMaxScore = currtempMaxScore;
    	if (currMaxScore == (counterAllSnacks+1)*(counterof0 + (counterof2*bSnackScoreAmplifier))) {
    		counterAllSnacks++;
    		resetSnacks();
    	}
    	scoreLabel.setText("Score  " + currtempMaxScore);
    }
    
    // Berechnet, wie viele Snacks gegessen wurden, abhaengig davon welchen Snacktyp (klein oder gross) man uebergibt
    public int calcSnackScore(Boolean[] snacksBool, int counterofSnacks, int counterofSnacksMax, int currScore) {
    	for (int i = 0; i < snacksBool.length; i++) {
    		if (snacksBool[i] == false) counterofSnacks++;
    	}
    	if (counterAllSnacks > 0) currScore = (counterAllSnacks * snacksBool.length) + counterofSnacks;
		else {
			currScore = counterofSnacks;
		}
    	return currScore;
    }
    
    // Setzt Snacks zurueck, sodass wieder jedes Feld einen Snack hat
    public void resetSnacks() {
    	counterofSSnacks = 0;
    	counterofSSnacksMax = 0;
    	counterofBSnacks = 0;
    	counterofBSnacksMax = 0;
    	currSScore = 0;
    	currBScore = 0;
    	for (int i = 0; i < sSnacksFile.length; i++) {
    		sSnacksFile[i].setFill(sSnackImagep);
    		sSnacksBool[i] = true;
    	}
    	for (int i = 0; i < bSnacksFile.length; i++) {
    		bSnacksFile[i].setFill(bSnackImagep);
    		bSnacksBool[i] = true;
    	}
    }
    
    // Setzt Spiel zurueck - Variablen auf 0 setzen und Timer stoppen
    public void resetGame() {
    	timer.stop();
    	timerE.cancel();
    	timerTask.cancel();
        resetPositions();
        resetSnacks();
        counter = 0; counterof1 = 0; counterof0 = 0; counterof2 = 0; counterofSSnacks = 0; counterofBSnacks = 0; counterofSSnacksMax = 0; counterofBSnacksMax = 0; counterAllSnacks = 0;
        currSScore = 0; currBScore = 0;
        endScore = 0;
        currtempMaxScore = 0; currMaxScore = 0;
        xwertfilewalls = 0;
        ywertfilewalls = 0;
        leben = 3;
        for(int i = 0; i < 3; i++) {
            heart[i].setFill(new ImagePattern(heartImage));
        }
        step = 2;
        stepE = 1;
        if(gameOver == true) {
            gameOver = false;
            gameOverBool.setValue(false);
            gameOverBool2 = false;
        }
    }
    
    // Positionen des Spielers (und Hitboxen) sowie der Gegner zuruecksetzen auf Anfangspositionen
    public void resetPositions() {
        player.setTranslateX(xyPlayer[0]);
        player.setTranslateY(xyPlayer[1]);
        playerHb.setTranslateX(xyPlayerHb[0]);
        playerHb.setTranslateY(xyPlayerHb[1]);
        playerHbHalf.setTranslateX(xyPlayerHbHalf[0]);
        playerHbHalf.setTranslateY(xyPlayerHbHalf[1]);
        enemy1.setTranslateX(xy1[0]);
        enemy1.setTranslateY(xy1[1]);
        enemy2.setTranslateX(xy2[0]);
        enemy2.setTranslateY(xy2[1]);
        enemy3.setTranslateX(xy3[0]);
        enemy3.setTranslateY(xy3[1]);
        first1 = true;
        first2 = true;
        first3 = true;
        wPress = false;
        aPress = false;
        sPress = false;
        dPress = false;
    }
    
    // Timer, die dauerhaft bzw. in bestimmten Zeitabstaenden Dinge ausfuehren
    public void timerStart() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(gameOver == false) {
                    movePlayer();
                    snacksLeft();
                    moveEnemy1();
                    moveEnemy2();
                    moveEnemy3();
                }

            }
        };
        timerE = new Timer();

        timerE.scheduleAtFixedRate(timerTask = new TimerTask() {
            public void run() {
                if(gameOver == false) {
                    try {
                        Thread.sleep(5*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    rand1 = (int)(Math.random() * 4 + 1);
                    randWsAd1 = (int)(Math.random() * 2 + 1);
                    rand2 = (int)(Math.random() * 4 + 1);
                    randWsAd2 = (int)(Math.random() * 2 + 1);
                    rand3 = (int)(Math.random() * 4 + 1);
                    randWsAd3 = (int)(Math.random() * 2 + 1);
                }

            }
         }, 0, 8*1000); //alle 8 Sekunden

        timer.start();
    }
    
    // Erstellung der Map: Waende, Snacks, Gegner- & Spielerpositionen - abhaengig von uebergebener Textdatei
    public void createMap(String labySrc) {
    	scanFile(labySrc);
		mengenzaehlen(fileArray);
		
		wallsFile = new Rectangle[counterof1];
		sSnacksFile = new Rectangle[counterof0];
		sSnacksBool = new Boolean[counterof0];
		bSnacksFile = new Rectangle[counterof2];
		bSnacksBool = new Boolean[counterof2];
		
		for (int i = 0; i < sSnacksBool.length; i++) {
			sSnacksBool[i] = true;
		}
		for (int i = 0; i < bSnacksBool.length; i++) {
			bSnacksBool[i] = true;
		}
		
		makeMazeFile(wallsFile, labyWallImagep, 1);
		makeMazeFile(sSnacksFile, sSnackImagep, 0);
		makeMazeFile(bSnacksFile, bSnackImagep, 2);
		
		setPlayer(3);
		
		xy1 = setEnemy(enemy1, enemy1Imagep, 4, xy1);
		xy2 = setEnemy(enemy2, enemy1Imagep, 5, xy2); //5
		xy3 = setEnemy(enemy3, enemy1Imagep, 6, xy3); //6
		
		mapPain.getChildren().clear();
		//Spieler, Hitbox und jede Wand zur Gruppe hinzufuegen
        for (int i = 0; i < wallsFile.length; i++) {
            mapPain.getChildren().add(wallsFile[i]);    
        }
        for (int i = 0; i < sSnacksFile.length; i++) {
            mapPain.getChildren().add(sSnacksFile[i]);
        }
        for (int i = 0; i < bSnacksFile.length; i++) {
            mapPain.getChildren().add(bSnacksFile[i]);
        }
        mapPain.getChildren().addAll(enemy1, enemy2, enemy3, playerHb, player, playerHbHalf);
        
        timerStart();
    }
    
    // Getter und Setter
    public static boolean getGameOver() {
		return gameOver;
    }
    public static int getScore() {
		return endScore;
    }
	public Pane getView() {
		return this;
	}
	public Pane getMapPain() {
		return mapPain;
	}
	public void setMapPain(Pane mapPain) {
		this.mapPain = mapPain;
	}
	
   
}
