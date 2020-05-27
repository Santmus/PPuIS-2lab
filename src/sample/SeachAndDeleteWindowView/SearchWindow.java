package sample.SeachAndDeleteWindowView;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Parsers.Controller;

public class SearchWindow {

    private VBox layout;

    public SearchWindow(Stage primaryStage, Controller controller) {

        SearchWindowTableGroup secondGroup = new SearchWindowTableGroup(controller);
        SearchDeleteGroup searchGroup = new SearchDeleteGroup(controller, secondGroup, "Поиск");

        VBox layout = new VBox(5);
        layout.getChildren().addAll(secondGroup.getAligner(),searchGroup.getAdding());

        Scene scene = new Scene(layout, 1600, 900);

        Stage stage = new Stage();
        stage.setTitle("Поиск необходимого");
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);

        stage.show();
    }
}
