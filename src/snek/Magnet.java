package snek;

import javafx.scene.shape.Circle;

/**
 * Contains definitions for Magnet.
 */
public class Magnet extends Circle {

    /**
     * A simple constructor to create an empty object.
     */
    public Magnet(){
        super();
    }

	/**
	 * A simple constructor to create an object, of specific radius.
	 * @param radius Radius of Magnet
	 */
	public Magnet(double radius){
        super(radius);
    }

}