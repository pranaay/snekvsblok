package snek;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class DestroyBlock extends Rectangle {

    private int boxValue;
    private boolean isActive = true;

    public DestroyBlock(){
        super();
        Random rand = new Random();
        this.boxValue = 1 + rand.nextInt(10);
    }

    public DestroyBlock(double width, double height){
        super(width, height);
        this.setArcHeight(10);
        this.setArcWidth(10);
        Random rand = new Random();
        this.boxValue = 1 + rand.nextInt(10);
    }

    public DestroyBlock(double xPos, double yPos, double width, double height){
        super(xPos, yPos, width, height);
        this.setArcHeight(10);
        this.setArcWidth(10);
        Random rand = new Random();
        this.boxValue = 1 + rand.nextInt(10);
    }

    public int getBoxValue() {
        return boxValue;
    }

    public boolean hit(int sneklen){
        if(this.boxValue < sneklen)
            return true;
        else
            return false;
    }

    public void reduceValue(int n){
        this.boxValue -= n;
    }

    public void setInactive(){
        isActive = false;
    }

    public boolean isActive(){
    	return isActive;
	}

}
