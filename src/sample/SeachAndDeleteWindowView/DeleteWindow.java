package sample.SeachAndDeleteWindowView;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Parsers.Controller;
import sample.SeachAndDeleteWindowView.SearchDeleteGroup;
import sample.View.MainWindowTable;

public class DeleteWindow {

    public DeleteWindow(Stage primaryStage, Controller controller, MainWindowTable secondTableView) {

        SearchDeleteGroup deleteGroup = new SearchDeleteGroup(controller, secondTableView, "Удаление");

        BorderPane layout = new BorderPane();
        layout.setCenter(deleteGroup.getAdding());
        Scene scene = new Scene(layout, 400, 280);

        Stage stage = new Stage();
        stage.setTitle("Удаение");
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);

        stage.show();
    }

}
