package Snek;

import javafx.scene.shape.Circle;

public class Coin extends Circle {

    private int Points;

    public Coin(){
        super();
        Points = 0;
    }

    public Coin(double radius){
        super(radius);
        Points = 10;
    }

}
