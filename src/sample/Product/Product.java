package sample.Product;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Product {

    private StringProperty  productName;
    private StringProperty  manufacturerName;
    private SimpleIntegerProperty unpManufacturer;
    private StringProperty quantityInStock;
    private StringProperty warehouseAddress;

    public Product(String productName,String manufacturerName,Integer unpManufacturer,String quantityInStock,String warehouseAddress){
        this.productName=new SimpleStringProperty(productName);
        this.manufacturerName=new SimpleStringProperty(manufacturerName);
        this.unpManufacturer=new SimpleIntegerProperty(unpManufacturer);
        this.quantityInStock=new SimpleStringProperty(quantityInStock);
        this.warehouseAddress=new SimpleStringProperty(warehouseAddress);
    }

    public String getProductName() {
        return productName.get();
    }

    public String getManufacturerName() {
        return manufacturerName.get();
    }

    public Integer getUnpManufacturer() {
        return unpManufacturer.get();
    }

    public String getQuantityInStock() {
        return quantityInStock.get();
    }

    public String getWarehouseAddress() {
        return warehouseAddress.get();
    }

}
