package snek;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * Contains definitions for DestroyBloks.
 */
public class DestroyAll extends Circle {

    public DestroyAll(){
        super(10);
    }

    /**
     * A simple constructor to create an object, of specific background.
     * @param background
     */
    public DestroyAll(Image background){
        super(10);
        this.setFill(new ImagePattern(background));
    }

}
