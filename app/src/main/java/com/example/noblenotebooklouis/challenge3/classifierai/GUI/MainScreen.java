package nl.utwente.mod6.ai.GUI;
/**
 * GUI, this is the class that should be executed.
 * Created by omer on 5-12-15.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import nl.utwente.mod6.ai.Main;
import nl.utwente.mod6.ai.classifier.NaiveBayes;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainScreen extends Application {

    public static void main(String[] args) {
        Main.CLASSIFIER = new NaiveBayes();
        launch(args);
    }

    public static Stage stage;
    public static GUIController guiController;

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            stage = primaryStage;
            final URL resource = this.getClass().getResource("main_screen.fxml");
            Parent root = FXMLLoader.load(resource);
            Scene scene = new Scene(root, 400, 400);

            FXMLLoader fxmlLoader = new FXMLLoader();
            SplitPane p = fxmlLoader.load(resource.openStream());
            guiController = fxmlLoader.getController();

            primaryStage.setTitle("Naive Bayes Classifier");
            primaryStage.setMaximized(true);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
