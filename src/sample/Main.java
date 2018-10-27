package sample;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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
    private GridPane gridPaneBoard;
    private int numberOfBoxes;

    private void createBoxes(){
        for (int x = 0; x < numberOfBoxes; x++) {
            DestroyBlock rect = new DestroyBlock(0, -boxHeight/2, boxWidth, boxHeight);

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

            Text text =  new Text(Integer.toString(rect.getBoxValue()));
            text.setStyle("-fx-font-size: 18px;style: \"-fx-font-weight: bold\";");

            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(rect, text);
            boxes[x] = stackPane;

            gridPaneBoard.add(boxes[x], x, 1);
        }

    }

    private void nextCycle(){
        boxes = new StackPane[numberOfBoxes];
        createBoxes();
        boxesFall();
    }

    private void boxesFall(){

        Path [] pathBoxes = new Path[boxes.length];

        for(int i=0; i<pathBoxes.length; i++){
            pathBoxes[i] = new Path();

            pathBoxes[i].getElements().add(new MoveTo(boxes[i].getLayoutX() + boxWidth/2, boxes[i].getLayoutY() - boxHeight/2));
            pathBoxes[i].getElements().add(new LineTo(boxes[i].getLayoutX() + boxWidth/2, windowHeight));
            PathTransition pathTransition = new PathTransition();
            pathTransition.setDuration(Duration.millis(3000));
            pathTransition.setPath(pathBoxes[i]);
            pathTransition.setNode(boxes[i]);
            pathTransition.play();
            if(i == 5)
                pathTransition.setOnFinished(e -> nextCycle());
        }
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
    }

    public static void main(String[] args) {
        launch(args);
    }
}