package main;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.clock.Clock;

public class Main extends Application {

    public static void main (String args[]) {
        launch(args); // main method
    }

    @Override
    public void start(Stage primaryStage)
    {
        Group root = new Group();
        Scene scene = new Scene (root, 640, 480);
        scene.setFill(Color.DARKOLIVEGREEN);

        Clock clock = new Clock(320, 240, 150, Color.GOLD, Color.WHITE, Color.BLACK);

        root.getChildren().add(clock);

        primaryStage.setScene(scene);
        primaryStage.show();

        clock.start();
    }
}
