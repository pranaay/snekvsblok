package snek;

import javafx.scene.shape.Polygon;

/**
 * Contains definitions for Shield.
 */
public class Shield extends Polygon {

    private double time;

	/**
	 * A simple constructor to create an object.
	 */
	public Shield(){
        super(0.0, 0.0, 20.0, 0.0, 20.0, 5.0, 10.0, 20.0, 0.0, 5.0);
        this.time = 5;
    }

	/**
	 * A simple constructor to create an object, at specified X, Y co-ordinates.
	 * @param x X co-ordinate
	 * @param y Y co-ordinate
	 */
	public Shield(int x, int y){
        super(x, y);
        this.time = 5;
    }

}
