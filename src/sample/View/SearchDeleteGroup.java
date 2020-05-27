package sample.View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Parsers.Controller;

public class SearchDeleteGroup {

    private Controller controller;
    private MainWindowTable mainWindowTable;
    private Text searchText;
    private TextField firstParametrSearch;
    private TextField secondParametrSearch;
    private Label addingProductName, addinQuantityInStock;
   private  Label addingManufacturerName,addingUnpManufacturer;
    private Button searchAndDeleteButton, exit;
    private VBox adding;

    public SearchDeleteGroup(Controller controller, MainWindowTable secondGroup,String searchOrDelete) {

        ObservableList<String> chooseSearchParametersValues = FXCollections.observableArrayList(
                "-по названию товара и количеству на складе", "-названию производителя и УНП производителя", "-по адресу склада");
        ComboBox<String> chooseSearchParameters = new ComboBox<>(chooseSearchParametersValues);
        chooseSearchParameters.setPrefSize(300, 20);
        chooseSearchParameters.setPromptText("");

        adding = new VBox(5);
        adding.setPadding(new Insets(10, 10, 10, 10));

        searchText = new Text(searchOrDelete);
        searchText.setStyle("-fx-font-size:14pt;");

        adding.getChildren().addAll(searchText, chooseSearchParameters);

        chooseSearchParameters.setOnAction(actionEvent -> {
            if (chooseSearchParameters.getValue().contains("-по названию товара и количеству на складе")) {

                searchAndDeleteButton = new Button(searchOrDelete);
                searchAndDeleteButton.setPrefSize(100, 20);

                addingProductName = new Label("Введите имя продукта");
                addinQuantityInStock = new Label("Введите кол-во на складе");

                firstParametrSearch = new TextField();
                firstParametrSearch.setPrefSize(60, 10);

                secondParametrSearch = new TextField();
                secondParametrSearch.setPrefSize(60, 10);

                exit = new Button("Выход из " + searchOrDelete + "a");

                adding.getChildren().addAll(addingProductName, firstParametrSearch, addinQuantityInStock, secondParametrSearch, searchAndDeleteButton, exit);

                searchAndDeleteButton.setOnAction(event -> {

                    checkoutParametrsSearch(firstParametrSearch.getText(), secondParametrSearch.getText());

                    if (searchOrDelete.equals("Поиск")) {

                        controller.searchProductNameAndQuantityInStock(firstParametrSearch.getText(), secondParametrSearch.getText());

                    } else {

                        int deleteNumber = controller.deleteProductNameAndQuantityInStock(firstParametrSearch.getText(), secondParametrSearch.getText());
                        Stage stage = (Stage) searchAndDeleteButton.getScene().getWindow();
                        stage.close();
                        showDeleteInformation(deleteNumber);

                    }
                    firstParametrSearch.clear();
                    secondParametrSearch.clear();
                    secondGroup.setPageNumber(1);
                    secondGroup.updateTable();
                    exit(exit);
                });

            } else if (chooseSearchParameters.getValue().contains("-названию производителя и УНП производителя")) {

                searchAndDeleteButton = new Button(searchOrDelete);
                searchAndDeleteButton.setPrefSize(100, 20);

                addingManufacturerName = new Label("Имя производителя");
                addingUnpManufacturer = new Label("УНП предприятия");

                firstParametrSearch = new TextField();
                firstParametrSearch.setPrefSize(60, 10);

                secondParametrSearch = new TextField();
                secondParametrSearch.setPrefSize(60, 10);

                exit = new Button("Выход из " + searchOrDelete + "a");

                adding.getChildren().addAll(addingManufacturerName, firstParametrSearch, addingUnpManufacturer, secondParametrSearch, searchAndDeleteButton,exit);
                searchAndDeleteButton.setOnAction(event -> {

                    checkoutParametrsSearch(firstParametrSearch.getText(), secondParametrSearch.getText());

                    if (searchOrDelete.equals("Поиск")) {

                        controller.searchManufacturerNameAndUnpManufacturer(firstParametrSearch.getText(), secondParametrSearch.getText());

                    } else {

                        int deleteNumber = controller.deleteManufacturerNameAndUnpManufacturer(firstParametrSearch.getText(), secondParametrSearch.getText());
                        Stage stage = (Stage) searchAndDeleteButton.getScene().getWindow();
                        stage.close();
                        showDeleteInformation(deleteNumber);

                    }
                    secondGroup.setPageNumber(1);
                    secondGroup.updateTable();
                    exit(exit);
                });

            }

        });

    }
    public VBox getAdding(){
        return adding;
    }

    public  void  checkoutParametrsSearch(String firstParametrSearch,String secondParametrSearch){
        if(firstParametrSearch.isEmpty() && secondParametrSearch.isEmpty()){
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("Ошибка");
            error.setContentText("Не введены все данные");
            error.show();
        }
        else {
            return;
        }
    }

    public void exit(Button button){
        button.setOnAction(event -> {
            Stage stage = (Stage) searchAndDeleteButton.getScene().getWindow();
            stage.close();
        });
    }

    private void showDeleteInformation(int numOfDeleted) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация об удалении");

        if (numOfDeleted != 0) {
            alert.setContentText(Integer.toString(numOfDeleted) + " записей было удалено");
        }
        else {
            alert.setContentText("Ничего не удалено!");
        }

        alert.showAndWait();
    }

}
