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
    private String productName,manufacturerName,warehouseAddress,quantityInStock;
    private Integer unpManufacturer;
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
            unpManufacturer = Integer.parseInt(unp_manufacturerText);
            System.out.print(unpManufacturer + " ");
        }
        else if (thisElement.equals("quantity_in_stock")) {
            quantityInStock = new String(ch, start, length);
            System.out.print(quantityInStock + " ");
        }
        else if (thisElement.equals("warehouse_address")) {
            warehouseAddress = new String(ch, start, length);
            System.out.print(warehouseAddress + "\n\n");
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
            product.add(new Product(productName, manufacturerName, unpManufacturer, quantityInStock, warehouseAddress));
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
