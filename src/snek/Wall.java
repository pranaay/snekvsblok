package snek;

import javafx.scene.shape.Rectangle;

/**
 * Contains definitions for Walls.
 */
public class Wall extends Rectangle {

    /**
     * A simple constructor to create an empty object.
     */
    public Wall(){
        super();
    }

	/**
	 * A simple constructor to create an Wall object of given height and width.
	 * @param height Height of wall
	 */
	public Wall(double height){
        super(5, height);
    }

}