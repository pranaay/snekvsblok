package snek;


import javafx.animation.PathTransition;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import sun.security.util.Length;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Snake extends Region {

    private ArrayList<StackPane> snek;
    private ArrayList<Double> Xs, Ys;

    private int length;

    private boolean hasShield = false;

    public int getLength() {

        return length;
    }

    public ArrayList<Double> getXs(){
        return Xs;
    }

    public ArrayList<Double> getYs(){
        return Ys;
    }

    public Snake(){
        this.snek = new ArrayList<StackPane>();
        this.Xs = new ArrayList<Double>();
        this.Ys = new ArrayList<Double>();
        this.length = 0;
    }

    public Snake(int n){
        this.snek = new ArrayList<StackPane>(n);
        this.Xs = new ArrayList<Double>(n);
        this.Ys = new ArrayList<Double>(n);
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

    public void getShield(){
        Timer timer = new Timer();
        this.hasShield = true;

        TimerTask task = new TimerTask()
        {
            public void run()
            {
                hasShield = false;
            }

        };

        timer.schedule(task, 5000);
    }


    public Snake(int n, GridPane gridPane, double centerX, double centerY){
        this.snek = new ArrayList<StackPane>();
        this.Xs = new ArrayList<Double>(n);
        this.Ys = new ArrayList<Double>(n);

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

        snek.add(firstBall);
        gridPane.getChildren().add(firstBall);

        Xs.add(centerX);
        Ys.add(centerY);

        double X = centerX;
        double Y = centerY + 2*radius;

        for(int i=1; i<n; i++){
            Ball otherBalls = new Ball(radius);
            otherBalls.setFill(getColor());
            StackPane ball = new StackPane();
            ball.getChildren().add(otherBalls);
            ball.setTranslateX(X);
            ball.setTranslateY(Y);

            Xs.add(X);
            Ys.add(Y);
            Y += 2*radius;
            ball.setId(Integer.toString(i));
            snek.add(ball);
            gridPane.getChildren().add(ball);
        }
    }
    public void addBalls(int n,GridPane gridPane){
        //this guys got some balls
        double radius = 10;

        Double X = Xs.get(this.length-1);
        Double Y = Ys.get(this.length-1);
        Y += 2*radius;

        this.length += n;
        StackPane first = getFirst();
        Text text = (Text) first.getChildren().get(1);
        text.setText(String.valueOf(length));

        for(int i=0; i<n; i++){
            Ball otherBalls = new Ball(radius);
            otherBalls.setFill(getColor());
            StackPane ball = new StackPane();
            ball.getChildren().add(otherBalls);

            Xs.add(X);
            Ys.add(Y);
            Y += 2*radius;
            ball.setId(Integer.toString(length+n));
            snek.add(ball);
            gridPane.getChildren().add(ball);
            ball.setTranslateX(X);
            ball.setTranslateY(Y);
        }

    }

    public void removeBalls(int n,GridPane gridPane){
        if(!hasShield) {
            for (int i = length - 1; i >= length - n; i--) {
                //gridPane.getChildren().indexOf(snek);
                StackPane ball = snek.get(i);
                ball.getChildren().remove(0);

                gridPane.getChildren().remove(ball);
                Xs.remove(i);
                Ys.remove(i);
                snek.remove(i);
            }

            length -= n;

            StackPane first = getFirst();
            Text text = (Text) first.getChildren().get(1);
            text.setText(String.valueOf(length));
        }
    }
    public void moveSnek(double x){

        Path[] snekPath = new Path[length];

        for(int i=0; i<length; i++){

            if(x == Xs.get(i))
                continue;

            snekPath[i] = new Path();

            snekPath[i].getElements().add(new MoveTo(Xs.get(i), Ys.get(i)));
            snekPath[i].getElements().add(new LineTo(x, Ys.get(i)));
            PathTransition pathTransition = new PathTransition();
            pathTransition.setDuration(Duration.millis(10));
            pathTransition.setPath(snekPath[i]);
            pathTransition.setNode(snek.get(i));
            pathTransition.setDelay(new Duration(50*i));
            pathTransition.play();

            Xs.set(i, x);
        }
    }

    public StackPane getFirst(){
        return this.snek.get(0);
    }

    public boolean isHasShield() {
        return hasShield;
    }
}
