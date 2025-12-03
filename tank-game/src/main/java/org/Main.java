package org;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import model.GameModel;
import view.GameView;
import controller.GameController;
import view.IntroScreen;   // <-- add this import

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        // ---------------------------------------------------
        // 1. Create Intro Screen FIRST
        // ---------------------------------------------------
        IntroScreen intro = new IntroScreen();

        Scene introScene = intro.createScene(stage, scenario -> {
            // User clicked one of the 4 buttons

            // LOAD SELECTED SCENARIO
            GameModel model = GameModel.getInstance();

            switch (scenario) {
                case 1 -> model.loadScenario1();
                case 2 -> model.loadScenario2();
                case 3 -> model.loadScenario3();
                case 4 -> model.loadScenario4();
            }

            // ---------------------------------------------------
            // 2. After selecting a scenario, start the actual game
            // ---------------------------------------------------

            // VIEW
            Canvas canvas = new Canvas(800, 600);
            GameView view = new GameView(model, canvas);

            // CONTROLLER
            GameController controller = new GameController(model, view);

            Scene gameScene = new Scene(new StackPane(canvas));
            gameScene.setOnKeyPressed(controller);

            stage.setTitle("Tank War Game");
            stage.setScene(gameScene);

            // GAME LOOP
            javafx.animation.AnimationTimer timer = new javafx.animation.AnimationTimer() {
                @Override
                public void handle(long now) {
                    controller.updateGameLoop();
                }
            };
            controller.setGameLoop(timer);
            timer.start();
        });

        // Show intro screen
        stage.setScene(introScene);
        stage.setTitle("Tank War - Select Scenario");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
