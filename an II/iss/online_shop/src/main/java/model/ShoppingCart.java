package model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private Integer id;
    private Integer productNumber;
    private Float totalPrice;
    private List<Product> products;
    private Client client;

    public ShoppingCart() {
        productNumber = 0;
        totalPrice = 0f;
        products = new ArrayList<>();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void add(Product product){
        products.add(product);
    }

    public List<Product> getProducts(){
        return products;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(Integer productNumber) {
        this.productNumber = productNumber;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
