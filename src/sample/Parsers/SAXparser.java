package sample.Parsers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import sample.Product.Product;

import java.util.ArrayList;
import java.util.List;

public class SAXparser extends DefaultHandler {

    private List<Product> product = new ArrayList<>();
    private String thisElement;
    private String productName,manufacturerName,warehouse_address;
    private Integer unp_manufacturer,quantity_in_stock;
    private int readCounter = 0;

    @Override
    public void startDocument() throws SAXException { System.out.println("Start parse XML..."); }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attributes) throws SAXException {
        thisElement = qName;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (thisElement.equals("productName")) {
            productName = new String(ch, start, length);
        }
        if (thisElement.equals("manufacturerName")) {
            manufacturerName = new String(ch, start, length);
        }
        if (thisElement.equals("unp_manufacturer")) {
            String unp_manufacturerText = new String(ch, start, length);
            unp_manufacturer = Integer.parseInt(unp_manufacturerText);
        }
        if (thisElement.equals("quantity_in_stock")) {
            String quantity_in_stockText = new String(ch, start, length);
            quantity_in_stock = Integer.parseInt(quantity_in_stockText);
        }
        if (thisElement.equals("warehouse_address")) {
            warehouse_address = new String(ch, start, length);
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        if (!thisElement.equals("tableDate")) {
            readCounter++;
        }
        if (readCounter == 6) {
            product.add(new Product(productName,manufacturerName,unp_manufacturer,quantity_in_stock,warehouse_address));
            readCounter = 0;
        }
        thisElement = " ";
    }

    @Override
    public void endDocument() { System.out.println("Stop parse XML..."); }

    public List<Product> getPatients() {
        return product;
    }
}
