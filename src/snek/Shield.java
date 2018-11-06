package snek;

import javafx.scene.shape.Polygon;

public class Shield extends Polygon {

    private double time;

    public Shield(){
        super(0.0, 0.0, 20.0, 0.0, 20.0, 5.0, 10.0, 20.0, 0.0, 5.0);
        this.time = 5;
    }

    public Shield(int x, int y){
        super(x, y);
        this.time = 5;
    }

}
