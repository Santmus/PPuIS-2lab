package sample.Parsers;

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
import java.util.List;


public class Controller {

    private List<Product> mainTableData = new ArrayList<>();
    private List<Product> searchTableData = new ArrayList<>();
    private DOMparser parser;
    private Product product;

    public void addPatientToArray(String productName,String manufacturerName,Integer unp_manufacturer,Integer quantity_in_stock,String warehouse_address) {

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

    public void insertTableData(File file, SAXParser parser) throws ParserConfigurationException, SAXException,
                                                                                                       IOException {
        SAXparser saxParser = new SAXparser();

        SAXParserFactory factory = SAXParserFactory.newInstance();

        parser = factory.newSAXParser();
        parser.parse(file, saxParser);

        mainTableData = saxParser.getPatients();
    }

    public Page<Product> updateSearchWindowTable(int pageNumber, int recordsOnPageCount) {
        return createPage(pageNumber, recordsOnPageCount, searchTableData);
    }


    public void exit() {
        System.exit(0);
    }
}
