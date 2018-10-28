package Snek;

import javafx.scene.shape.Rectangle;

import java.util.Random;

public class DestroyBlock extends Rectangle {

    private int boxValue;
    private boolean active;

    public DestroyBlock(){
        super();
        active = true;
        Random rand = new Random();
        this.boxValue = 1 + rand.nextInt(10);
    }

    public DestroyBlock(double width, double height){
        super(width, height);
        this.setArcHeight(10);
        this.setArcWidth(10);
        active = true;
        Random rand = new Random();
        this.boxValue = 1 + rand.nextInt(10);
    }

    public DestroyBlock(double xPos, double yPos, double width, double height){
        super(xPos, yPos, width, height);
        this.setArcHeight(10);
        this.setArcWidth(10);
        active = true;
        Random rand = new Random();
        this.boxValue = 1 + rand.nextInt(10);
    }

    public int getBoxValue() {
        return boxValue;
    }

    public void setInactive(){ this.active = false;}
}
