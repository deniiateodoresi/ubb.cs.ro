package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shopping_cart")
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

    @OneToOne
    @JoinColumn(name="client_id")
    public Client getClient() { return client;  }

    public void setClient(Client client) {
        this.client = client;
    }

    public void add(Product product){
        products.add(product);
    }

    @Transient
    public List<Product> getProducts(){
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "nr_products")
    public Integer getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(Integer productNumber) {
        this.productNumber = productNumber;
    }

    @Column(name = "total_price")
    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
