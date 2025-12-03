package view;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.SpriteManager;

public class IntroScreen {
    private final SpriteManager spriteManager = new SpriteManager();
    public interface ScenarioListener {
        void onScenarioSelected(int scenario);
    }

    public Scene createScene(Stage stage, ScenarioListener listener) {

        // Container with background image
        Image bg = spriteManager.introScreen;
        BackgroundImage bgImage = new BackgroundImage(
                bg,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(
                        BackgroundSize.AUTO, BackgroundSize.AUTO,
                        false, false, false, false
                )
        );

        BorderPane root = new BorderPane();
        root.setBackground(new Background(bgImage));

        // Buttons at the top to select scenario
        VBox buttonBox = new VBox(10);
        buttonBox.setAlignment(Pos.TOP_CENTER);
        buttonBox.setStyle("-fx-padding: 20;");
        Button s1 = createScenarioButton("Scenario 1", () -> listener.onScenarioSelected(1));
        Button s2 = createScenarioButton("Scenario 2", () -> listener.onScenarioSelected(2));
        Button s3 = createScenarioButton("Scenario 3", () -> listener.onScenarioSelected(3));
        Button s4 = createScenarioButton("Scenario 4", () -> listener.onScenarioSelected(4));
        buttonBox.getChildren().addAll(s1, s2, s3, s4);
        root.setTop(buttonBox);

        return new Scene(root, 800, 600);
    }

    // Creates button
    private Button createScenarioButton(String text, Runnable action) {
        Button b = new Button(text);
        b.setPrefWidth(200);

        // Army green background + darker green text
        b.setStyle(
                "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-color: #556B2F;" +  // army green
                        "-fx-text-fill: #2E3D16;" +         // darker green text
                        "-fx-background-radius: 5;" +
                        "-fx-border-color: #2E3D16;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 5;"
        );

        b.setOnAction(e -> action.run());
        return b;
    }
}
