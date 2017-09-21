package nl.utwente.mod6.ai.GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import nl.utwente.mod6.ai.Main;
import nl.utwente.mod6.ai.documents_and_words.ClassDocument;
import nl.utwente.mod6.ai.documents_and_words.Document;
import nl.utwente.mod6.ai.classifier.NaiveBayes;

import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by omer on 6-12-15.
 */
public class GUIController implements Initializable {

    @FXML
    private Label accuracyText;
    @FXML
    public TextArea processText;
    @FXML
    private Button testDirButton;
    @FXML
    private Button trainDirButton;
    @FXML
    private Button addDirButton;
    @FXML
    private Button trainDatasetButton;
    @FXML
    private Button testDatasetButton;
    @FXML
    private Button addButton;
    @FXML
    private Button exitButton;
    @FXML
    private Text testPath;
    @FXML
    private Text trainPath;
    @FXML
    private Text addPath;


    public static List<String> messages = new ArrayList<>();


    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert accuracyText != null : "fx:id=\"accuracyText\" was not injected: check your FXML file 'main_screen.fxml'.";
        assert processText != null : "fx:id=\"processText\" was not injected: check your FXML file 'main_screen.fxml'.";
        assert testDirButton != null : "fx:id=\"testDirButton\" was not injected: check your FXML file 'main_screen.fxml'.";
        assert trainDirButton != null : "fx:id=\"trainDirButton\" was not injected: check your FXML file 'main_screen.fxml'.";
        assert trainDatasetButton != null : "fx:id=\"trainDatasetButton\" was not injected: check your FXML file 'main_screen.fxml'.";
        assert testDatasetButton != null : "fx:id=\"testDatasetButton\" was not injected: check your FXML file 'main_screen.fxml'.";
        assert exitButton != null : "fx:id=\"exitButton\" was not injected: check your FXML file 'main_screen.fxml'.";
        assert testPath != null : "fx:id=\"testPath\" was not injected: check your FXML file 'main_screen.fxml'.";
        assert trainPath != null : "fx:id=\"trainPath\" was not injected: check your FXML file 'main_screen.fxml'.";
        assert addPath != null : "fx:id=\"addPath\" was not injected: check your FXML file 'main_screen.fxml'.";
        assert addDirButton != null : "fx:id=\"setAddPath\" was not injected: check your FXML file 'main_screen.fxml'.";
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'main_screen.fxml'.";

        processText.textProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue,
                                Object newValue) {
                processText.setScrollTop(processText.getHeight());
            }
        });

    }

    public String chooseDir() {
        final DirectoryChooser directoryChooser =
                new DirectoryChooser();
        final File selectedDirectory =
                directoryChooser.showDialog(MainScreen.stage);
        System.out.println(selectedDirectory.getAbsolutePath());
        return selectedDirectory.getAbsolutePath();
    }

    public void setAddPath() {
        String path = chooseDir();
        addPath.setText(path);
    }

    public void setTrainPath() {
        String path = chooseDir();
        trainPath.setText(path);
    }

    public void setTestPath() {
        String path = chooseDir();
        testPath.setText(path);
    }

    public void addWords() {
        String path = addPath.getText();
        if ((new File(path).exists())) {
            write("\n\n------------------- START OF ADDING-------------------\n\n");
            List<ClassDocument> trainngsDataSet = Main.CLASSIFIER.setUpDataForTraining(path);
            Main.CLASSIFIER.addWordsToClassifier(trainngsDataSet, Main.SMOOTHING_FACTOR);
            write(NaiveBayes.message);
            write("\n\n------------------- END OF ADDING -------------------\n\n");

        } else {
            write("Something went wrong. Check if the chosen directory exists\n");
        }

    }

    public void train() {
        String path = trainPath.getText();
        if ((new File(path).exists())) {
            write("\n\n------------------- START OF TRAINING -------------------\n\n");
            List<ClassDocument> trainngsDataSet = Main.CLASSIFIER.setUpDataForTraining(path);
            write(NaiveBayes.message);
            Main.CLASSIFIER.trainClassifier(trainngsDataSet, Main.SMOOTHING_FACTOR);
            write("\n\n------------------- END OF TRAINING -------------------\n\n");
        } else {
            write("Something went wrong. Check if the chosen directory exists\n");
        }
    }

    public void test() {
        String path = testPath.getText();
        if ((new File(path).exists())) {
            write("\n\n------------------- START OF TEST -------------------\n\n");
            List<ClassDocument> testDataSet = Main.CLASSIFIER.setUpDataForTest(path);
            write(NaiveBayes.message);
            List<Document> incorrect = Main.CLASSIFIER.testClassifier(testDataSet);
            write(NaiveBayes.message);

            write("\n\nDocuments that were wrongly classified: ");
            for (Document doc : incorrect) {
                write("\t" + doc.path);
            }
            setAccuracyTextAndSize();
            write("\n\n------------------- END OF TEST -------------------\n\n");

        } else {
            write("Something went wrong. Check if the chosen directory exists\n");
        }

    }

    public void setAccuracyTextAndSize() {
        NumberFormat formatter = new DecimalFormat("#0.0");
        accuracyText.setText(formatter.format(NaiveBayes.CURRENT_ACCURACY*100) + " %");
        accuracyText.setFont(new Font(70 + 220 / 80 * NaiveBayes.CURRENT_ACCURACY));
    }

    public void write(String msg) {
        this.processText.appendText(msg + "\n");
    }

    public void exit() {
        System.exit(0);
    }


}
