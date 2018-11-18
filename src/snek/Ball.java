package snek;

import javafx.scene.shape.Circle;

import java.util.Random;

/**
 * Contains definitions for Balls.
 */
public class Ball extends Circle {

    private int Value;

	/**
	 * Returns the value of a ball.
	 * @return Value of the ball
	 */
	public int getValue() {
        return Value;
    }

	/**
	 * A simple constructor, to create a default Ball
	 */
	public Ball() {
        super();
        Random rand = new Random();
        this.Value = 1 + rand.nextInt(10);
    }

	/**
	 * A simple constructor, to create a default Ball, of given radius.
	 * @param radius
	 */
	public Ball(double radius) {
        super(radius);
    }

	/**
	 * A constructor to create a bol of specific radius and value.
	 * @param radius
	 * @param vv
	 */
	public Ball(double radius,int vv){
        super(radius);
        this.Value = vv;
    }
}