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

    public void parse(List<Product> tableData, File file) throws ParserConfigurationException, TransformerException {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder ;
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
        document.appendChild(productItem);

        Element productName = document.createElement("productName");
        productName.appendChild(document.createTextNode(product.getProductName()));
        productItem.appendChild(productName);

        Element manufacturerName = document.createElement("manufacturerName");
        manufacturerName.appendChild(document.createTextNode(product.getManufacturerName()));
        productItem.appendChild(manufacturerName);

        Element unp_manufacturer = document.createElement("unp_manufacturer");
        unp_manufacturer.appendChild(document.createTextNode(product.getUnp_manufacturer().toString()));
        productItem.appendChild(unp_manufacturer);

        Element quantity_in_stock = document.createElement("quantity_in_stock");
        quantity_in_stock.appendChild(document.createTextNode(product.getQuantity_in_stock().toString()));
        productItem.appendChild(quantity_in_stock);

        Element warehouse_address = document.createElement("warehouse_address");
        warehouse_address.appendChild(document.createTextNode(product.getWarehouse_address()));
        productItem.appendChild(warehouse_address);

        return productItem;
    }

    private void saveDataInFile(DOMSource source, StreamResult result) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(source, result);
    }
}
