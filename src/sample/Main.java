package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Parsers.Controller;
import sample.View.MainWindowTable;
import sample.View.MenuEvent;

import java.io.File;


public class Main extends Application {

    private FlowPane root;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FileChooser openFileChooser = new FileChooser();
        openFileChooser.setTitle("Открытие файла");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("XML files (*.xml)","*.xml");
        openFileChooser.setInitialDirectory(new File(("C:/Users/Евгений/Desktop/Study/2 курс/2 курс(2 сем)")));
        openFileChooser.getExtensionFilters().add(extensionFilter);


        Controller controller = new Controller();
        MainWindowTable insertTableElements= new MainWindowTable(controller);
        MenuEvent menuEvent = new MenuEvent(insertTableElements,primaryStage,controller,openFileChooser);

        root = new FlowPane(15,30);
        root.getChildren().addAll(menuEvent.getAligner(),insertTableElements.getAligner());

        Scene scene = new Scene(root,960,720);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
