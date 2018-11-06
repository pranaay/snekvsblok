package snek;


import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class Snake extends Region {

    private ArrayList<StackPane> snek;
    int length;

    public Snake(){
        this.snek = new ArrayList<StackPane>();
        this.length = 0;
    }

    public Snake(int n){
        this.snek = new ArrayList<StackPane>(n);
        this.length = n;
    }

    private Color getColor(){
        Random random = new Random();
        int n = random.nextInt(10);
        Color color;

        switch (n){
            case 0: color = Color.RED;
                break;
            case 1: color = Color.PURPLE;
                break;
            case 2: color = Color.YELLOW;
                break;
            case 3: color = Color.TOMATO;
                break;
            case 4: color = Color.NAVY;
                break;
            case 5: color = Color.BLUEVIOLET;
                break;
            case 6: color = Color.IVORY;
                break;
            case 7: color = Color.SEAGREEN;
                break;
            case 8: color = Color.MAROON;
                break;
            default: color = Color.PALETURQUOISE;
                break;
        }

        return color;
    }

    public Snake(int n, GridPane gridPane, double centerX, double centerY){
        this.snek = new ArrayList<StackPane>();
        this.length = n;

        double radius = 10;

        Ball tempBall = new Ball(radius);
        tempBall.setFill(getColor());
        Text text = new Text();
        text.setText(Integer.toString(n));
        StackPane firstBall = new StackPane();
        firstBall.getChildren().addAll(tempBall, text);
        firstBall.setTranslateX(centerX);
        firstBall.setTranslateY(centerY);

        double X = centerX;
        double Y = centerY + 2*radius;

        snek.add(firstBall);

        gridPane.getChildren().add(firstBall);

        for(int i=1; i<n; i++){
            Ball otherBalls = new Ball(radius);
            otherBalls.setFill(getColor());
            StackPane ball = new StackPane();
            ball.getChildren().add(otherBalls);
            ball.setTranslateX(X);
            ball.setTranslateY(Y);
            Y += 2*radius;

            gridPane.getChildren().add(ball);
        }

    }

}
