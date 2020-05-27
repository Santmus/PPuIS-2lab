package sample.View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private ComboBox chooseValueProductList;
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

        TableColumn<Product, Integer> unp_manufacturerColumn = new TableColumn<>("УНП производителя");
        unp_manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("unp_manufacturer"));

        TableColumn<Product, Integer> quantity_in_stockColumn = new TableColumn<>("Количество на складе");
        quantity_in_stockColumn.setCellValueFactory(new PropertyValueFactory<>("quantity_in_stock"));

        TableColumn<Product, String> warehouse_addressColumn = new TableColumn<>("Адрес склада");
        warehouse_addressColumn.setCellValueFactory(new PropertyValueFactory<>("warehouse_address"));

        table.setPrefSize(960,520);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.getColumns().add(productNameColumn);
        table.getColumns().add(manufacturerNameColumn);
        table.getColumns().add(unp_manufacturerColumn);
        table.getColumns().add(quantity_in_stockColumn);
        table.getColumns().add(warehouse_addressColumn);

        chooseValueProductList = new ComboBox(kolCountValues);
        chooseValueProductList.setValue(15);
        chooseValueProductList.setMinSize(150,30);

        chooseValueProductList.setOnAction(event -> {
            recordsOnPageCount = (int) chooseValueProductList.getValue();
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

        end = new Button("Поеследняя страница");
        end.setMinSize(60,30);

        HBox horizontal = new HBox(5);
        horizontal.setSpacing(10);
        horizontal.getChildren().addAll(begin,next,last,end);


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
            else{
                return;
            }
        });

        last.setOnAction(actionEvent -> {
            if (pageNumber != 1) {
                pageNumber -= 1;
                updateTable();
            }
            else {
                return;
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
        aligner.getChildren().addAll(table,numberOfPages,chooseValueProductList,horizontal,totalRecordsCountLabel,pageNumberLabel);
    }

    public void updateTable() {
        updateCurrentPage();
        productData.setAll((Collection<? extends Product>) currentPage.getPatients());
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


    public ObservableList<Product> getPatients() {
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