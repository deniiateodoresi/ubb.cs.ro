package model;

public class Order {
    private Integer number;
    private OrderStatus orderStatus;
    private Address address;
    private BankCard card;
    private ShoppingCart cart;

    public Order(Integer number, OrderStatus orderStatus, Address address, BankCard card, ShoppingCart cart) {
        this.number = number;
        this.orderStatus = orderStatus;
        this.address = address;
        this.card = card;
        this.cart = cart;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public BankCard getCard() {
        return card;
    }

    public void setCard(BankCard card) {
        this.card = card;
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }
}

