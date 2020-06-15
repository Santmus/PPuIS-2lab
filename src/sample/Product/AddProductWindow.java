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
    private Label nameProduct,manufacturerName,unpManufacturer,quantityInStock,warehouseAddress;
    private TextField nameProductText,manufacturerNameText,unpManufacturerText,quantityInStockText,warehouseAddressText;
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
        addProductStage.show();
    }

    public VBox getWindow() {

        VBox addingStage = new VBox(5);
        addingStage.setPadding(new Insets(10, 20, 20, 20));

        nameProduct = new Label("Название товара:");
        nameProductText = new TextField();

        manufacturerName = new Label ("Название производителя:");
        manufacturerNameText = new TextField();

        unpManufacturer = new Label ("УНП производителя:");
        unpManufacturerText = new TextField();

        quantityInStock = new Label ("Количество на складе:");
        quantityInStockText = new TextField();

        warehouseAddress = new Label("Адрес склада:");
        warehouseAddressText = new TextField();
        addingStage.getChildren().addAll(nameProduct,nameProductText,manufacturerName,manufacturerNameText,unpManufacturer,unpManufacturerText,quantityInStock,quantityInStockText,warehouseAddress,warehouseAddressText);

        add = new Button("Добавить информацию");
        add.autosize();
        addingStage.getChildren().add(add);

        add.setOnAction(event -> {
            if (nameProductText.getText().isEmpty() || manufacturerNameText.getText().isEmpty() || unpManufacturerText.getText().isEmpty() || warehouseAddressText.getText().isEmpty() || quantityInStockText.getText().isEmpty())
            {
                Alert warning = new Alert(Alert.AlertType.WARNING);
                warning.setTitle("Ошибка");
                warning.setContentText("Незаполненны все данные.");
                warning.showAndWait();
            }
            else {
                controller.addProductToList(nameProductText.getText(), manufacturerNameText.getText(), Integer.parseInt(unpManufacturerText.getText()),quantityInStockText.getText(), warehouseAddressText.getText());
                mainWindowTable.updateTable();
                Stage stage = (Stage) add.getScene().getWindow();
                stage.close();
            }
            });
        return addingStage;
    }
}
