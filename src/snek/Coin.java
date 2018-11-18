package snek;

import javafx.scene.shape.Circle;

/**
 * Contains definitions for Coins.
 */
public class Coin extends Circle {

    private int Points;

	/**
	 * A simple constructor to create an empty object.
	 */
	public Coin(){
        super();
        Points = 0;
    }

	/**
	 * A simple constructor to create a Coin object, of specific radius.
	 * @param radius Radius of the coin.
	 */
	public Coin(double radius){
        super(radius);
        Points = 10;
    }

}