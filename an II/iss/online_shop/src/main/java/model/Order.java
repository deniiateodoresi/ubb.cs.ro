package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    private Integer number;
    private Address address;
    private BankCard card;
    private ShoppingCart cart;

    public Order(Address address, BankCard card, ShoppingCart cart) {
        this.address = address;
        this.card = card;
        this.cart = cart;
    }

    public Order() {

    }

    @Id
    @Column(name="id")
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @OneToOne
    @JoinColumn(name="address_id")
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @OneToOne
    @JoinColumn(name="card_id")
    public BankCard getCard() {
        return card;
    }

    public void setCard(BankCard card) {
        this.card = card;
    }

    @OneToOne
    @JoinColumn(name="cart_id")
    public ShoppingCart getCart() {
        return cart;
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }
}

