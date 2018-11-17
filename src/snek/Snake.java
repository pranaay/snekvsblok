package snek;


import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
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

    private ArrayList<Ball> snek;
    private ArrayList<Double> Xs, Ys;

    private double scoreY;

    private Text score;

    private Image snakeSkin;

    private String imageName ;

    private int length;

    private boolean hasShield = false;

    public Snake(){
        this.snek = new ArrayList<Ball>();
        this.Xs = new ArrayList<Double>();
        this.Ys = new ArrayList<Double>();
        this.length = 0;
    }

    public Snake(int n){
        this.snek = new ArrayList<Ball>(n);
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
        this.snek = new ArrayList<Ball>();
        score = new Text();
        this.Xs = new ArrayList<Double>(n);
        this.Ys = new ArrayList<Double>(n);

        this.length = n;
        score.setText(String.valueOf(length));
        score.setFill(Color.WHITE);

        double radius = 15;

        Ball firstBall = new Ball(radius);
        firstBall.setFill(getColor());
        firstBall.setTranslateX(centerX);
        firstBall.setTranslateY(centerY);

        scoreY = centerY - 2*radius;
        score.setTranslateX(centerX);
        score.setTranslateY(scoreY);

        snek.add(firstBall);
        gridPane.getChildren().addAll(firstBall, score);

        Xs.add(centerX);
        Ys.add(centerY);

        double X = centerX;
        double Y = centerY + 1.75*radius;

        for(int i=1; i<n; i++){
            Ball otherBalls = new Ball(radius);

            otherBalls.setFill(getColor());

//            ball.getChildren().add(otherBalls);
            otherBalls.setTranslateX(X);
            otherBalls.setTranslateY(Y);

            Xs.add(X);
            Ys.add(Y);
            Y += 2*radius;
            snek.add(otherBalls);
            gridPane.getChildren().add(otherBalls);
        }
    }

    public Snake(int n, GridPane gridPane, double centerX, double centerY,Image ii){
        this.snek = new ArrayList<Ball>();
        score = new Text();
        this.Xs = new ArrayList<Double>(n);
        this.Ys = new ArrayList<Double>(n);

        this.length = n;
        score.setText(String.valueOf(length));
        score.setFill(Color.WHITE);

        double radius = 15;
        snakeSkin = ii;

        Ball firstBall = new Ball(radius);
        firstBall.setFill(new ImagePattern(ii));
        firstBall.setTranslateX(centerX);
        firstBall.setTranslateY(centerY);

        scoreY = centerY - 1.5*radius;
        score.setTranslateX(centerX);
        score.setTranslateY(scoreY);

        snek.add(firstBall);
        gridPane.getChildren().addAll(firstBall, score);

        Xs.add(centerX);
        Ys.add(centerY);

        double X = centerX;
        double Y = centerY + 1.75*radius;

        for(int i=1; i<n; i++){
            Ball otherBalls = new Ball(radius);

            otherBalls.setFill(new ImagePattern(ii));

            otherBalls.setTranslateX(X);
            otherBalls.setTranslateY(Y);

            Xs.add(X);
            Ys.add(Y);
            Y += 2*radius;

            snek.add(otherBalls);
            gridPane.getChildren().add(otherBalls);
        }
    }

    public void addBalls(int n,GridPane gridPane){
        //this guys got some balls
        double radius = 15;

        Double X = Xs.get(this.length-1);
        Double Y = Ys.get(this.length-1);
        Y += 2*radius;

        this.length += n;
        score.setText(String.valueOf(length));

        for(int i=0; i<n; i++){
            Ball otherBalls = new Ball(radius);
            if(snakeSkin == null){
                otherBalls.setFill(getColor());
            }
            else{
                otherBalls.setFill(new ImagePattern(snakeSkin));
            }

            Xs.add(X);
            Ys.add(Y);
            Y += 2*radius;
            snek.add(otherBalls);
            gridPane.getChildren().add(otherBalls);
            otherBalls.setTranslateX(X);
            otherBalls.setTranslateY(Y);
        }
    }

    public void removeBalls(int n,GridPane gridPane){
        if(!hasShield) {
            for (int i = length - 1; i >= length - n; i--) {
                Ball ball = snek.get(i);

                gridPane.getChildren().remove(ball);
                Xs.remove(i);
                Ys.remove(i);
                snek.remove(i);
            }

            length -= n;
            score.setText(String.valueOf(length));
        }
    }

    public void moveSnek(double x){

        Path[] snekPath = new Path[length];

        for(int i=0; i<length; i++){

            if(x == Xs.get(i))
                continue;

            if(i == 0){
                Path scorePath = new Path();
                scorePath.getElements().add(new MoveTo(Xs.get(i) + 15, scoreY-4));
                scorePath.getElements().add(new LineTo(x + 15, scoreY-4));
                PathTransition pathTransition = new PathTransition();
                pathTransition.setDuration(Duration.millis(10));
                pathTransition.setPath(scorePath);
                pathTransition.setNode(score);
                pathTransition.setDelay(new Duration(25*i));
                pathTransition.play();
            }

            snekPath[i] = new Path();

            snekPath[i].getElements().add(new MoveTo(Xs.get(i), Ys.get(i)));
            snekPath[i].getElements().add(new LineTo(x, Ys.get(i)));
            PathTransition pathTransition = new PathTransition();
            pathTransition.setDuration(Duration.millis(10));
            pathTransition.setPath(snekPath[i]);
            pathTransition.setNode(snek.get(i));
            pathTransition.setDelay(new Duration(25*i));
            pathTransition.play();

            Xs.set(i, x);
        }
    }

    public Ball getFirst(){
        return this.snek.get(0);
    }

    public boolean isHasShield() {
        return hasShield;
    }

    public double getXFirst(){
        return Xs.get(0);
    }

    public double getXLast(){
        return Xs.get(Xs.size()-1);
    }

    public double getYFirst(){
        return Ys.get(0);
    }

    public Image getSnakeSkin(){
        return snakeSkin;
    }

    public void setSnakeSkin(Image i){
        this.snakeSkin = i ;
    }

    public int getLength() {
        return length;
    }

    public ArrayList<Double> getXs(){
        return Xs;
    }

    public ArrayList<Double> getYs(){
        return Ys;
    }
}
