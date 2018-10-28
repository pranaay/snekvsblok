package Snek;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Random;

public class Main extends Application{

    private double windowWidth;
    private double windowHeight;
    private double boxWidth = 80;
    private double boxHeight = 50;

    private StackPane[] boxes;
    private StackPane[] boxesAlternate;
    private GridPane gridPaneBoard;
    private int numberOfBoxes;
    private boolean alternateFall = true;

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

        if(this.alternateFall){

            for (int x = 0; x < 2*numberOfBoxes; x++) {
                DestroyBlock rect = createBlok();

                int inactiveBlock = rand.nextInt(25);
                if(inactiveBlock == 0){
                    rect.setFill(Color.BLACK);
                    rect.setInactive();
                }

                Text text =  new Text(Integer.toString(rect.getBoxValue()));
                text.setStyle("-fx-font-size: 18px;style: \"-fx-font-weight: bold\";");
                text.setY(-boxHeight);

                StackPane stackPane = new StackPane();
                stackPane.getChildren().addAll(rect, text);
                if(x < numberOfBoxes){
                    boxes[x] = stackPane;
                    boxes[x].setTranslateY(-boxHeight);
//                    boxes[x].setLayoutY(-boxHeight);
                    gridPaneBoard.add(boxes[x], x, 0);
                }
                else{
                    boxesAlternate[x-numberOfBoxes] = stackPane;
                    boxesAlternate[x-numberOfBoxes].setTranslateY(-boxHeight);
//                    boxesAlternate[x-numberOfBoxes].setLayoutY(-boxHeight);
                    gridPaneBoard.add(boxesAlternate[x-numberOfBoxes], x-numberOfBoxes, 0);
                }
            }
        }
        else {
            for (int x = 0; x < numberOfBoxes; x++) {
                DestroyBlock rect = createBlok();

                int inactiveBlock = rand.nextInt(25);
                if(inactiveBlock == 0){
                    rect.setFill(Color.BLACK);
                    rect.setInactive();
                }

                Text text =  new Text(Integer.toString(rect.getBoxValue()));
                text.setStyle("-fx-font-size: 18px;style: \"-fx-font-weight: bold\";");
                text.setY(-boxHeight);

                StackPane stackPane = new StackPane();
                stackPane.getChildren().addAll(rect, text);
                boxes[x] = stackPane;
                boxes[x].setTranslateY(-boxHeight);
//                boxes[x].setLayoutY(-boxHeight);
                gridPaneBoard.add(boxes[x], x, 0);
            }
        }

    }

    private void createAnimation(){
        Path [] pathBoxes = new Path[boxes.length];
        for(int i=0; i<pathBoxes.length; i++){
            pathBoxes[i] = new Path();

            pathBoxes[i].getElements().add(new MoveTo(boxes[i].getLayoutX() + boxWidth/2, boxes[i].getLayoutY() - boxHeight));
            System.out.println(boxes[i].getLayoutY() - boxHeight);
            pathBoxes[i].getElements().add(new LineTo(boxes[i].getLayoutX() + boxWidth/2, windowHeight));
            PathTransition pathTransition = new PathTransition();
            pathTransition.setDuration(Duration.millis(3000));
            pathTransition.setPath(pathBoxes[i]);
            pathTransition.setNode(boxes[i]);
            pathTransition.play();
            if(i == 5)
                pathTransition.setOnFinished(e -> nextCycle());
        }
        if(alternateFall){
            alternateFall = false;
            Path [] pathBoxesAlternate = new Path[boxesAlternate.length];

            for(int i=0; i<pathBoxesAlternate.length; i++){
                pathBoxesAlternate[i] = new Path();

                pathBoxesAlternate[i].getElements().add(new MoveTo(boxesAlternate[i].getLayoutX() + boxWidth/2, boxes[i].getLayoutY() - boxHeight));
                pathBoxesAlternate[i].getElements().add(new LineTo(boxesAlternate[i].getLayoutX() + boxWidth/2, windowHeight));
                PathTransition pathTransition = new PathTransition();
                pathTransition.setDuration(Duration.millis(3000));
                pathTransition.setPath(pathBoxesAlternate[i]);
                pathTransition.setNode(boxesAlternate[i]);
                pathTransition.setDelay(new Duration(1500));
                pathTransition.play();
            }
        }
    }

    private void boxesFall(){
        createAnimation();
    }

    private void nextCycle(){
        boxes = new StackPane[numberOfBoxes];
        boxesAlternate = new StackPane[numberOfBoxes];
        Random rand = new Random();
        int alternate = rand.nextInt(5);
        if(alternate == 0){
            this.alternateFall = true;
        }
        createBoxes();
        boxesFall();
    }

    @Override
    public void start(Stage primaryStage) {

        gridPaneBoard = new GridPane();
        primaryStage.setResizable(false );
        Scene scene = new Scene(gridPaneBoard);
        primaryStage.show();
        primaryStage.setMinHeight(windowHeight);
        primaryStage.setMinWidth(windowWidth);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        scene.setFill(Color.BLACK);
        primaryStage.setTitle("Snek");

        windowWidth = primaryStage.getWidth();
        windowHeight = primaryStage.getHeight();
        numberOfBoxes = (int) Math.floor(windowWidth/boxWidth);

        nextCycle();
//        gridPaneBoard.getChildren().add(new Ball(10));
//        StackPane ball = new StackPane();
//        Ball temp = new Ball(10);
//        temp.setFill(Color.PINK);
//        Text text = new Text("5");
//        ball.getChildren().addAll(temp, text);
//        ball.setTranslateX(100);
//        ball.setTranslateY(100);
//        gridPaneBoard.getChildren().add(ball);

    }


    public static void main(String[] args) {
        launch(args);
    }
}