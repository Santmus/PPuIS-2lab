package sample.View;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Parsers.Controller;

public class SearchWindow {

    private VBox layout;
    private SearchDeleteGroup searchGroup;
    private SearchWindowTableGroup secondGroup;

    public SearchWindow(Stage primaryStage, Controller controller) {

        SearchDeleteGroup searchGroup = new SearchDeleteGroup(controller, secondGroup, "Поиск");
        SearchWindowTableGroup secondGroup = new SearchWindowTableGroup(controller);

        VBox layout = new VBox(5);
        layout.getChildren().addAll(searchGroup.getAdding(),secondGroup.getAligner());

        Scene scene = new Scene(layout, 1700, 980);

        Stage stage = new Stage();
        stage.setTitle("Поиск необходимого");
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);

        stage.show();
    }
}
