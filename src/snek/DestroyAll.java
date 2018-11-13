package snek;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class DestroyAll extends Circle {

    public DestroyAll(){
        super(10);
    }

    public DestroyAll(Image background){
        super(10);
        this.setFill(new ImagePattern(background));
    }

}
