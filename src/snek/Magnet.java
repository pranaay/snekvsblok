package snek;

import javafx.scene.shape.Circle;
public class Magnet extends Circle {

    private double time;

    public Magnet(){
        super();
        this.time = 0;
    }

    public Magnet(double radius){
        super(radius);
        this.time = 5;
    }

}