package snek;

import javafx.scene.shape.Rectangle;

import java.util.Random;

/**
 * Contains definitions for Bloks.
 */
public class DestroyBlock extends Rectangle {

	private int boxValue;
    private boolean isActive = true;

	/**
	 * A simple constructor to create an empty object.
	 */
	public DestroyBlock(){
        super();
        Random rand = new Random();
        this.boxValue = 1 + rand.nextInt(10);
    }

	/**
	 * A constructor to create a blok of specified width and height.
	 * @param width Width of the box
	 * @param height Height of the box
	 */
	public DestroyBlock(double width, double height){
        super(width, height);
        this.setArcHeight(10);
        this.setArcWidth(10);
        Random rand = new Random();
        this.boxValue = 1 + rand.nextInt(10);
    }

	/**
	 * A constructor to create a blok
	 * @param xPos X co-ordinate of blok
	 * @param yPos Y co-ordinate of blok
	 * @param width Width of the box
	 * @param height Height of the box
	 */
    public DestroyBlock(double xPos, double yPos, double width, double height){
        super(xPos, yPos, width, height);
        this.setArcHeight(10);
        this.setArcWidth(10);
        Random rand = new Random();
        this.boxValue = 1 + rand.nextInt(10);
    }

	/**
	 * Returns the value of the box.
	 * @return Integer
	 */
	public int getBoxValue() {
        return boxValue;
    }

	/**
	 * method to reduce the value of blok. Returns true, when blok value is less than snake's length.
	 * @param sneklen Integer Length of snake
	 * @return boolean
	 */
	public boolean hit(int sneklen){
        if(this.boxValue < sneklen)
            return true;
        else
            return false;
    }

	/**
	 * Decrease value of blok by n.
	 * @param n Factor by which to reduce the value of blok.
	 */
	public void reduceValue(int n){
        this.boxValue -= n;
    }

	/**
	 * Set the block as inactive.
	 */
	public void setInactive(){
        isActive = false;
    }

	/**
	 * Checks whether a blok is active or not.
	 * @return boolean
	 */
	public boolean isActive(){
    	return isActive;
	}
}
