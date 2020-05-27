package sample.Parsers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.xml.sax.SAXException;
import sample.Product.Product;
import sample.View.Page;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerException;
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

    public void addProductToList(String productName,String manufacturerName,Integer unp_manufacturer,Integer quantity_in_stock,String warehouse_address) {

        mainTableData.add(new Product(productName,manufacturerName,unp_manufacturer,quantity_in_stock,warehouse_address));
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

        Page<Product> page = new Page<>(pageNumber, pageData, pageCount, tableData.size());
        pageNumber = page.getPageNumber();
        return page;
    }

    public void saveTableData(File file, DOMparser parser) throws TransformerException, ParserConfigurationException {
        parser.parse(mainTableData, file);
    }

    public void searchProductNameAndQuantityInStock(String productName, String quantity_in_stock) {
        List<Product> searchResult = new ArrayList<>();

        for (Product product : mainTableData) {
            if (product.getProductName().contains(productName) && product.getQuantity_in_stock().toString().equals(quantity_in_stock)) {
                searchResult.add(product);
            }
        }
        searchTableData = searchResult;
    }

    public int deleteProductNameAndQuantityInStock(String productName, String quantity_in_stock){
        int deleteNumber = 0;
        for (Iterator<Product> iterator = mainTableData.iterator(); iterator.hasNext();) {
            Product product = iterator.next();
            if (product.getProductName().contains(productName) && product.getQuantity_in_stock().toString().equals(quantity_in_stock)) {
                iterator.remove();
                deleteNumber++;
            }
        }
        return deleteNumber;
    }

    public void searchManufacturerNameAndUnpManufacturer(String manufacturerName,String unp_manufacturer){
        List<Product> searchResult = new ArrayList<>();

        for (Product product : mainTableData) {
            if (product.getManufacturerName().contains(manufacturerName) && product.getUnp_manufacturer().toString().equals(unp_manufacturer)) {
                searchResult.add(product);
            }
        }
        searchTableData = searchResult;
    }

    public int deleteManufacturerNameAndUnpManufacturer(String manufacturerName, String unp_manufacturer){
        int deleteNumber = 0;
        for (Iterator<Product> iterator = mainTableData.iterator(); iterator.hasNext();) {
            Product product = iterator.next();
            if (product.getManufacturerName().contains(manufacturerName) && product.getUnp_manufacturer().toString().equals(unp_manufacturer)) {
                iterator.remove();
                deleteNumber++;
            }
        }
        return deleteNumber;
    }

    public void searchWarehouseAddress(String warehouse_address){
        List<Product> searchResult = new ArrayList<>();

        for (Product product : mainTableData) {
            if (product.getWarehouse_address().contains(warehouse_address)) {
                searchResult.add(product);
            }
        }
        searchTableData = searchResult;
    }

    public int deleteWarehouseAddress(String warehouse_address){
        int deleteNumber = 0;
        for (Iterator<Product> iterator = mainTableData.iterator(); iterator.hasNext();) {
            Product product = iterator.next();
            if (product.getWarehouse_address().contains(warehouse_address)) {
                iterator.remove();
                deleteNumber++;
            }
        }
        return deleteNumber;
    }

    public void insertTableData(File file, SAXParser saxParser) throws ParserConfigurationException, SAXException,
                                                                                                       IOException {
        SAXparser saXparser = new SAXparser();

        SAXParserFactory factory = SAXParserFactory.newInstance();
        saxParser = factory.newSAXParser();
        saxParser.parse(file, saXparser);

        mainTableData = saXparser.getProduct();
    }

    public Page<Product> updateSearchWindowTable(int pageNumber, int recordsOnPageCount) {
        return createPage(pageNumber, recordsOnPageCount, searchTableData);
    }


    public void exit() {
        try {
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
            } finally {
            return;
            }
        }
}

