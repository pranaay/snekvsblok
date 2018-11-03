package Snek;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Random;

public class PlayGame extends Application{

	private double windowWidth;
	private double windowHeight;
	private double boxWidth = 80;
	private double boxHeight = 50;

	private double gamePaneWidth;
	private double gamePaneHeight;
	private double optionsPaneWidth;
	private double optionsPaneHeight;

	private StackPane[] boxes;
	private StackPane[] boxesAlternate;
	private StackPane[] balls;
	private StackPane[] ballsAlternate;
	private StackPane[] coins;
	private StackPane[] walls;

	private StackPane magnet;
	private StackPane shield;

	private GridPane gridPaneBoard;

	private int numberOfBoxes = 8;
	private int numberOfBalls;
	private int numberOfCoins;
	private int numberOfWalls;

	private int[] heightOfWalls;

	private double wallHeight = 200;

	private boolean alternateFallBoxes = true;
	private boolean wantMagnet;
	private boolean wantShield;

	private DestroyBlock createBlok(){
		DestroyBlock rect = new DestroyBlock(0, -boxHeight, boxWidth, boxHeight);

		Random rand = new Random();
		int selectColor = rand.nextInt(10);
		switch (selectColor){
			case 0: rect.setFill(Color.BLUEVIOLET);
				break;
			case 1: rect.setFill(Color.AQUA);
				break;
			case 2: rect.setFill(Color.TOMATO);
				break;
			case 3: rect.setFill(Color.PINK);
				break;
			case 4: rect.setFill(Color.ORANGE);
				break;
			case 5: rect.setFill(Color.RED);
				break;
			case 6: rect.setFill(Color.MISTYROSE);
				break;
			case 7: rect.setFill(Color.YELLOW);
				break;
			case 8: rect.setFill(Color.MAGENTA);
				break;
			case 9: rect.setFill(Color.MINTCREAM);
				break;
		}
		rect.setStroke(Color.BLACK);
		return rect;
	}

	private void createBoxes(){
		Random rand = new Random();

		if(this.alternateFallBoxes){

			int definetlyInactive = rand.nextInt(numberOfBoxes);
			for (int x = 0; x < 2*numberOfBoxes; x++) {
				DestroyBlock rect = createBlok();

				Text text =  new Text(Integer.toString(rect.getBoxValue()));
				text.setStyle("-fx-font-size: 18px;style: \"-fx-font-weight: bold\";");
				text.setY(-boxHeight);

				StackPane stackPane = new StackPane();
				stackPane.getChildren().addAll(rect, text);
				if(x < numberOfBoxes){
					int inactiveBlock = rand.nextInt(numberOfBoxes);
					if(inactiveBlock == x || x == definetlyInactive){
						continue;
					}
					stackPane.setTranslateY(-(wallHeight+boxHeight+boxHeight));
					boxes[x] = stackPane;
//                    boxes[x].setLayoutY(-boxHeight);
					gridPaneBoard.add(boxes[x], x, 0);
					boxes[x].setTranslateY(-(wallHeight+boxHeight+boxHeight));
				}
				else{
					int inactiveBlock = rand.nextInt(numberOfBoxes);
					if(inactiveBlock == x-numberOfBoxes || x-numberOfBoxes == definetlyInactive){
						continue;
					}
					stackPane.setTranslateY(-(wallHeight+boxHeight+boxHeight));
					boxesAlternate[x-numberOfBoxes] = stackPane;
					gridPaneBoard.add(boxesAlternate[x-numberOfBoxes], x-numberOfBoxes, 0);
					boxesAlternate[x-numberOfBoxes].setTranslateY(-(wallHeight+boxHeight+boxHeight));
				}
			}
		}
		else {
			int definetlyInactive = rand.nextInt(numberOfBoxes);
			for (int x = 0; x < numberOfBoxes; x++) {
				DestroyBlock rect = createBlok();

				int inactiveBlock = rand.nextInt(numberOfBoxes);
				if(inactiveBlock == x || x == definetlyInactive){
					continue;
				}

				Text text =  new Text(Integer.toString(rect.getBoxValue()));
				text.setStyle("-fx-font-size: 18px;style: \"-fx-font-weight: bold\";");
				text.setY(-boxHeight);

				StackPane stackPane = new StackPane();
				stackPane.getChildren().addAll(rect, text);
				stackPane.setTranslateY(-(wallHeight+boxHeight+boxHeight));
				boxes[x] = stackPane;
				boxes[x].setTranslateY(-(wallHeight+boxHeight+boxHeight));
				gridPaneBoard.add(boxes[x], x, 0);
			}
		}

	}

	private void boxesFall(){
		Path [] pathBoxes = new Path[boxes.length];
		boolean setNext = false;
		for(int i=0; i<pathBoxes.length; i++){
			if(boxes[i] == null)
				continue;

			pathBoxes[i] = new Path();

			pathBoxes[i].getElements().add(new MoveTo(boxes[i].getTranslateX() + boxWidth/2, boxes[i].getTranslateY()));
			pathBoxes[i].getElements().add(new LineTo(boxes[i].getTranslateX() + boxWidth/2, gamePaneHeight + (wallHeight+boxHeight+boxHeight)));
			PathTransition pathTransition = new PathTransition();
			pathTransition.setDuration(Duration.millis(3000));
			pathTransition.setPath(pathBoxes[i]);
			pathTransition.setNode(boxes[i]);
			pathTransition.play();
			if(!setNext) {
				setNext = true;
//                pathTransition.setOnFinished(e -> nextCycle());
			}
		}

		if(alternateFallBoxes){
			alternateFallBoxes = false;
			Path [] pathBoxesAlternate = new Path[boxesAlternate.length];

			for(int i=0; i<pathBoxesAlternate.length; i++){

				if(boxesAlternate[i] == null)
					continue;

				pathBoxesAlternate[i] = new Path();

				pathBoxesAlternate[i].getElements().add(new MoveTo(boxesAlternate[i].getTranslateX() + boxWidth/2, boxesAlternate[i].getTranslateY()));
				pathBoxesAlternate[i].getElements().add(new LineTo(boxesAlternate[i].getTranslateX() + boxWidth/2, gamePaneHeight + (wallHeight+boxHeight+boxHeight)));
				PathTransition pathTransition = new PathTransition();
				pathTransition.setDuration(Duration.millis(3000));
				pathTransition.setPath(pathBoxesAlternate[i]);
				pathTransition.setNode(boxesAlternate[i]);
				pathTransition.setDelay(new Duration(1500));
				pathTransition.play();
			}
		}
	}

	private void createBalls(){
		Random rand = new Random();

		for(int i=0; i<2*numberOfBalls; i++){
			int x = rand.nextInt(numberOfBoxes);
			int y = 0;
			while(y == 0){
				y = rand.nextInt(20);
				y *= -1;
			}

			if(i < numberOfBalls) {
				StackPane ball = new StackPane();
				Ball rawBall = new Ball(15);
				rawBall.setFill(Color.PINK);
				Text text = new Text(Integer.toString(rand.nextInt(10)));
				ball.getChildren().addAll(rawBall, text);
				gridPaneBoard.add(ball, x, 0);
				balls[i] = ball;

				balls[i].setTranslateY((-wallHeight+boxHeight+boxHeight));
			}
			else{
				StackPane ball = new StackPane();
				Ball rawBall = new Ball(15);
				rawBall.setFill(Color.BLUE);
				Text text = new Text(Integer.toString(rand.nextInt(10)));
				ball.getChildren().addAll(rawBall, text);

				gridPaneBoard.add(ball, x, 0);
				ballsAlternate[i-numberOfBalls] = ball;

				ballsAlternate[i-numberOfBalls].setTranslateY(-(wallHeight+boxHeight+boxHeight));
			}
		}
	}

	private void ballsFall(){
		Path [] pathBalls = new Path[balls.length];
		Path [] pathBallsAlternate = new Path[balls.length];

		Random rand = new Random();

		for(int i=0; i<pathBalls.length; i++) {

			if (boxes[i] == null)
				continue;
			pathBalls[i] = new Path();

			pathBalls[i].getElements().add(new MoveTo(balls[i].getTranslateX() + boxWidth / 2, balls[i].getTranslateY()));
			pathBalls[i].getElements().add(new LineTo(balls[i].getTranslateX() + boxWidth / 2, gamePaneHeight + (wallHeight+boxHeight+boxHeight)));
			PathTransition pathTransition = new PathTransition();
			pathTransition.setDuration(Duration.millis(3000));
			pathTransition.setPath(pathBalls[i]);
			pathTransition.setNode(balls[i]);
			pathTransition.setDelay(new Duration(700 + rand.nextInt(500)));
			pathTransition.play();
		}

		boolean setOnFinished = false;
		for(int i=0; i<ballsAlternate.length; i++){
			pathBallsAlternate[i] = new Path();

			pathBallsAlternate[i].getElements().add(new MoveTo(ballsAlternate[i].getTranslateX() + boxWidth/2, ballsAlternate[i].getTranslateY()));
			pathBallsAlternate[i].getElements().add(new LineTo(ballsAlternate[i].getTranslateX() + boxWidth/2, gamePaneHeight + (wallHeight+boxHeight+boxHeight)));
			PathTransition pathTransition = new PathTransition();
			pathTransition.setDuration(Duration.millis(3000));
			pathTransition.setPath(pathBallsAlternate[i]);
			pathTransition.setNode(ballsAlternate[i]);
			pathTransition.setDelay(new Duration(2000));
			if(!setOnFinished) {
				setOnFinished = true;
				pathTransition.setOnFinished(event -> nextCycle());
			}
			pathTransition.play();
		}
	}

	private void createCoins(){
		Random rand = new Random();

		for(int i=0; i<numberOfCoins; i++){
			int x = rand.nextInt(numberOfBoxes);

			StackPane coin = new StackPane();
			Coin rawCoin = new Coin(10);
			rawCoin.setFill(Color.GOLD);
			coin.getChildren().add(rawCoin);

			gridPaneBoard.add(coin, x, 0);
			coins[i] = coin;

			coins[i].setTranslateY(-(wallHeight+boxHeight+boxHeight));
		}
	}

	private void coinsFall(){
		Path [] pathCoins = new Path[numberOfCoins];
		Random rand = new Random();

		for(int i=0; i<pathCoins.length; i++){

			pathCoins[i] = new Path();

			pathCoins[i].getElements().add(new MoveTo(coins[i].getTranslateX() + boxWidth/2, coins[i].getTranslateY()));
			pathCoins[i].getElements().add(new LineTo(coins[i].getTranslateX() + boxWidth/2, gamePaneHeight + (wallHeight+boxHeight+boxHeight)));
			PathTransition pathTransition = new PathTransition();
			pathTransition.setDuration(Duration.millis(3000));
			pathTransition.setPath(pathCoins[i]);
			pathTransition.setNode(coins[i]);
			pathTransition.setDelay(new Duration(900 + rand.nextInt(500)));
			pathTransition.play();
		}
	}

	private void addMagnet(){
	    Random rand = new Random();

	    int x = rand.nextInt(numberOfBoxes);

	    Magnet mag = new Magnet(10);
	    mag.setFill(Color.BLACK);
	    Text text = new Text("U");
        text.setFont(Font.font ("Verdana", 20));
        text.setStyle("-fx-font-weight: bold");
        text.setFill(Color.MAGENTA);
        magnet.getChildren().addAll(mag, text);
        gridPaneBoard.add(magnet, x, 2);

        magnet.setTranslateY(-(wallHeight+boxHeight+boxHeight));
    }

    private void magnetFall(){
	    Path magPath = new Path();
	    Random rand = new Random();

        magPath.getElements().add(new MoveTo(magnet.getTranslateX() + boxWidth/2, magnet.getTranslateY()));
        magPath.getElements().add(new LineTo(magnet.getTranslateX() + boxWidth/2, gamePaneHeight + (wallHeight+boxHeight+boxHeight)));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(3000));
        pathTransition.setPath(magPath);
        pathTransition.setNode(magnet);
        pathTransition.setDelay(new Duration(700 + rand.nextInt(600)));
        pathTransition.play();
    }

	private void createWalls(){
		Random rand = new Random();
		for (int x = 0; x < numberOfWalls; x++) {
			int needAWall = rand.nextInt(10);
			if(needAWall < 4)
				continue;

			int height = 100;
			Wall wall = new Wall(wallHeight);
			heightOfWalls[x] = height;
			wall.setFill(Color.WHITE);

			StackPane stackPane = new StackPane();
			stackPane.getChildren().addAll(wall);
//            stackPane.setTranslateY(-height);
//            stackPane.setTranslateX(-boxWidth/2);
			walls[x] = stackPane;
			gridPaneBoard.add(walls[x], x, 1);
			walls[x].setTranslateY(-(wallHeight+boxHeight+boxHeight/2));
		}
	}

	private void wallsFall(){
		Path [] pathWalls = new Path[walls.length];
//        boolean setNext = false;
		for(int i=0; i<pathWalls.length; i++){
			if(walls[i] == null)
				continue;

			pathWalls[i] = new Path();

			pathWalls[i].getElements().add(new MoveTo(walls[i].getTranslateX(), walls[i].getTranslateY()));
			pathWalls[i].getElements().add(new LineTo(walls[i].getTranslateX(), gamePaneHeight + (wallHeight+boxHeight+3*boxHeight/2)));
			PathTransition pathTransition = new PathTransition();
			pathTransition.setDuration(Duration.millis(3000));
			pathTransition.setPath(pathWalls[i]);
			pathTransition.setNode(walls[i]);
//            pathTransition.setDelay(new Duration(150));
			pathTransition.play();
		}
	}

	private void nextCycle(){

		Random rand = new Random();

		// Boxes
		boxes = new StackPane[numberOfBoxes];
		boxesAlternate = new StackPane[numberOfBoxes];

		int alternate = rand.nextInt(3);
		if(alternate == 0){
			this.alternateFallBoxes = true;
		}

		createBoxes();
		boxesFall();

		// Walls
		numberOfWalls = 1 + rand.nextInt(numberOfBoxes);
		walls = new StackPane[numberOfWalls];
		heightOfWalls = new int[numberOfWalls];
		createWalls();
		wallsFall();

		// Balls
		numberOfBalls = 1 + rand.nextInt(numberOfBoxes/2);
//        System.out.println(numberOfBalls);
		balls = new StackPane[numberOfBalls];
		ballsAlternate = new StackPane[numberOfBalls];

		createBalls();
		ballsFall();

		// Coins
		numberOfCoins = 1 + rand.nextInt(numberOfBalls / 2 == 0 ? 1 : numberOfBalls/2);
		coins = new StackPane[numberOfCoins];
		createCoins();
		coinsFall();

		// Magnet
        int mag = rand.nextInt(10);
        if(mag == 0){
            magnet = new StackPane();
            addMagnet();
            magnetFall();
        }

        // Shields
	}

	@Override
	public void start(Stage primaryStage) {

		gridPaneBoard = new GridPane();
		HBox hBox = new HBox();
		hBox.setTranslateX(20);
		hBox.setTranslateY(20);
		hBox.setSpacing(40);
		primaryStage.setResizable(false);
		Scene scene = new Scene(new Group(hBox));
		primaryStage.show();
		primaryStage.setMinHeight(windowHeight);
		primaryStage.setMinWidth(windowWidth);
		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		scene.setFill(Color.DARKGREY);
		primaryStage.setTitle("Snek");

		windowWidth = primaryStage.getWidth();
		windowHeight = primaryStage.getHeight();

		gamePaneWidth = 0.6 * windowWidth - 40;
		gamePaneHeight = windowHeight - 60;
		optionsPaneWidth = 0.4 * windowWidth - 40;
		optionsPaneHeight = windowHeight - 60;
		double centerOfGamePaneHeight = gamePaneHeight/2;
		double centerOfGamePaneWidth = gamePaneWidth/2;

		boxWidth = Math.floor(gamePaneWidth/numberOfBoxes);

		SplitPane gamePane = new SplitPane();
		gamePane.setOrientation(Orientation.VERTICAL);
		gamePane.setPrefSize(gamePaneWidth, gamePaneHeight);
		hBox.getChildren().add(gamePane);
		gamePane.getItems().add(gridPaneBoard);

		gamePane.setStyle("-fx-background-color: #000000");

		SplitPane optionsPane = new SplitPane();
		optionsPane.setOrientation(Orientation.VERTICAL);
		optionsPane.setPrefSize(optionsPaneWidth, optionsPaneHeight);
		hBox.getChildren().add(optionsPane);

		Snake snake = new Snake(10, gridPaneBoard, centerOfGamePaneHeight, centerOfGamePaneWidth);

		nextCycle();
	}


//    public static void main(String[] args) {
//        launch(args);
//    }
}