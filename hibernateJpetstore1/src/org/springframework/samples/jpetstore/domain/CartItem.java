package org.springframework.samples.jpetstore.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class CartItem implements Serializable {
    
    /* Private Fields */
    
    private Item item;
    private int quantity;
    private boolean inStock;
    
    /* JavaBeans Properties */
    
    public boolean isInStock() { return inStock; }
    public void setInStock(boolean inStock) { this.inStock = inStock; }
    
    public Item getItem() { return item; }
    public void setItem(Item item) {
        this.item = item;
    }
    
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public BigDecimal getTotalPrice() {
        if (item != null) {
            return item.getListPrice().multiply(
                    BigDecimal.valueOf(quantity));
        } else {
            return BigDecimal.ZERO;
        }
    }
    
    /* Public methods */
    
    public void incrementQuantity() {
        quantity++;
    }
    
}
