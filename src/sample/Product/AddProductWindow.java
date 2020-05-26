package sample.Product;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Parsers.Controller;
import sample.View.MainWindowTable;


public class AddProductWindow {

    private Controller controller;
    private MainWindowTable mainWindowTable;
    private Label nameProduct,manufacturerName,unp_manufacturer,quantity_in_stock,warehouse_address;
    private TextField nameProductText,manufacturerNameText,unp_manufacturerText,quantity_in_stockText,warehouse_addressText;
    private Button add;

    public AddProductWindow(MainWindowTable mainWindowTable, Stage primaryStage, Controller controller) {

        this.controller = controller;
        this.mainWindowTable = mainWindowTable;

        VBox layout = getWindow();

        Scene secondScene = new Scene(layout,640,320);

        Stage addProductStage = new Stage();
        addProductStage.setTitle("Добавление продукта");
        addProductStage.setScene(secondScene);
        addProductStage.initModality(Modality.WINDOW_MODAL);
        addProductStage.initOwner(primaryStage);

        addProductStage .show();
    }

    public VBox getWindow() {

        VBox addingStage = new VBox(5);
        addingStage.setPadding(new Insets(10, 20, 20, 20));

        nameProduct = new Label("Название товара");
        nameProductText = new TextField();

        manufacturerName = new Label ("Название производителя");
        manufacturerNameText = new TextField();

        unp_manufacturer = new Label ("УНП производителя");
        unp_manufacturerText = new TextField();

        quantity_in_stock = new Label ("Количество на складе");
        quantity_in_stockText = new TextField();

        warehouse_address = new Label("Адрес склада");
        warehouse_addressText = new TextField();
        addingStage.getChildren().addAll(nameProduct,nameProductText,manufacturerName,manufacturerNameText,unp_manufacturer,unp_manufacturerText,quantity_in_stock,quantity_in_stockText,warehouse_address,warehouse_addressText);

        add = new Button("Добавить информацию");
        add.autosize();
        addingStage.getChildren().add(add);

        add.setOnAction(event -> {
            if (nameProductText.getText().isEmpty() || manufacturerNameText.getText().isEmpty() || unp_manufacturerText.getText().isEmpty() || warehouse_addressText.getText().isEmpty() || quantity_in_stockText.getText().isEmpty())
            {
                Alert warning = new Alert(Alert.AlertType.WARNING);
                warning.setTitle("Ошибка");
                warning.setContentText("Незаполненны все данные.");
                warning.showAndWait();
            }
            else {
                controller.addProductToArray(nameProductText.getText(), manufacturerNameText.getText(), Integer.parseInt(unp_manufacturerText.getText()), Integer.parseInt(quantity_in_stockText.getText()), warehouse_addressText.getText());
                mainWindowTable.updateTable();
                Stage stage = (Stage) add.getScene().getWindow();
                stage.close();
            }
            });
        return addingStage;
    }
}
