package sample.View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import sample.Parsers.Controller;
import sample.Product.Product;

import java.util.Collection;
import java.util.Optional;

public class MainWindowTable {

    private ObservableList<Product> productData = FXCollections.observableArrayList();
    private ObservableList<Integer> kolCountValues = FXCollections.observableArrayList(5,10,15,20);
    private Controller controller;
    private Label numberOfPages;
    private ComboBox<Integer> chooseValueProductList;
    private Button next,last,begin,end;;
    private Pane aligner;
    private int pageCount = 1;
    private int pageNumber = 1;
    private Page<Product> currentPage;

    private Label totalRecordsCountLabel = new Label();
    private Label pageNumberLabel = new Label();
    private int totalRecordsCount = 0;
    private int recordsOnPageCount = 10;

    public MainWindowTable(Controller controller) {

        this.controller = controller;

        TableView table = new TableView<>(productData);
        TableColumn<Product, String> productNameColumn = new TableColumn<>("Название товара");
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));

        TableColumn<Product, String> manufacturerNameColumn = new TableColumn<>("Название производителя");
        manufacturerNameColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturerName"));

        TableColumn<Product, Integer> unpManufacturerColumn = new TableColumn<>("УНП производителя");
        unpManufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("unpManufacturer"));

        TableColumn<Product, String> quantityInStockColumn = new TableColumn<>("Количество на складе");
        quantityInStockColumn.setCellValueFactory(new PropertyValueFactory<>("quantityInStock"));

        TableColumn<Product, String> warehouseAddressColumn = new TableColumn<>("Адрес склада");
        warehouseAddressColumn.setCellValueFactory(new PropertyValueFactory<>("warehouseAddress"));

        table.setPrefSize(960,520);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.getColumns().add(productNameColumn);
        table.getColumns().add(manufacturerNameColumn);
        table.getColumns().add(unpManufacturerColumn);
        table.getColumns().add(quantityInStockColumn);
        table.getColumns().add(warehouseAddressColumn);

        HBox horizontalTabel = new HBox();
        horizontalTabel.setAlignment(Pos.CENTER);

        chooseValueProductList = new ComboBox<>(kolCountValues);
        chooseValueProductList.setValue(15);
        chooseValueProductList.setMinSize(150,30);

        chooseValueProductList.setOnAction(event -> {
            recordsOnPageCount = chooseValueProductList.getValue();
            pageNumber = 1;
            updateTable();
        });

        aligner = new VBox(10);

        numberOfPages = new Label("Количество страниц: " + pageCount);
        numberOfPages.autosize();



        next = new Button("Следующая страница");
        next.setMinSize(120,30);

        begin = new Button("Начальная страница");
        begin.setMinSize(60,30);

        last = new Button("Предыдущая страница");
        last.setMinSize(60,30);

        end = new Button("Поcледняя страница");
        end.setMinSize(60,30);

        HBox horizontalButton = new HBox(5);
        horizontalButton.setSpacing(10);
        horizontalButton.setAlignment(Pos.CENTER);
        horizontalButton.getChildren().addAll(begin,last,next,end);


        totalRecordsCountLabel.setText("Всего товаров в списке: " + totalRecordsCount);
        totalRecordsCountLabel.setLayoutY(450);

        pageNumberLabel.setText("Cтраница: " + pageNumber);
        pageNumberLabel.setLayoutY(460);
        pageNumberLabel.setLayoutX(295);


        begin.setOnAction(actionEvent -> {
            if(pageNumber == 1){
                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.initModality(Modality.WINDOW_MODAL);
                alert.setTitle("Ошибка");
                alert.setContentText("Вы на первой странице");
                ButtonType buttonType = new ButtonType("OK");
                alert.getButtonTypes().add(buttonType);
                Optional<ButtonType> optional = alert.showAndWait();
                if (optional.get() == buttonType){
                    return;
                }
            }
            else {
                pageNumber = 1;
                updateTable();
            }
        });


        next.setOnAction(actionEvent -> {
            if (pageNumber != pageCount) {
                pageNumber += 1;
                updateTable();
            }
        });

        last.setOnAction(actionEvent -> {
            if (pageNumber != 1) {
                pageNumber -= 1;
                updateTable();
            }
        });

        end.setOnAction(actionEvent -> {
            if(pageNumber == pageCount){
                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.initModality(Modality.WINDOW_MODAL);
                alert.setTitle("Ошибка");
                alert.setContentText("Вы на последней странице");
                ButtonType buttonType = new ButtonType("OK");
                alert.getButtonTypes().add(buttonType);
                Optional<ButtonType> optional = alert.showAndWait();
                if (optional.get() == buttonType){
                    return;
                }
            }
            else {
                pageNumber = pageCount;
                updateTable();
            }
        });
        aligner.getChildren().addAll(table,numberOfPages,chooseValueProductList,horizontalButton,totalRecordsCountLabel,pageNumberLabel);
    }

    public void updateTable() {
        updateCurrentPage();
        productData.setAll((Collection<? extends Product>) currentPage.getProduct());
        pageCount = currentPage.getPageCount();
        numberOfPages.setText("Количество страниц:" + getPageCount());
        totalRecordsCount = currentPage.getTotalRecordsCount();
        totalRecordsCountLabel.setText("Всего товаров в списке:" + getTotalRecordsCount());
        pageNumberLabel.setText("Cтраница:" + currentPage.getPageNumber());
    }

    public void updateCurrentPage() {
        setCurrentPage(controller.updateMainWindowTableView(pageNumber, recordsOnPageCount));
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getRecordsOnPageCount() {
        return recordsOnPageCount;
    }


    public ObservableList<Product> getProduct() {
        return this.productData;
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getTotalRecordsCount() {
        return totalRecordsCount;
    }

    public void setCurrentPage(Page<Product> currentPage) {
        this.currentPage = currentPage;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Pane getAligner() {
        return aligner;
    }

}