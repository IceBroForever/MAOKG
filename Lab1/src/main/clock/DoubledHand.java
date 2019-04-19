package main.clock;

import javafx.scene.paint.Color;

public class DoubledHand extends Hand {
    private PlainHand background;
    private PlainHand foreground;

    public DoubledHand(double x, double y, double width, double height, Color bgColor, Color fgColor){
        super();
        background = new PlainHand(x, y, width, height, bgColor);
        foreground = new PlainHand(x, y, width * 0.5, height, fgColor);
        getChildren().addAll(background, foreground);
    }

    @Override
    public void setAngle(double angle) {
        background.setAngle(angle);
        foreground.setAngle(angle);
    }
}
