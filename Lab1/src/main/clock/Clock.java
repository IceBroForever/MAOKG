package main.clock;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class Clock extends Group {
    private final static int NUM_OF_DOTS = 4;

    private Hand secondsHand;
    private Hand minutesHand;
    private Hand hoursHand;

    private Timer timer;

    public Clock(double x, double y, double r, Color bgColor, Color fgColor, Color handColor){
        getChildren().add(new Circle(x, y, r, bgColor));
        getChildren().add(new Circle(x, y, r * 0.9, fgColor));

        for (int i = 0; i < NUM_OF_DOTS; i++) {
            final double angle = i * 2 * Math.PI / NUM_OF_DOTS;
            getChildren().add(new Circle(
                    x + r * 0.7 * Math.cos(angle),
                    y + r * 0.7 * Math.sin(angle),
                    r * 0.1,
                    bgColor
            ));
        }

        secondsHand = new PlainHand(x, y, r * 0.04, r * 0.9, bgColor);
        minutesHand = new PlainHand(x, y, r * 0.07, r * 0.8, handColor);
        hoursHand = new DoubledHand(x, y, r * 0.14, r * 0.6, handColor, fgColor);

        getChildren().addAll(hoursHand, minutesHand, secondsHand);
    }

    public void start() {
        if(timer != null) return;
        timer = new Timer(true);
        timer.schedule(new HandsUpdater(), 0, 1000);
    }

    public void stop() {
        if(timer == null) return;
        timer.purge();
        timer = null;
    }

    private class HandsUpdater extends TimerTask {
        @Override
        public void run() {
            LocalDateTime now = LocalDateTime.now();

            final int seconds = now.getSecond();
            secondsHand.setAngle(2 * Math.PI * now.getSecond() / 60);

            final int minutes = now.getMinute();
            minutesHand.setAngle(2 * Math.PI * (minutes + seconds / 60f) / 60);

            final int hours = now.getHour();
            hoursHand.setAngle(2 * Math.PI * ((hours % 12) + minutes / 60f) / 12);
        }
    }
}
