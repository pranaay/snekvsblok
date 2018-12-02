package snek;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Contains definitions for The Game. Tells how the game start and executes.
 */
public class PlayGame extends Application{
    private Label score;

    private Image snekmage;

    private double windowWidth;
    private double windowHeight;
    private double boxWidth = 80;
    private double boxHeight = 50;
    private double gamePaneWidth;
    private double gamePaneHeight;
    private double optionsPaneWidth;
    private double optionsPaneHeight;
    private double wallHeight = 200;
    private double snakeOldX = 0;

    private StackPane[] boxes;
    private StackPane[] boxesAlternate;
    private StackPane[] balls;
    private StackPane[] ballsAlternate;
    private StackPane[] coins;
    private Wall[] walls;

    private StackPane magnet;
    private StackPane shield;
    private StackPane destroyAllShiz;

    private GridPane gameGridPane;
    private GridPane optionsGridPane;

    private int numberOfBoxes = 8;
    private int numberOfBalls;
    private int numberOfCoins;
    private int numberOfWalls;
    private int PlayerScore = 0;
    private int TransitionTime = 3000;

    private ChoiceBox<String> Choices ;
    private Button confirmButton;
    private Stage window;
    private Main main;
    private ResultWindow resultWindow;
    private PlayGame game;

    private String cheat = "";

    private boolean alternateFallBoxes = true;
    private boolean touchingWalls = false;
    private boolean ballsAltGone = false;
    private boolean wallsAltGone = false;
	private boolean wallsAltComing = false;
	private boolean mouseMove = false;

    private Snake snake;

    private Path[] pathBoxes;
    private Path[] alternatePathBoxes;

    private PathTransition[] pathTransitionBoxes;
    private PathTransition[] pathTransitionBoxesAlternate;
    private PathTransition[] pathTransitionBalls;
    private PathTransition[] pathTransitionBallsAlternate;
    private PathTransition[] pathTransitionCoins;
    private PathTransition[] pathTransitionWalls;

    private PathTransition pathTransitionShield;
    private PathTransition pathTransitionMagnet;
    private PathTransition pathTransitionDestroyAll;

    final String IDLE_BUTTON_STYLE = "-fx-padding: 8 15 15 15;"+
            "-fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;"+
            "-fx-background-radius: 8;"+
            "-fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%), #9d4024, #d86e3a, radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c);"+
            "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );"+
            "-fx-font-weight: bold;"+
            "-fx-font-size: 1.1em;" ;

    final String HOVEROVER_BUTTON_STYLE ="-fx-padding: 8 15 15 15;"+
            "-fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;"+
            "-fx-background-radius: 8;"+
            " -fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%), #9d4024, #d86e3a,radial-gradient(center 50% 50%, radius 100%, #ea7f4b, #c54e2c);"+
            "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );"+
            "-fx-font-weight: bold;"+
            "-fx-font-size: 1.1em;" ;
    final String PRESSED_BUTTON_STYLE = "-fx-padding: 10 15 13 15;"+
            "-fx-background-insets: 2 0 0 0,2 0 3 0, 2 0 4 0, 2 0 5 0;"+
            "-fx-background-radius: 8;"+
            " -fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%), #9d4024, #d86e3a,radial-gradient(center 50% 50%, radius 100%, #ea7f4b, #c54e2c);"+
            "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );"+
            "-fx-font-weight: bold;"+
            "-fx-font-size: 1.1em;" ;

    public void setMouseMove(boolean value){
    	this.mouseMove = value;
	}

	/**
	 * Method to set the snake background/skin.
	 * @param i Image/Skin of the snake.
	 */
	public void setSnekmage(Image i){
        this.snekmage = i ;
    }

	/**
	 * Returns the snake.
	 * @return Snake
	 */
	public Snake getSnake(){
        return this.snake;
    }

	/**
	 *  Initialize the snake
	 * @param i Snake object to initialize the local snake object.
	 */
	public void setSnake(Snake i){
        this.snake = i ;
    }

	/**
	 * Method to create a random blok
	 * @return A random blok
	 */
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
        rect.setStrokeWidth(4);
        return rect;
    }

	/**
	 * A method to create and initialize bloks. It also sets the listeners for the boxes.
	 */
	private void createBoxes(){
        Random rand = new Random();

		int definetlyInactive = rand.nextInt(numberOfBoxes);
		for(int x = 0; x<2*numberOfBoxes; x++) {

			DestroyBlock rect;

			if(x < numberOfBoxes){
				int inactiveBlock = rand.nextInt(numberOfBoxes);
				if(inactiveBlock == x || x == definetlyInactive){
					rect = new DestroyBlock(0, -boxHeight, boxWidth, boxHeight);
					rect.setFill(Color.TRANSPARENT);
					rect.setInactive();
				}
				else {
					rect = createBlok();
					rect.setFill(Color.RED);
				}

				Text text =  new Text(Integer.toString(rect.getBoxValue()));
				text.setStyle("-fx-font-size: 18px;style: \"-fx-font-weight: bold\";");
				text.setY(-boxHeight);

				StackPane stackPane = new StackPane();
				if(inactiveBlock == x-numberOfBoxes || x-numberOfBoxes == definetlyInactive){
					stackPane.getChildren().addAll(rect);
				}
				else{
					stackPane.getChildren().addAll(rect, text);
				}

				stackPane.setTranslateY(-(wallHeight+boxHeight+boxHeight));
				boxes[x] = stackPane;
				gameGridPane.add(boxes[x], x, 0);
				boxes[x].setTranslateY(-(wallHeight+boxHeight+boxHeight));
			}
			if(x >= numberOfBoxes && this.alternateFallBoxes){
				int inactiveBlock = rand.nextInt(numberOfBoxes);
				if(inactiveBlock == x-numberOfBoxes || x-numberOfBoxes == definetlyInactive){
					rect = new DestroyBlock(0, -boxHeight, boxWidth, boxHeight);
					rect.setFill(Color.TRANSPARENT);
					rect.setInactive();
				}
				else {
					rect = createBlok();
					rect.setFill(Color.BLUE);
				}

				Text text =  new Text(Integer.toString(rect.getBoxValue()));
				text.setStyle("-fx-font-size: 18px;style: \"-fx-font-weight: bold\";");
				text.setY(-boxHeight);

				StackPane stackPane = new StackPane();
				if(inactiveBlock == x-numberOfBoxes || x-numberOfBoxes == definetlyInactive){
					stackPane.getChildren().addAll(rect);
				}
				else{
					stackPane.getChildren().addAll(rect, text);
				}

				stackPane.setTranslateY(-(wallHeight+boxHeight+boxHeight));
				boxesAlternate[x-numberOfBoxes] = stackPane;
				gameGridPane.add(boxesAlternate[x-numberOfBoxes], x-numberOfBoxes, 0);
				boxesAlternate[x-numberOfBoxes].setTranslateY(-(wallHeight+boxHeight+boxHeight));
			}
		}

		for(int x = 0; x<boxes.length; x++){
            final int index = x;

            if(boxes[x] == null)
                continue;

            boxes[x].boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
                @Override
                public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
                    Ball ball;
                    DestroyBlock blok;

                    try{
                        ball = (Ball) snake.getFirst();
                        blok = (DestroyBlock) boxes[index].getChildren().get(0);
                    } catch (Exception e){
                        ball = null;
                        blok = null;
                    }

                    if(ball == null || blok == null)
                        return;

//                    Shape intersect = Shape.intersect(ball, blok);
                    if (boxes[index].getBoundsInParent().intersects(ball.getBoundsInParent()) && blok.isActive()) {

                        boolean hit = blok.hit(snake.getLength());

                        int tempScore = 0;

                        if(snake.isHasShield()){
                            snake.removeBalls(blok.getBoxValue(),gameGridPane);
//                            boxes[index].getChildren().remove(0, 1);
                            tempScore += blok.getBoxValue();
                            tempScore += Integer.parseInt(score.getText());
                            score.setText(String.valueOf(tempScore));

                            addFire(index, blok);
                        }
                        else if(hit && blok.getBoxValue() <= 5){
                            snake.removeBalls(blok.getBoxValue(),gameGridPane);
//                            boxes[index].getChildren().remove(0, 1);
                            tempScore += blok.getBoxValue();
                            tempScore += Integer.parseInt(score.getText());
                            score.setText(String.valueOf(tempScore));

                            addFire(index, blok);
                        }
                        else if(blok.getBoxValue() > 5 && !snake.isHasShield() && hit){
                            pauseBoxes();

                            tempScore += 5;
                            tempScore += Integer.parseInt(score.getText());
                            score.setText(String.valueOf(tempScore));

                            snake.removeBalls(5, gameGridPane);

                            Text text = (Text) boxes[index].getChildren().get(1);
                            text.setText(String.valueOf(blok.getBoxValue() - 5));
                            blok.reduceValue(5);
                        }
                        else{
                            try {
								PlayGame.super.stop();
                                stopAllAnim();
                                window.close();
                                resultWindow.setScore(Integer.parseInt(score.getText()));
                                resultWindow.start(window);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
        }

        if(this.alternateFallBoxes) {
			for (int x = 0; x < boxesAlternate.length; x++) {
				final int index = x;

				if (boxesAlternate[x] == null)
					continue;

				boxesAlternate[x].boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
					@Override
					public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
						Ball ball;
						DestroyBlock blok;

						try {
							ball = (Ball) snake.getFirst();
							blok = (DestroyBlock) boxesAlternate[index].getChildren().get(0);
						} catch (Exception e) {
							ball = null;
							blok = null;
						}

						if (ball == null || blok == null)
							return;

						if (boxesAlternate[index].getBoundsInParent().intersects(ball.getBoundsInParent()) && blok.isActive()) {

							boolean hit = blok.hit(snake.getLength());

							int tempScore = 0;

							if (snake.isHasShield()) {
								snake.removeBalls(blok.getBoxValue(), gameGridPane);
								//                            boxesAlternate[index].getChildren().remove(0, 1);
								tempScore += blok.getBoxValue();
								tempScore += Integer.parseInt(score.getText());
								score.setText(String.valueOf(tempScore));

								addFireAlternate(index, blok);
							} else if (hit && blok.getBoxValue() <= 5) {
								snake.removeBalls(blok.getBoxValue(), gameGridPane);
								//                            boxesAlternate[index].getChildren().remove(0, 1);
								tempScore += blok.getBoxValue();
								tempScore += Integer.parseInt(score.getText());
								score.setText(String.valueOf(tempScore));

								addFireAlternate(index, blok);
							} else if (blok.getBoxValue() > 5 && hit) {
								pauseBoxes();

								tempScore += 5;
								tempScore += Integer.parseInt(score.getText());
								score.setText(String.valueOf(tempScore));

								snake.removeBalls(5, gameGridPane);

								Text text = (Text) boxesAlternate[index].getChildren().get(1);
								text.setText(String.valueOf(blok.getBoxValue() - 5));
								blok.reduceValue(5);
							} else {
								try {
									PlayGame.super.stop();
									stopAllAnim();
									window.close();
									resultWindow.setScore(Integer.parseInt(score.getText()));
									resultWindow.start(window);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
				});
			}
		}
    }

	/**
	 * Method to stop all animation.
	 */
	private void stopAllAnim(){
        for(int i=0; i<pathBoxes.length; i++){
            if(pathTransitionBoxes[i] != null)
                pathTransitionBoxes[i].stop();

            if(pathTransitionBoxesAlternate[i] != null)
                pathTransitionBoxesAlternate[i].stop();
        }

        for(int i=0; i<coins.length; i++){
            if(pathTransitionCoins[i] != null)
                pathTransitionCoins[i].stop();
        }

        for(int i=0; i<balls.length; i++){
            if(pathTransitionBalls[i] != null)
                pathTransitionBalls[i].stop();
        }

        for(int i=0; i<ballsAlternate.length; i++){
            if(pathTransitionBallsAlternate[i] != null)
                pathTransitionBallsAlternate[i].stop();
        }

        for(int i=0; i<walls.length; i++){
            if(pathTransitionWalls[i] != null)
                pathTransitionWalls[i].stop();
        }

        if(pathTransitionMagnet != null)
            pathTransitionMagnet.stop();

        if(pathTransitionShield != null)
            pathTransitionShield.stop();
    }

	/**
	 * Method to add fire to a blok in alternate boxes which gets destroyed.
	 * @param index Index of the element where to add fire
	 * @param blok Blok to get the fire.
	 */
	private void addFireAlternate(int index, DestroyBlock blok){
        Timer timer = new Timer();

        try{
            boxesAlternate[index].getChildren().remove(1);
            boxesAlternate[index].getChildren().remove(0);
        } catch (Exception e){

        }

        DestroyBlock rect = createBlok();
        boxesAlternate[index].setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(getClass().getClassLoader().getResource("littt.png").toString())), CornerRadii.EMPTY, Insets.EMPTY)));
        rect.setFill(Color.TRANSPARENT);
//        rect.setStyle("-fx-background-image: url(littt.png)");

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        boxesAlternate[index].setBackground(Background.EMPTY);
                    }
                });
            }
        };

        timer.schedule(timerTask, 500);
    }

	/**
	 * Method to add fire to a blok which gets destroyed
	 * @param index Index of the element where to add fire
	 * @param blok Blok to get the fire.
	 */
    private void addFire(int index, DestroyBlock blok){
        Timer timer = new Timer();

        try{
            boxes[index].getChildren().remove(1);
            boxes[index].getChildren().remove(0);
        } catch (Exception e){
        	e.printStackTrace();
		}

        DestroyBlock rect = createBlok();
        if(boxes[index] == null)
            return;

        boxes[index].setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(getClass().getClassLoader().getResource("littt.png").toString())), CornerRadii.EMPTY, Insets.EMPTY)));
        rect.setFill(Color.TRANSPARENT);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if(boxes[index] != null)
                            boxes[index].setBackground(Background.EMPTY);
                    }
                });
            }
        };

        timer.schedule(timerTask, 500);
    }

	/**
	 * Create animation to bloks falling.
	 */
	private void boxesFall(){
        pathBoxes = new Path[boxes.length];
        pathTransitionBoxes = new PathTransition[boxes.length];
		alternatePathBoxes = new Path[boxesAlternate.length];
		pathTransitionBoxesAlternate = new PathTransition[boxesAlternate.length];

        for(int i=0; i<pathBoxes.length; i++){
            if(boxes[i] == null)
                continue;

            pathBoxes[i] = new Path();
            pathBoxes[i].getElements().add(new MoveTo(boxes[i].getTranslateX() + boxWidth/2, boxes[i].getTranslateY()));
            pathBoxes[i].getElements().add(new LineTo(boxes[i].getTranslateX() + boxWidth/2, gamePaneHeight + (wallHeight+boxHeight+boxHeight)));
            pathTransitionBoxes[i] = new PathTransition();
            pathTransitionBoxes[i].setDuration(Duration.millis(TransitionTime));
            pathTransitionBoxes[i].setPath(pathBoxes[i]);
            pathTransitionBoxes[i].setNode(boxes[i]);
            pathTransitionBoxes[i].play();
        }

        if(alternateFallBoxes){
            alternateFallBoxes = false;
            boolean setOnFinished = false;

            for(int i=0; i<alternatePathBoxes.length; i++){
                if(boxesAlternate[i] == null)
                    continue;

                alternatePathBoxes[i] = new Path();
                alternatePathBoxes[i].getElements().add(new MoveTo(boxesAlternate[i].getTranslateX() + boxWidth/2, boxesAlternate[i].getTranslateY()));
                alternatePathBoxes[i].getElements().add(new LineTo(boxesAlternate[i].getTranslateX() + boxWidth/2, gamePaneHeight + (wallHeight+boxHeight+boxHeight)));
                pathTransitionBoxesAlternate[i] = new PathTransition();
                pathTransitionBoxesAlternate[i].setDuration(Duration.millis(TransitionTime));
                pathTransitionBoxesAlternate[i].setPath(alternatePathBoxes[i]);
                pathTransitionBoxesAlternate[i].setNode(boxesAlternate[i]);
                pathTransitionBoxesAlternate[i].setDelay(new Duration(1500));
                if(!setOnFinished){
                	setOnFinished = true;
					pathTransitionBoxesAlternate[i].setOnFinished(event -> nextCycleBox());
				}
                pathTransitionBoxesAlternate[i].play();
            }
        }
    }

	/**
	 * Method to call the next loop, if the walls reach later than the balls
	 */
	private void nextCycleBox(){
		wallsAltGone = true;
		if(ballsAltGone)
			nextCycle();
	}

	/**
	 * Pause the animation of all tokens.
	 */
	private void pauseBoxes(){
        Timer timer = new Timer();

        for(int i=0; i<pathBoxes.length; i++){
            if(pathTransitionBoxes[i] != null)
                pathTransitionBoxes[i].pause();

            if(pathTransitionBoxesAlternate[i] != null)
                pathTransitionBoxesAlternate[i].pause();
        }

        for(int i=0; i<coins.length; i++){
            if(pathTransitionCoins[i] != null)
                pathTransitionCoins[i].pause();
        }

        for(int i=0; i<balls.length; i++){
            if(pathTransitionBalls[i] != null)
                pathTransitionBalls[i].pause();
        }

        for(int i=0; i<pathTransitionBallsAlternate.length; i++){
            if(pathTransitionBallsAlternate[i] != null)
                pathTransitionBallsAlternate[i].pause();
            else
            	System.out.println("Not stoppping");
        }

        for(int i=0; i<walls.length; i++){
            if(pathTransitionWalls[i] != null)
                pathTransitionWalls[i].pause();
        }

        if(pathTransitionMagnet != null)
            pathTransitionMagnet.pause();

        if(pathTransitionShield != null)
            pathTransitionShield.pause();

        if(pathTransitionDestroyAll != null)
            pathTransitionDestroyAll.pause();

        TimerTask task = new TimerTask()
        {
            public void run()
            {
                for(int i=0; i<pathBoxes.length; i++){
                    if(pathTransitionBoxes[i] != null)
                        pathTransitionBoxes[i].play();

                    if(pathTransitionBoxesAlternate[i] != null)
                        pathTransitionBoxesAlternate[i].play();
                }

                for(int i=0; i<coins.length; i++){
                    if(pathTransitionCoins[i] != null)
                        pathTransitionCoins[i].play();
                }

                for(int i=0; i<balls.length; i++){
                    if(pathTransitionBalls[i] != null)
                        pathTransitionBalls[i].play();
                }

                for(int i=0; i<ballsAlternate.length; i++){
                    if(pathTransitionBallsAlternate[i] != null)
                        pathTransitionBallsAlternate[i].play();
                }

                for(int i=0; i<walls.length; i++){
                    if(pathTransitionWalls[i] != null)
                        pathTransitionWalls[i].play();
                }

                if(pathTransitionMagnet != null)
                    pathTransitionMagnet.play();

                if(pathTransitionShield != null)
                    pathTransitionShield.play();

                if(pathTransitionDestroyAll != null)
                    pathTransitionDestroyAll.play();
            }
        };
        timer.schedule(task, 250);
    }

	/**
	 * Creates ball tokens for the game. Also adds collision listeners.
	 */
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
                int temp = 1 + rand.nextInt(10);

                Ball rawBall = new Ball(15, temp);
				rawBall.setFill(Color.PINK);
				Text text = new Text(Integer.toString(temp));

				StackPane ball = new StackPane();
				ball.getChildren().addAll(rawBall, text);

				gameGridPane.add(ball, x, 0);

				balls[i] = ball;
                balls[i].setTranslateY((-wallHeight+boxHeight+boxHeight));
            }
            else {
                int temp = 1 + rand.nextInt(10);

                Ball rawBall = new Ball(15, temp);
				rawBall.setFill(Color.PINK);
				Text text = new Text(Integer.toString(temp));

				StackPane ball = new StackPane();
				ball.getChildren().addAll(rawBall, text);

				gameGridPane.add(ball, x, 2);

				ballsAlternate[i-numberOfBalls] = ball;
				ballsAlternate[i-numberOfBalls].setTranslateY(-(wallHeight+boxHeight+boxHeight));
            }
        }

        for(int x = 0; x<numberOfBalls; x++){
            final int index = x;

            balls[x].boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
                @Override
                public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
					Ball ball;
                    Ball boll;

                    try{
                        ball = (Ball) snake.getFirst();
                        boll = (Ball) balls[index].getChildren().get(0);
                    } catch (Exception e){
                        ball = null;
                        boll = null;
                    }

                    if(ball == null || boll == null)
                        return;

                    Shape intersect = Shape.intersect(ball, boll);

                    if (intersect.getBoundsInLocal().getWidth() != -1) {
                        snake.addBalls(boll.getValue(),gameGridPane);
//                        gameGridPane.getChildren().removeAll(ballsAlternate[index]);
						balls[index].getChildren().removeAll(boll);
                    }
                }
            });
        }
        for(int x = 0; x<ballsAlternate.length; x++){
            final int index = x;

            ballsAlternate[x].boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
                @Override
                public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
//                            System.out.println(newValue);
                    Ball ball;
                    Ball boll;

                    try{
                        ball = (Ball) snake.getFirst();
                        boll = (Ball) ballsAlternate[index].getChildren().get(0);
                    } catch (Exception e){
                        ball = null;
                        boll = null;
                    }

                    if(boll == null || ball == null)
                        return;
//                            System.out.println(ball + " " + blok);
                    Shape intersect = Shape.intersect(ball, boll);
                    if (intersect.getBoundsInLocal().getWidth() != -1) {
                        snake.addBalls(boll.getValue(),gameGridPane);
						ballsAlternate[index].getChildren().removeAll(boll);
					}
                }
            });
        }
    }

	/**
	 * Adds animation to balls, falling down.
	 */
	private void ballsFall(){
        Path [] pathBalls = new Path[balls.length];
        Path [] pathBallsAlternate = new Path[balls.length];
        pathTransitionBalls = new PathTransition[balls.length];
		pathTransitionBallsAlternate = new PathTransition[ballsAlternate.length];

        Random rand = new Random();

        for(int i=0; i<pathBalls.length; i++) {

            if (boxes[i] == null)
                continue;
            pathBalls[i] = new Path();

            pathBalls[i].getElements().add(new MoveTo(balls[i].getTranslateX() + boxWidth / 2, balls[i].getTranslateY()));
            pathBalls[i].getElements().add(new LineTo(balls[i].getTranslateX() + boxWidth / 2, gamePaneHeight + (wallHeight+boxHeight+boxHeight)));
            pathTransitionBalls[i] = new PathTransition();
            pathTransitionBalls[i].setDuration(Duration.millis(TransitionTime));
            pathTransitionBalls[i].setPath(pathBalls[i]);
            pathTransitionBalls[i].setNode(balls[i]);
            pathTransitionBalls[i].setDelay(new Duration(500 + rand.nextInt(500)));
            pathTransitionBalls[i].play();
        }

        boolean setOnFinished = false;
        for(int i=0; i<ballsAlternate.length; i++){
            pathBallsAlternate[i] = new Path();

            pathBallsAlternate[i].getElements().add(new MoveTo(ballsAlternate[i].getTranslateX() + boxWidth/2, ballsAlternate[i].getTranslateY()));
            pathBallsAlternate[i].getElements().add(new LineTo(ballsAlternate[i].getTranslateX() + boxWidth/2, gamePaneHeight + (wallHeight+boxHeight+boxHeight)));
            pathTransitionBallsAlternate[i] = new PathTransition();
            pathTransitionBallsAlternate[i].setDuration(Duration.millis(TransitionTime));
            pathTransitionBallsAlternate[i].setPath(pathBallsAlternate[i]);
            pathTransitionBallsAlternate[i].setNode(ballsAlternate[i]);
            pathTransitionBallsAlternate[i].setDelay(new Duration(3000));
            if(!setOnFinished) {
                setOnFinished = true;
                pathTransitionBallsAlternate[i].setOnFinished(event -> nextCycleBalls());
            }
            pathTransitionBallsAlternate[i].play();
        }
    }

	/**
	 * Method to call the next loop, if the balls reach later than the walls
	 */
	private void nextCycleBalls(){
		ballsAltGone = true;
		if(!wallsAltComing)
			nextCycle();
		else if(wallsAltGone)
			nextCycle();
	}

	/**
	 * Creates coins, and adds collision listeners.
	 */
	private void createCoins(){
        Random rand = new Random();

        for(int i=0; i<numberOfCoins; i++){
            int x = rand.nextInt(numberOfBoxes);

            StackPane coin = new StackPane();
            Coin rawCoin = new Coin(10);
            rawCoin.setFill(Color.GOLD);
            coin.getChildren().add(rawCoin);

            gameGridPane.add(coin, x, 0);
            coins[i] = coin;

            coins[i].setTranslateY(-(wallHeight+boxHeight+boxHeight));
        }

        for(int i=0; i<numberOfCoins; i++){
            final int index = i;

            if(coins[i] == null){
                continue;
            }

            coins[i].boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
                @Override
                public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
                    Ball ball;
                    Coin boll;

                    try{
                        ball = (Ball) snake.getFirst();
                        boll = (Coin) coins[index].getChildren().get(0);
                    } catch (Exception e){
                        ball = null;
                        boll = null;
                    }

                    if(ball == null || boll == null)
                        return;

                    Shape intersect = Shape.intersect(ball, boll);

                    if (intersect.getBoundsInLocal().getWidth() != -1) {
                        PlayerScore = Integer.parseInt(score.getText());
                        PlayerScore++;
                        score.setText(String.valueOf(PlayerScore));
                        coins[index].getChildren().remove(0);
                    }
                }
            });
        }
    }

	/**
	 * Adds animation to coins, falling down.
	 */
	private void coinsFall(){
        Path [] pathCoins = new Path[numberOfCoins];
        pathTransitionCoins = new PathTransition[numberOfCoins];
        Random rand = new Random();

        for(int i=0; i<pathCoins.length; i++){

            pathCoins[i] = new Path();

            pathCoins[i].getElements().add(new MoveTo(coins[i].getTranslateX() + boxWidth/2, coins[i].getTranslateY()));
            pathCoins[i].getElements().add(new LineTo(coins[i].getTranslateX() + boxWidth/2, gamePaneHeight + (wallHeight+boxHeight+boxHeight)));
            pathTransitionCoins[i] = new PathTransition();
            pathTransitionCoins[i].setDuration(Duration.millis(3000));
            pathTransitionCoins[i].setPath(pathCoins[i]);
            pathTransitionCoins[i].setNode(coins[i]);
            pathTransitionCoins[i].setDelay(new Duration(200 + rand.nextInt(TransitionTime/6)));
            pathTransitionCoins[i].play();
        }
    }

	/**
	 * Creates Shield, and adds collision listeners.
	 */
	private void addShield(){
        Random rand = new Random();
        shield = new StackPane();

        int x = rand.nextInt(numberOfBoxes);

        Shield tempShield = new Shield();
        tempShield.setFill(Color.SILVER);
        shield.getChildren().add(tempShield);

        gameGridPane.add(shield, x, 3);
        shield.setTranslateY(-(wallHeight+boxHeight+boxHeight));

        shield.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
                Polygon poly;
                Ball ball;
                try{
                    ball = (Ball) snake.getFirst();
                    poly = (Polygon) shield.getChildren().get(0);
                } catch (Exception e){
                    ball = null;
                    poly = null;
                }

                if(ball == null || poly == null)
                    return;

                Shape intersect = Shape.intersect(ball, poly);
                if (intersect.getBoundsInLocal().getWidth() != -1) {
                    snake.getShield(5000);
                    shield.getChildren().remove(0);
                }
            }
        });
    }

	/**
	 * Adds animation to shield, falling down.
	 */
	private void shieldFall(){
        Path shieldPath = new Path();
        pathTransitionShield = new PathTransition();
        Random rand = new Random();

        shieldPath.getElements().add(new MoveTo(shield.getTranslateX() + boxWidth/2, shield.getTranslateY()));
        shieldPath.getElements().add(new LineTo(shield.getTranslateX() + boxWidth/2, gamePaneHeight + (wallHeight+boxHeight+boxHeight)));

        pathTransitionShield.setDuration(Duration.millis(3000));
        pathTransitionShield.setPath(shieldPath);
        pathTransitionShield.setNode(shield);
        pathTransitionShield.setDelay(new Duration(700 + rand.nextInt(TransitionTime/5)));
        pathTransitionShield.play();
    }

	/**
	 * Creates destroyBloks, and adds collision listeners.
	 */
	private void addDestroyAll(){
        Random rand = new Random();
        destroyAllShiz = new StackPane();

        int x = rand.nextInt(numberOfBoxes);

        Image back = new Image(getClass().getClassLoader().getResource("destroyAll.png").toString());

        DestroyAll tempDestroy = new DestroyAll(back);
        destroyAllShiz.getChildren().add(tempDestroy);

        gameGridPane.add(destroyAllShiz, x, 3);
        destroyAllShiz.setTranslateY(-(wallHeight+boxHeight+boxHeight));

        destroyAllShiz.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
                DestroyAll poly;
                Ball ball;
                try{
                    ball = (Ball) snake.getFirst();
                    poly = (DestroyAll) destroyAllShiz.getChildren().get(0);
                } catch (Exception e){
                    ball = null;
                    poly = null;
                }

                if(ball == null || poly == null)
                    return;

                Shape intersect = Shape.intersect(ball, poly);
                if (intersect.getBoundsInLocal().getWidth() != -1) {
                    // Destroy all
                    int tempScore = 0;

                    ((DestroyAll) destroyAllShiz.getChildren().get(0)).setFill(Color.TRANSPARENT);
                    destroyAllShiz.getChildren().remove(0);

                    for(int i=0; i<boxes.length; i++){
                        DestroyBlock blok;

                        if(boxes[i] == null)
                            continue;

                        try {
                            blok = (DestroyBlock) boxes[i].getChildren().get(0);
                        } catch (Exception e){
                            blok = null;
                        }

                        if(blok == null)
                            continue;

                        tempScore += blok.getBoxValue();
                        addFire(i, blok);
                    }
                    for(int i=0; i<boxesAlternate.length; i++){
                        DestroyBlock blok;

                        if(boxesAlternate[i] == null)
                            continue;

                        try {
                            blok = (DestroyBlock) boxesAlternate[i].getChildren().get(0);
                        } catch (Exception e){
                            blok = null;
                        }

                        if(blok == null)
                            continue;

                        tempScore += blok.getBoxValue();
                        addFireAlternate(i, blok);
                    }
                    tempScore += Integer.parseInt(score.getText());
                    score.setText(String.valueOf(tempScore));
                }
            }
        });
    }

	/**
	 * Adds animation to destroyBloks, falling down.
	 */
	private void destroyAllFall(){
        Path destroyPath = new Path();
        pathTransitionDestroyAll = new PathTransition();
        Random rand = new Random();

        destroyPath.getElements().add(new MoveTo(destroyAllShiz.getTranslateX() + boxWidth/2, destroyAllShiz.getTranslateY()));
        destroyPath.getElements().add(new LineTo(destroyAllShiz.getTranslateX() + boxWidth/2, gamePaneHeight + (wallHeight+boxHeight+boxHeight)));

        pathTransitionDestroyAll.setDuration(Duration.millis(3000));
        pathTransitionDestroyAll.setPath(destroyPath);
        pathTransitionDestroyAll.setNode(destroyAllShiz);
        pathTransitionDestroyAll.setDelay(new Duration(700 + rand.nextInt(TransitionTime/5)));
        pathTransitionDestroyAll.play();
    }

	/**
	 * Creates magnet, and adds collision listeners.
	 */
	private void addMagnet(){
        Random rand = new Random();

        int x = rand.nextInt(numberOfBoxes);

//        Magnet mag = new Magnet(10);
//        mag.setFill(Color.BLACK);
        Text text = new Text("U");
        text.setFont(Font.font ("Verdana", 20));
        text.setStyle("-fx-font-weight: bold");
        text.setFill(Color.MAGENTA);
        magnet.getChildren().addAll(text);
        gameGridPane.add(magnet, x, 2);

        magnet.setTranslateY(-(wallHeight+boxHeight+boxHeight));

        magnet.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
                Ball ball;
                Text newMag;
                try{
                    ball = (Ball) snake.getFirst();
                    newMag = (Text) magnet.getChildren().get(0);
                } catch (Exception e){
                    ball = null;
                    newMag = null;
                }

                if(ball == null || newMag == null)
                    return;

                Shape intersect = Shape.intersect(ball, newMag);
                if (intersect.getBoundsInLocal().getWidth() != -1) {
                    magnet.getChildren().remove(0);

                    Bounds[] coinBounds = new Bounds[numberOfCoins];
                    double[] distances = new double[numberOfCoins];

                    for(int i=0; i<numberOfCoins; i++){
                        if(coins[i] == null){
                            coinBounds[i] = null;
                            distances[i] = 9999;
                        }
                        else{
                            coinBounds[i] = coins[i].getBoundsInParent();
                            double X = oldValue.getMinX() - coinBounds[i].getMinX();
                            double Y = oldValue.getMinY() - coinBounds[i].getMinY();
                            double dist = Math.sqrt(Math.pow(X, 2) + Math.pow(Y, 2));
                            distances[i] = dist;
                        }
                    }

                    for(int i=0; i<numberOfCoins; i++){
                        if(distances[i] == 9999)
                            continue;
                        if(distances[i] < 500){
                            // Add the coin to score. :#
                            PlayerScore++;
                            score.setText(String.valueOf(PlayerScore));

                            // take the coins.
                            StackPane theCoin = coins[i];
                            try{
                                theCoin.getChildren().remove(0);
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
    }

	/**
	 * Adds animation to magnet, falling down.
	 */
	private void magnetFall(){
        Path magPath = new Path();
        pathTransitionMagnet = new PathTransition();
        Random rand = new Random();

        magPath.getElements().add(new MoveTo(magnet.getTranslateX() + boxWidth/2, magnet.getTranslateY()));
        magPath.getElements().add(new LineTo(magnet.getTranslateX() + boxWidth/2, gamePaneHeight + (wallHeight+boxHeight+boxHeight)));

        pathTransitionMagnet.setDuration(Duration.millis(3000));
        pathTransitionMagnet.setPath(magPath);
        pathTransitionMagnet.setNode(magnet);
        pathTransitionMagnet.setDelay(new Duration(700 + rand.nextInt(TransitionTime/5)));
        pathTransitionMagnet.play();
    }

	/**
	 * Creates walls, and adds collision listeners.
	 */
	private void createWalls(){
        Random rand = new Random();
        for (int x = 0; x < numberOfWalls; x++) {
            int needAWall = rand.nextInt(10);
            if(needAWall < 4)
                continue;

            Wall wall = new Wall(wallHeight);
            wall.setFill(Color.WHITE);

            walls[x] = wall;
            walls[x].setArcHeight(10);
            walls[x].setArcWidth(10);

            gameGridPane.add(walls[x], x, 1);
            walls[x].setTranslateY(-(wallHeight+boxHeight+boxHeight/2));
        }

//        for(int i=0; i<numberOfWalls; i++){
//            if(walls[i] == null)
//                continue;
//
//            final int index = i;
//
//            walls[i].boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
//                @Override
//                public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
//                    StackPane ball;
//                    Wall wall;
//                    try{
//                        ball = snake.getFirst();
//                        wall = (Wall) walls[index];
//                    } catch (Exception e){
//                        ball = null;
//                        wall = null;
//                    }
//
//                    if(ball == null || wall == null)
//                        return;
//
////                    Shape intersect = Shape.intersect(ball, wall);
////                    if (intersect.getBoundsInLocal().getWidth() != -1) {
////                        // Please help. I cant think of some approch
////                        // I might kermit suicide...
////                        touchingWalls = true;
////                    }
//                    if(wall.getBoundsInParent().intersects(ball.getBoundsInParent())){
//                        touchingWalls = true;
//                    }
//                    else{
//                        touchingWalls = false;
//                    }
//                }
//            });
//        }
    }

	/**
	 * Adds animation to walls, falling down.
	 */
	private void wallsFall(){
        Path [] pathWalls = new Path[walls.length];
        pathTransitionWalls = new PathTransition[walls.length];

        for(int i=0; i<pathWalls.length; i++){
            if(walls[i] == null)
                continue;

            pathWalls[i] = new Path();

            pathWalls[i].getElements().add(new MoveTo(walls[i].getTranslateX(), walls[i].getTranslateY()));
            pathWalls[i].getElements().add(new LineTo(walls[i].getTranslateX(), gamePaneHeight + (wallHeight+boxHeight+3*boxHeight/2)));
            pathTransitionWalls[i] = new PathTransition();
            pathTransitionWalls[i].setDuration(Duration.millis(TransitionTime));
            pathTransitionWalls[i].setPath(pathWalls[i]);
            pathTransitionWalls[i].setNode(walls[i]);
            pathTransitionWalls[i].play();
        }
    }

	/**
	 * The looping function. Repeats the game loop, and makes the game endless.
	 */
	private void nextCycle(){

        Random rand = new Random();

        wallsAltGone = false;
        ballsAltGone = false;

        // Boxes
        boxes = new StackPane[numberOfBoxes];
        boxesAlternate = new StackPane[numberOfBoxes];
        int alternate = rand.nextInt(3);
        if(alternate < 2){
            this.alternateFallBoxes = true;
        }
        wallsAltComing = alternateFallBoxes;
        createBoxes();

        // Walls
        numberOfWalls = 1 + rand.nextInt(numberOfBoxes);
        walls = new Wall[numberOfWalls];
        createWalls();

		// Coins
		numberOfCoins = 1 + rand.nextInt(numberOfBalls / 2 == 0 ? 1 : numberOfBalls/2);
		coins = new StackPane[numberOfCoins];
		createCoins();

		// Magnet
		int magNow = rand.nextInt(10);
		if(magNow == 0){
			magnet = new StackPane();
			addMagnet();
			magnetFall();
		}

		// Shields
		int shieldNow = rand.nextInt(10);
		if(shieldNow == 0){
			addShield();
			shieldFall();
		}

		// DestroyAllNow
		int destroyAllNow = rand.nextInt(10);
		if(destroyAllNow == 0){
			addDestroyAll();
			destroyAllFall();
		}

		// Balls
		numberOfBalls = 1 + rand.nextInt(numberOfBoxes/2);
		balls = new StackPane[numberOfBalls];
		ballsAlternate = new StackPane[numberOfBalls];
		createBalls();
		boxesFall();
		ballsFall();
		wallsFall();
		coinsFall();

		TransitionTime = 3000;
		TransitionTime -= snake.getLength()/10;
	}

	/**
	 * Method to reset the cheat string.
	 */
	private void resetCheat(){
		cheat = "";
	}

	/**
	 * Starts the main scene.
	 * @param primaryStage Window where to show details
	 * @throws Exception
	 */
    @Override
    public void start(Stage primaryStage) throws Exception{

        main = new Main();
        game = new PlayGame();
        resultWindow = new ResultWindow();
        window = primaryStage;
        Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();

        gameGridPane = new GridPane();
        optionsGridPane = new GridPane();
        HBox hBox = new HBox();
        confirmButton = new Button();
        Choices = new ChoiceBox<>();

        windowWidth = screenSize.getWidth();
        windowHeight = screenSize.getHeight();
        gamePaneWidth = 0.6 * windowWidth - 40;
        gamePaneHeight = windowHeight - 60;
        optionsPaneWidth = 0.4 * windowWidth - 40;
        optionsPaneHeight = windowHeight - 60;

        double centerOfGamePaneHeight = gamePaneHeight/2;
        double centerOfGamePaneWidth = gamePaneWidth/2;

        Scene scene = new Scene(new Group(hBox));
        scene.setFill(Color.DARKGREEN);

        hBox.setTranslateX(20);
        hBox.setTranslateY(20);
        hBox.setSpacing(40);

        boxWidth = Math.floor(gamePaneWidth/numberOfBoxes);

        Choices.setItems(FXCollections.observableArrayList(
                "Restart", "Go Back"));
        Choices.getSelectionModel().selectFirst();
        Choices.setPrefHeight(30);
        Choices.setPrefWidth(200);

        confirmButton.setStyle(IDLE_BUTTON_STYLE);
        confirmButton.setText("Confirm");
        confirmButton.setPrefHeight(30);
        confirmButton.setPrefWidth(200);
        confirmButton.setOnAction(e->{
            if(Choices.getValue().equalsIgnoreCase("RESTART")){
                try {
                    ((Node)(e.getSource())).getScene().getWindow().hide();
                    game.start(primaryStage);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            if(Choices.getValue().equalsIgnoreCase("GO Back")){
                try {
                    ((Node)(e.getSource())).getScene().getWindow().hide();
                    main.start(primaryStage);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        confirmButton.setOnMousePressed(e -> {
            confirmButton.setStyle(PRESSED_BUTTON_STYLE);
        });
        confirmButton.setOnMouseEntered(e -> confirmButton.setStyle(HOVEROVER_BUTTON_STYLE));
        confirmButton.setOnMouseExited(e -> confirmButton.setStyle(IDLE_BUTTON_STYLE));

        SplitPane gamePane = new SplitPane();
        gamePane.setOrientation(Orientation.VERTICAL);
        gamePane.setPrefSize(gamePaneWidth, gamePaneHeight);
        hBox.getChildren().add(gamePane);
        gamePane.getItems().add(gameGridPane);

        gamePane.setStyle("-fx-background-color: #000000");
        if(snekmage == null){
            snake = new Snake(10, gameGridPane, centerOfGamePaneHeight, centerOfGamePaneWidth);
        }
        else{
            snake =  new Snake(10, gameGridPane, centerOfGamePaneHeight, centerOfGamePaneWidth,snekmage);
        }

        if(mouseMove){
			gameGridPane.setOnMouseMoved(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					snakeOldX = snake.getXFirst();
					double moveSnek = event.getSceneX()-20;

					Ball ball = (Ball) snake.getFirst();
					touchingWalls = false;

					for(int i=0; i<walls.length; i++){
						if(walls[i] == null)
							continue;

						if(walls[i].getBoundsInParent().intersects(ball.getBoundsInParent()))
							touchingWalls = true;
					}

					if(!touchingWalls)
						snake.moveSnek(moveSnek);

					touchingWalls = false;
				}
			});
		}

        SplitPane optionsPane = new SplitPane();
        optionsPane.setOrientation(Orientation.VERTICAL);
        optionsPane.setPrefSize(optionsPaneWidth, optionsPaneHeight);
        hBox.getChildren().add(optionsPane);
        optionsPane.getItems().add(optionsGridPane);

        score = new Label();
        score.setText(String.valueOf(0));
        score.setAlignment(Pos.CENTER);

        Label scoreLabel = new Label();
        scoreLabel.setText("SCORE");
        scoreLabel.setFont(Font.font("Roboto", 32));

        optionsGridPane.setVgap(10);
        optionsGridPane.setPadding(new Insets(10, 10, 10, 10));
        optionsGridPane.add(scoreLabel, 3, 0);
        optionsGridPane.add(score, 3, 1);
        optionsGridPane.add(Choices, 3, 3);
        optionsGridPane.add(confirmButton, 3, 5);
        optionsGridPane.setAlignment(Pos.CENTER);
        optionsGridPane.setHalignment(score, HPos.CENTER);
        optionsGridPane.setHalignment(scoreLabel, HPos.CENTER);
        optionsGridPane.setHalignment(Choices, HPos.CENTER);
        optionsGridPane.setHalignment(confirmButton, HPos.CENTER);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
        	double moveOffset = 40;
        	double limitWidth = gameGridPane.getWidth();

        	if(!mouseMove){
				if(key.getCode() == KeyCode.RIGHT){
					snakeOldX = snake.getXFirst();
					double moveSnek = snakeOldX + moveOffset;

					if(moveSnek > 2 && moveSnek < (limitWidth)){
						Ball ball = (Ball) snake.getFirst();
						touchingWalls = false;

						for(int i=0; i<walls.length; i++){
							if(walls[i] == null)
								continue;
							if(walls[i].getBoundsInParent().intersects(ball.getBoundsInParent()))
								touchingWalls = true;
						}

						if(!touchingWalls)
							snake.moveSnek(moveSnek);

						touchingWalls = false;
					}
				}
				if(key.getCode() == KeyCode.LEFT){
					snakeOldX = snake.getXFirst();
					double moveSnek = snakeOldX - moveOffset;

					if(moveSnek > 2 && moveSnek < (limitWidth)) {
						Ball ball = (Ball) snake.getFirst();
						touchingWalls = false;

						for(int i=0; i<walls.length; i++){
							if(walls[i] == null)
								continue;
							if(walls[i].getBoundsInParent().intersects(ball.getBoundsInParent()))
								touchingWalls = true;
						}

						if(!touchingWalls)
							snake.moveSnek(moveSnek);

						touchingWalls = false;
					}
				}
			}
			else{
				cheat += key.getText();
				if(cheat.equalsIgnoreCase("ligma")){
					// Add shield for 10 seconds
					snake.getShield(10000);
					resetCheat();
				}
				if(cheat.equalsIgnoreCase("sugma")){
					// Add 100 points
					int tempScore = Integer.parseInt(score.getText());
					tempScore += 100;
					tempScore += Integer.parseInt(score.getText());
					score.setText(String.valueOf(tempScore));
					resetCheat();
				}
				if(cheat.equalsIgnoreCase("sawcon")){
					// Add 10 bolls
					snake.addBalls(10, gameGridPane);
					resetCheat();
				}
				if(cheat.length() > 6)
					resetCheat();
			}
		});

		Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				resetCheat();
			}
		};
		timer.schedule(timerTask, 0, 6000);

        nextCycle();

        primaryStage.setMinHeight(windowHeight);
        primaryStage.setMinWidth(windowWidth);

        primaryStage.setX(screenSize.getMinX());
        primaryStage.setY(screenSize.getMinY());
        primaryStage.setWidth(screenSize.getWidth());
        primaryStage.setHeight(screenSize.getHeight());

        primaryStage.setScene(scene);
//        primaryStage.setMaximized(true);
        primaryStage.setTitle("Snek");
//        primaryStage.setResizable(false);
        primaryStage.show();
    }


//    public static void main(String[] args) {
//        launch(args);
//    }
}