package model;

import javax.persistence.*;

@Entity
@Table(name = "product_order")
public class ProductOrder {

        @EmbeddedId
        ProductOrderKey id = new ProductOrderKey();

        @ManyToOne
        @MapsId("productId")
        @JoinColumn(name = "product_id")
        Product product;

        @ManyToOne
        @MapsId("cartId")
        @JoinColumn(name = "cart_id")
        ShoppingCart cart;

        public ProductOrder(Product product, ShoppingCart cart) {
                this.product = product;
                this.cart = cart;
        }

        public ProductOrder() {
        }

        public ProductOrderKey getId() {
                return id;
        }

        public void setId(ProductOrderKey id) {
                this.id = id;
        }

        public Product getProduct() {
                return product;
        }

        public void setProduct(Product product) {
                this.product = product;
        }

        public ShoppingCart getCart() {
                return cart;
        }

        public void setCart(ShoppingCart cart) {
                this.cart = cart;
        }
}
