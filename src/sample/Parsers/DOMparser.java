package sample.Parsers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import sample.Product.Product;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class DOMparser {

    public void parse(List<Product> tableData, File file) {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        try {
            docBuilder = docFactory.newDocumentBuilder();
            Document document = docBuilder.newDocument();
            Element docRootElement = document.createElement("tableData");
            for (int index = 0; index < tableData.size(); index++) {
                docRootElement.appendChild(addPlayerToDocument(index, tableData.get(index), document));
            }
            document.appendChild(docRootElement);
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(file);
            saveDataInFile(source, result);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Element addPlayerToDocument(int index, Product product, Document document) {

        Element productItem = document.createElement("product");
        productItem.setAttribute("id", Integer.toString(index));


        Element productName = document.createElement("productName");
        productName.appendChild(document.createTextNode(product.getProductName()));
        productItem.appendChild(productName);

        Element manufacturerName = document.createElement("manufacturerName");
        manufacturerName.appendChild(document.createTextNode(product.getManufacturerName()));
        productItem.appendChild(manufacturerName);

        Element unpManufacturer = document.createElement("unp_manufacturer");
        unpManufacturer.appendChild(document.createTextNode(product.getUnpManufacturer().toString()));
        productItem.appendChild(unpManufacturer);

        Element quantityInStock = document.createElement("quantity_in_stock");
        quantityInStock.appendChild(document.createTextNode(product.getQuantityInStock().toString()));
        productItem.appendChild(quantityInStock);

        Element warehouseAddress = document.createElement("warehouse_address");
        warehouseAddress.appendChild(document.createTextNode(product.getWarehouseAddress()));
        productItem.appendChild(warehouseAddress);

        return productItem;
    }

    private void saveDataInFile(DOMSource source, StreamResult result) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(source, result);
    }
}
