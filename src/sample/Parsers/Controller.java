package sample.Parsers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.xml.sax.SAXException;
import sample.Product.Product;
import sample.View.Page;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


/*
  Условия поиска и удаления:
-по названию товара или количеству на складе; +
-названию производителя или УНП производителя
-по адресу склада;
*/

public class Controller {

    private List<Product> mainTableData = new ArrayList<>();
    private List<Product> searchTableData = new ArrayList<>();
    private DOMparser parser;
    private Product product;

    public void addProductToList(String productName,String manufacturerName,Integer unpManufacturer,String quantityInStock,String warehouseAddress) {

        mainTableData.add(new Product(productName,manufacturerName,unpManufacturer,quantityInStock,warehouseAddress));
    }

    public Page<Product> updateMainWindowTableView(int pageNumber, int recordsOnPageCount) {
        return createPage(pageNumber, recordsOnPageCount, mainTableData);
    }

    public Page<Product> createPage(int pageNumber, int recordsOnPageCount, List<Product> tableData) {
        int pageCount = 1;
        if (tableData.size() > recordsOnPageCount) {
            if (tableData.size() % recordsOnPageCount != 0) {
                pageCount = (tableData.size() - tableData.size() % recordsOnPageCount) / recordsOnPageCount + 1;
            }
            else {
                pageCount = (tableData.size() - tableData.size() % recordsOnPageCount) / recordsOnPageCount;
            }
        }

        List<Product> pageData;
        if (pageCount != pageNumber && pageCount != 0) {
            pageData = tableData.subList((pageNumber - 1) * recordsOnPageCount,
                                        (pageNumber - 1) * recordsOnPageCount + recordsOnPageCount);
        }
        else pageData = tableData.subList((pageNumber - 1) * recordsOnPageCount,
                tableData.size());

        return new Page<>(pageNumber, pageData, pageCount, tableData.size());
    }

    public void saveTableData(File file, DOMparser parser) {
        parser.parse(mainTableData, file);
    }

    public void searchProductNameAndQuantityInStock(String productName, String quantityInStock) {
        List<Product> searchResult = new ArrayList<>();

        for (Product product : mainTableData) {
            if (product.getProductName().contains(productName) && product.getQuantityInStock().contains(quantityInStock)) {
                searchResult.add(product);
            }
        }
        searchTableData = searchResult;
    }

    public int deleteProductNameAndQuantityInStock(String productName, String quantityInStock){
        int deleteNumber = 0;
        Iterator<Product> iterator = mainTableData.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getProductName().contains(productName) && product.getQuantityInStock().contains(quantityInStock)) {
                iterator.remove();
                deleteNumber++;
            }
        }
        return deleteNumber;
    }

    public void searchManufacturerNameAndUnpManufacturer(String manufacturerName, String unpManufacturer){
        List<Product> searchResult = new ArrayList<>();

        for (Product product : mainTableData) {
            if (product.getManufacturerName().contains(manufacturerName) && product.getUnpManufacturer().toString().equals(unpManufacturer)) {
                searchResult.add(product);
            }
        }
        searchTableData = searchResult;
    }

    public int deleteManufacturerNameAndUnpManufacturer(String manufacturerName, String unpManufacturer){
        int deleteNumber = 0;
        Iterator<Product> iterator = mainTableData.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getManufacturerName().contains(manufacturerName) && product.getUnpManufacturer().toString().equals(unpManufacturer)) {
                iterator.remove();
                deleteNumber++;
            }
        }
        return deleteNumber;
    }

    public void searchWarehouseAddress(String warehouseAddress){
        List<Product> searchResult = new ArrayList<>();

        for (Product product : mainTableData) {
            if (product.getWarehouseAddress().contains(warehouseAddress)) {
                searchResult.add(product);
            }
        }
        searchTableData = searchResult;
    }

    public int deleteWarehouseAddress(String warehouseAddress){
        int deleteNumber = 0;
        Iterator<Product> iterator = mainTableData.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getWarehouseAddress().contains(warehouseAddress)) {
                iterator.remove();
                deleteNumber++;
            }
        }
        return deleteNumber;
    }

    public void insertTableData(File file) throws ParserConfigurationException, SAXException,
                                                                                                       IOException {
        SAXparser saXparser = new SAXparser();

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(file, saXparser);

        mainTableData = saXparser.getProduct();
    }

    public Page<Product> updateSearchWindowTable(int pageNumber, int recordsOnPageCount) {
        return createPage(pageNumber, recordsOnPageCount, searchTableData);
    }


    public void exit() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Выход из программы");
        alert.setContentText("Вы точно уверены что хотите выйти?");
        ButtonType yes = new ButtonType("Да");
        ButtonType no = new ButtonType("Нет");
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(no, yes);
        Optional<ButtonType> optional = alert.showAndWait();
        if (optional.get() == yes) {
            System.exit(0);
        }
        else if (optional.get() == no) {
               return;
        }
    }
}

