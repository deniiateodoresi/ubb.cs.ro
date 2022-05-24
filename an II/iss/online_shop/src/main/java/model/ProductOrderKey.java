package model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductOrderKey implements Serializable {

        @Column(name = "product_id")
        Integer productId;

        @Column(name = "cart_id")
        Integer cartId;

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                ProductOrderKey that = (ProductOrderKey) o;
                return Objects.equals(productId, that.productId) && Objects.equals(cartId, that.cartId);
        }

        @Override
        public int hashCode() {
                return Objects.hash(productId, cartId);
        }

        public Integer getProductId() {
                return productId;
        }

        public void setProductId(Integer productId) {
                this.productId = productId;
        }

        public Integer getCartId() {
                return cartId;
        }

        public void setCartId(Integer cartId) {
                this.cartId = cartId;
        }
}
