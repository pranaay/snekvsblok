package snek;

import javafx.scene.shape.Circle;

import java.util.Random;

public class Ball extends Circle {

    private int Value;

    public int getValue() {
        return Value;
    }

    public Ball() {
        super();
        Random rand = new Random();
        this.Value = 1 + rand.nextInt(10);
    }

    public Ball(double radius) {
        super(radius);
        //Random rand = new Random();
        //this.Value = 1 + rand.nextInt(10);
    }
    public Ball(double radius,int vv){
        super(radius);
        this.Value = vv;
    }
}