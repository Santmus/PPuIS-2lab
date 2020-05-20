package sample.Product;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Product {

    private StringProperty  productName;
    private StringProperty  manufacturerName;
    private SimpleIntegerProperty unp_manufacturer;
    private SimpleIntegerProperty quantity_in_stock;
    private StringProperty warehouse_address;

    public Product(String productName,String manufacturerName,Integer unp_manufacturer,Integer quantity_in_stock,String warehouse_address){
        this.productName=new SimpleStringProperty(productName);
        this.manufacturerName=new SimpleStringProperty(manufacturerName);
        this.unp_manufacturer=new SimpleIntegerProperty(unp_manufacturer);
        this.quantity_in_stock=new SimpleIntegerProperty(quantity_in_stock);
        this.warehouse_address=new SimpleStringProperty(warehouse_address);
    }

    public String getProductName() {
        return productName.get();
    }

    public String getManufacturerName() {
        return manufacturerName.get();
    }

    public Integer getUnp_manufacturer() {
        return unp_manufacturer.get();
    }

    public Integer getQuantity_in_stock() {
        return quantity_in_stock.get();
    }

    public String getWarehouse_address() {
        return warehouse_address.get();
    }

}
