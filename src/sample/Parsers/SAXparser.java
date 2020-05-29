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
    private String productName,manufacturerName,warehouse_address,quantity_in_stock;
    private Integer unp_manufacturer;
    private int readCounter;

    @Override
    public void startDocument() { System.out.println("Start parse XML..."); }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attributes) {
        thisElement = qName;
    }

    @Override
    public void characters(char[] ch, int start, int length){

        if (thisElement.equals("productName")) {
            productName = new String(ch, start, length);
            System.out.print("Товар: \n" + productName + " ");
        }
        else if (thisElement.equals("manufacturerName")) {
            manufacturerName = new String(ch, start, length);
            System.out.print(manufacturerName + " ");
        }
        else if (thisElement.equals("unp_manufacturer")) {
            String unp_manufacturerText = new String(ch, start, length);
            unp_manufacturer = Integer.parseInt(unp_manufacturerText);
            System.out.print(unp_manufacturer + " ");
        }
        else if (thisElement.equals("quantity_in_stock")) {
            quantity_in_stock = new String(ch, start, length);
            System.out.print(quantity_in_stock + " ");
        }
        else if (thisElement.equals("warehouse_address")) {
            warehouse_address = new String(ch, start, length);
            System.out.print(warehouse_address + "\n\n");
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) {
      /*
       if (!thisElement.equals("tableDate")) { readCounter++;}
      */
        if (readCounter != 5) {
            readCounter++;
        } else {
            product.add(new Product(productName, manufacturerName, unp_manufacturer, quantity_in_stock, warehouse_address));
            readCounter = 0;
        }
        thisElement = " ";
    }

    @Override
    public void endDocument() { System.out.println("Stop parse XML..."); }

    public List<Product> getProduct() {
        return product;
    }
}
