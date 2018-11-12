package snek;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableListValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
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
import sun.security.krb5.internal.crypto.Des;

import java.awt.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Random;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

public class PlayGame extends Application{
    private Label score;

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

    private GridPane gameGridPane;
    private GridPane optionsGridPane;

    private int numberOfBoxes = 8;
    private int numberOfBalls;
    private int numberOfCoins;
    private int numberOfWalls;
    private int PlayerScore = 0;

    private double wallHeight = 200;

    private ChoiceBox<String> Choices ;
    private Button confirmButton;
    private Stage window;
    private Main main;
    private ResultWindow resultWindow;
    private PlayGame game;

    private boolean alternateFallBoxes = true;

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
                    gameGridPane.add(boxes[x], x, 0);
                    boxes[x].setTranslateY(-(wallHeight+boxHeight+boxHeight));
                }
                else{
                    int inactiveBlock = rand.nextInt(numberOfBoxes);
                    if(inactiveBlock == x-numberOfBoxes || x-numberOfBoxes == definetlyInactive){
                        continue;
                    }
                    stackPane.setTranslateY(-(wallHeight+boxHeight+boxHeight));
                    boxesAlternate[x-numberOfBoxes] = stackPane;
                    gameGridPane.add(boxesAlternate[x-numberOfBoxes], x-numberOfBoxes, 0);
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
                gameGridPane.add(boxes[x], x, 0);
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
                        ball = (Ball) snake.getFirst().getChildren().get(0);
                        blok = (DestroyBlock) boxes[index].getChildren().get(0);
                    } catch (Exception e){
                        ball = null;
                        blok = null;
                    }

                    if(ball == null || blok == null)
                        return;

                    Shape intersect = Shape.intersect(ball, blok);
                    if (intersect.getBoundsInLocal().getWidth() != -1) {
                        boolean hit = blok.hit(snake.getLength());

                        if(snake.isHasShield()){
                            snake.removeBalls(blok.getBoxValue(),gameGridPane);
                            boxes[index].getChildren().remove(0, 1);
                            addFire(index, blok);
                        }
                        else if(hit && blok.getBoxValue() <= 5){
                            snake.removeBalls(blok.getBoxValue(),gameGridPane);
//                            boxes[index].getChildren().remove(0, 1);
                            addFire(index, blok);
                        }
                        else if(blok.getBoxValue() > 5 && !snake.isHasShield() && hit){
                            pauseBoxes();

                            snake.removeBalls(blok.getBoxValue(), gameGridPane);
                            Text text = (Text) boxes[index].getChildren().get(1);
                            text.setText(String.valueOf(blok.getBoxValue() - 5));
                            blok.reduceValue(5);
                        }
                        else{
                            try {
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
        for(int x = 0; x<boxesAlternate.length; x++){
            final int index = x;

            if(boxesAlternate[x] == null)
                continue;

            boxesAlternate[x].boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
                @Override
                public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
                    Ball ball;
                    DestroyBlock blok;

                    try{
                        ball = (Ball) snake.getFirst().getChildren().get(0);
                        blok = (DestroyBlock) boxesAlternate[index].getChildren().get(0);
                    } catch (Exception e){
                        ball = null;
                        blok = null;
                    }

                    if(ball == null || blok == null)
                        return;

                    Shape intersect = Shape.intersect(ball, blok);
                    if (intersect.getBoundsInLocal().getWidth() != -1) {
                        boolean hit = blok.hit(snake.getLength());

                        if(snake.isHasShield()){
                            snake.removeBalls(blok.getBoxValue(),gameGridPane);
                            boxesAlternate[index].getChildren().remove(0, 1);
                            addFireAlternate(index, blok);
                        }
                        else if(hit && blok.getBoxValue() <= 5){
                            snake.removeBalls(blok.getBoxValue(),gameGridPane);
                            boxesAlternate[index].getChildren().remove(0, 1);
                            addFireAlternate(index, blok);
                        }
                        else if(blok.getBoxValue() > 5 && hit){
                            pauseBoxes();
                            snake.removeBalls(blok.getBoxValue(), gameGridPane);
                            Text text = (Text) boxesAlternate[index].getChildren().get(1);
                            text.setText(String.valueOf(blok.getBoxValue() - 5));
                            blok.reduceValue(5);
                        }
                        else{
                            try {
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

    private void addFire(int index, DestroyBlock blok){
        Timer timer = new Timer();

        try{
            boxes[index].getChildren().remove(1);
            boxes[index].getChildren().remove(0);
        } catch (Exception e){

        }

        DestroyBlock rect = createBlok();
        boxes[index].setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(getClass().getClassLoader().getResource("littt.png").toString())), CornerRadii.EMPTY, Insets.EMPTY)));
        rect.setFill(Color.TRANSPARENT);
//        rect.setStyle("-fx-background-image: url(littt.png)");

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        boxes[index].setBackground(Background.EMPTY);
                    }
                });
            }
        };

        timer.schedule(timerTask, 500);
    }

    private void boxesFall(){
        pathBoxes = new Path[boxes.length];
        pathTransitionBoxes = new PathTransition[boxes.length];

        boolean setNext = false;
        for(int i=0; i<pathBoxes.length; i++){
            if(boxes[i] == null)
                continue;

            pathBoxes[i] = new Path();

            pathBoxes[i].getElements().add(new MoveTo(boxes[i].getTranslateX() + boxWidth/2, boxes[i].getTranslateY()));
            pathBoxes[i].getElements().add(new LineTo(boxes[i].getTranslateX() + boxWidth/2, gamePaneHeight + (wallHeight+boxHeight+boxHeight)));
            pathTransitionBoxes[i] = new PathTransition();
            pathTransitionBoxes[i].setDuration(Duration.millis(3000));
            pathTransitionBoxes[i].setPath(pathBoxes[i]);
            pathTransitionBoxes[i].setNode(boxes[i]);
            pathTransitionBoxes[i].play();
            if(!setNext) {
                setNext = true;
//                pathTransition.setOnFinished(e -> nextCycle());
            }
        }

        if(alternateFallBoxes){
            alternateFallBoxes = false;
            alternatePathBoxes = new Path[boxesAlternate.length];
            pathTransitionBoxesAlternate = new PathTransition[boxesAlternate.length];

            for(int i=0; i<alternatePathBoxes.length; i++){

                if(boxesAlternate[i] == null)
                    continue;

                alternatePathBoxes[i] = new Path();

                alternatePathBoxes[i].getElements().add(new MoveTo(boxesAlternate[i].getTranslateX() + boxWidth/2, boxesAlternate[i].getTranslateY()));
                alternatePathBoxes[i].getElements().add(new LineTo(boxesAlternate[i].getTranslateX() + boxWidth/2, gamePaneHeight + (wallHeight+boxHeight+boxHeight)));
                pathTransitionBoxesAlternate[i] = new PathTransition();
                pathTransitionBoxesAlternate[i].setDuration(Duration.millis(3000));
                pathTransitionBoxesAlternate[i].setPath(alternatePathBoxes[i]);
                pathTransitionBoxesAlternate[i].setNode(boxesAlternate[i]);
                pathTransitionBoxesAlternate[i].setDelay(new Duration(1500));
                pathTransitionBoxesAlternate[i].play();
            }
        }
    }

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

        for(int i=0; i<ballsAlternate.length; i++){
            if(pathTransitionBallsAlternate[i] != null)
                pathTransitionBallsAlternate[i].pause();
        }

        for(int i=0; i<walls.length; i++){
            if(pathTransitionWalls[i] != null)
                pathTransitionWalls[i].pause();
        }

        if(pathTransitionMagnet != null)
            pathTransitionMagnet.pause();

        if(pathTransitionShield != null)
            pathTransitionShield.pause();

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
            }
        };
        timer.schedule(task, 1000);
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
                int temp  = 1+ rand.nextInt(10);
                StackPane ball = new StackPane();
                Ball rawBall = new Ball(15,temp);
                rawBall.setFill(Color.PINK);
                Text text = new Text(Integer.toString(temp));
                ball.getChildren().addAll(rawBall, text);
                gameGridPane.add(ball, x, 0);
                balls[i] = ball;

                balls[i].setTranslateY((-wallHeight+boxHeight+boxHeight));
            }
            else{
                int temp  = 1+ rand.nextInt(10);
                StackPane ball = new StackPane();
                Ball rawBall = new Ball(15,temp);
                rawBall.setFill(Color.PINK);
                Text text = new Text(Integer.toString(temp));
                ball.getChildren().addAll(rawBall, text);

                gameGridPane.add(ball, x, 0);
                ballsAlternate[i-numberOfBalls] = ball;

                ballsAlternate[i-numberOfBalls].setTranslateY(-(wallHeight+boxHeight+boxHeight));
            }
        }

        for(int x = 0; x<balls.length; x++){
            final int index = x;

            if(balls[x] == null)
                continue;

            balls[x].boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
                @Override
                public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
//                            System.out.println(newValue);
                    Ball ball;
                    Ball boll;
                    try{
                        ball = (Ball) snake.getFirst().getChildren().get(0);
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
                        balls[index].getChildren().remove(0, 1);
                    }
                }
            });
        }
        for(int x = 0; x<ballsAlternate.length; x++){
            final int index = x;

            if(ballsAlternate[x] == null)
                continue;

            ballsAlternate[x].boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
                @Override
                public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
//                            System.out.println(newValue);
                    Ball ball;
                    Ball boll;

                    try{
                        ball = (Ball) snake.getFirst().getChildren().get(0);
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
                        //if(boll.hit()){
                        snake.addBalls(boll.getValue(),gameGridPane);

                        //ballsAlternate[index].setStyle("-fx-background-color: #000000");
                        ballsAlternate[index].getChildren().remove(0, 1);

                    }
                }
            });
        }
    }

    private void ballsFall(){
        Path [] pathBalls = new Path[balls.length];
        Path [] pathBallsAlternate = new Path[balls.length];
        pathTransitionBalls = new PathTransition[balls.length];

        Random rand = new Random();

        for(int i=0; i<pathBalls.length; i++) {

            if (boxes[i] == null)
                continue;
            pathBalls[i] = new Path();

            pathBalls[i].getElements().add(new MoveTo(balls[i].getTranslateX() + boxWidth / 2, balls[i].getTranslateY()));
            pathBalls[i].getElements().add(new LineTo(balls[i].getTranslateX() + boxWidth / 2, gamePaneHeight + (wallHeight+boxHeight+boxHeight)));
            pathTransitionBalls[i] = new PathTransition();
            pathTransitionBalls[i].setDuration(Duration.millis(3000));
            pathTransitionBalls[i].setPath(pathBalls[i]);
            pathTransitionBalls[i].setNode(balls[i]);
            pathTransitionBalls[i].setDelay(new Duration(500 + rand.nextInt(500)));
            pathTransitionBalls[i].play();
        }

        boolean setOnFinished = false;
        for(int i=0; i<ballsAlternate.length; i++){
            pathBallsAlternate[i] = new Path();
            pathTransitionBallsAlternate = new PathTransition[ballsAlternate.length];

            pathBallsAlternate[i].getElements().add(new MoveTo(ballsAlternate[i].getTranslateX() + boxWidth/2, ballsAlternate[i].getTranslateY()));
            pathBallsAlternate[i].getElements().add(new LineTo(ballsAlternate[i].getTranslateX() + boxWidth/2, gamePaneHeight + (wallHeight+boxHeight+boxHeight)));
            pathTransitionBallsAlternate[i] = new PathTransition();
            pathTransitionBallsAlternate[i].setDuration(Duration.millis(3000));
            pathTransitionBallsAlternate[i].setPath(pathBallsAlternate[i]);
            pathTransitionBallsAlternate[i].setNode(ballsAlternate[i]);
            pathTransitionBallsAlternate[i].setDelay(new Duration(2000));
            if(!setOnFinished) {
                setOnFinished = true;
                pathTransitionBallsAlternate[i].setOnFinished(event -> nextCycle());
            }
            pathTransitionBallsAlternate[i].play();
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
                        ball = (Ball) snake.getFirst().getChildren().get(0);
                        boll = (Coin) coins[index].getChildren().get(0);
                    } catch (Exception e){
                        ball = null;
                        boll = null;
                    }

                    if(ball == null || boll == null)
                        return;

                    Shape intersect = Shape.intersect(ball, boll);

                    if (intersect.getBoundsInLocal().getWidth() != -1) {
                        PlayerScore += 1;
                        score.setText(String.valueOf(PlayerScore));
                        coins[index].getChildren().remove(0);
                    }
                }
            });
        }
    }

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
            pathTransitionCoins[i].setDelay(new Duration(200 + rand.nextInt(500)));
            pathTransitionCoins[i].play();
        }
    }

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
                    ball = (Ball) snake.getFirst().getChildren().get(0);
                    poly = (Polygon) shield.getChildren().get(0);
                } catch (Exception e){
                    ball = null;
                    poly = null;
                }

                if(ball == null || poly == null)
                    return;

                Shape intersect = Shape.intersect(ball, poly);
                if (intersect.getBoundsInLocal().getWidth() != -1) {
                    System.out.println("LALAL");
                    snake.getShield();
                    shield.getChildren().remove(0);
                }
            }
        });
    }

    private void shieldFall(){
        Path shieldPath = new Path();
        pathTransitionShield = new PathTransition();
        Random rand = new Random();

        shieldPath.getElements().add(new MoveTo(shield.getTranslateX() + boxWidth/2, shield.getTranslateY()));
        shieldPath.getElements().add(new LineTo(shield.getTranslateX() + boxWidth/2, gamePaneHeight + (wallHeight+boxHeight+boxHeight)));

        pathTransitionShield.setDuration(Duration.millis(3000));
        pathTransitionShield.setPath(shieldPath);
        pathTransitionShield.setNode(shield);
        pathTransitionShield.setDelay(new Duration(700 + rand.nextInt(600)));
        pathTransitionShield.play();
    }

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
                    ball = (Ball) snake.getFirst().getChildren().get(0);
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

    private void magnetFall(){
        Path magPath = new Path();
        pathTransitionMagnet = new PathTransition();
        Random rand = new Random();

        magPath.getElements().add(new MoveTo(magnet.getTranslateX() + boxWidth/2, magnet.getTranslateY()));
        magPath.getElements().add(new LineTo(magnet.getTranslateX() + boxWidth/2, gamePaneHeight + (wallHeight+boxHeight+boxHeight)));

        pathTransitionMagnet.setDuration(Duration.millis(3000));
        pathTransitionMagnet.setPath(magPath);
        pathTransitionMagnet.setNode(magnet);
        pathTransitionMagnet.setDelay(new Duration(700 + rand.nextInt(600)));
        pathTransitionMagnet.play();
    }

    private void createWalls(){
        Random rand = new Random();
        for (int x = 0; x < numberOfWalls; x++) {
            int needAWall = rand.nextInt(10);
            if(needAWall < 4)
                continue;

            int height = 100;
            Wall wall = new Wall(wallHeight);
            wall.setFill(Color.WHITE);

            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(wall);
//            stackPane.setTranslateY(-height);
//            stackPane.setTranslateX(-boxWidth/2);
            walls[x] = stackPane;
            gameGridPane.add(walls[x], x, 1);
            walls[x].setTranslateY(-(wallHeight+boxHeight+boxHeight/2));
        }

        for(int i=0; i<numberOfWalls; i++){
            if(walls[i] == null)
                continue;

            final int index = i;

            walls[i].boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
                @Override
                public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
                    Ball ball;
                    Wall wall;
                    try{
                        ball = (Ball) snake.getFirst().getChildren().get(0);
                        wall = (Wall) walls[index].getChildren().get(0);
                    } catch (Exception e){
                        ball = null;
                        wall = null;
                    }

                    if(ball == null || wall == null)
                        return;

                    Shape intersect = Shape.intersect(ball, wall);
                    if (intersect.getBoundsInLocal().getWidth() != -1) {
                        int Xlast = (int) snake.getXLast();
                        int X = (int) snake.getXFirst();
                        int Y = (int) snake.getYFirst();

                        if(oldValue.getMinX() < Xlast){
                            X -= 40;
                        }
                        else{
                            X += 100;
                        }
                        moveCursor(X, Y);
                    }

                }
            });
        }
    }

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
            pathTransitionWalls[i].setDuration(Duration.millis(3000));
            pathTransitionWalls[i].setPath(pathWalls[i]);
            pathTransitionWalls[i].setNode(walls[i]);
            pathTransitionWalls[i].play();
        }
    }

    public void moveCursor(int screenX, int screenY) {
        Platform.runLater(() -> {
            try {
                Robot robot = new Robot();
                robot.mouseMove(screenX, screenY);
            } catch (AWTException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
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
        createWalls();
        wallsFall();

        // Balls
        numberOfBalls = 1 + rand.nextInt(numberOfBoxes/2);
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
    }

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
        scene.setFill(Color.DARKGREY);

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

        snake = new Snake(10, gameGridPane, centerOfGamePaneHeight, centerOfGamePaneWidth);

        gameGridPane.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                snake.moveSnek(event.getSceneX() - 20);
            }
        });

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