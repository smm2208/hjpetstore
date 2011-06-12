package org.springframework.samples.jpetstore.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.support.PagedListHolder;

public class Cart implements Serializable {
    
    /* Private Fields */
    
    private final Map itemMap = Collections.synchronizedMap(new HashMap());
    
    private final PagedListHolder itemList = new PagedListHolder();
    
    /* JavaBeans Properties */
    
    public Cart() {
        this.itemList.setPageSize(4);
    }
    
    public Iterator getAllCartItems() { return itemList.getSource().iterator(); }
    public PagedListHolder getCartItemList() { return itemList; }
    public int getNumberOfItems() { return itemList.getSource().size(); }
    
    /* Public Methods */
    
    public boolean containsItemName(String itemName) {
        return itemMap.containsKey(itemName);
    }
    
    public void addItem(Item item, boolean isInStock) {
        CartItem cartItem = (CartItem) itemMap.get(item.getItemName());
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setItem(item);
            cartItem.setQuantity(0);
            cartItem.setInStock(isInStock);
            itemMap.put(item.getItemName(), cartItem);
            itemList.getSource().add(cartItem);
        }
        cartItem.incrementQuantity();
    }
    
    
    public Item removeItemByName(String itemName) {
        CartItem cartItem = (CartItem) itemMap.remove(itemName);
        if (cartItem == null) {
            return null;
        } else {
            itemList.getSource().remove(cartItem);
            return cartItem.getItem();
        }
    }
    
    public void incrementQuantityByItemName(String itemName) {
        CartItem cartItem = (CartItem) itemMap.get(itemName);
        cartItem.incrementQuantity();
    }
    
    public void setQuantityByItemName(String itemName, int quantity) {
        CartItem cartItem = (CartItem) itemMap.get(itemName);
        cartItem.setQuantity(quantity);
    }
    
    /**
     * 对于分页显示来说，功能不够，因为分页显示时也许只希望看到当前页的汇总，
     * 而不是的有的价格.
     */
    public BigDecimal getSubTotal() {
        BigDecimal subTotal = BigDecimal.ZERO;
        Iterator items = getAllCartItems();
        while (items.hasNext()) {
            CartItem cartItem = (CartItem) items.next();
            Item item = cartItem.getItem();
            BigDecimal listPrice = item.getListPrice();
            int quantity = cartItem.getQuantity();
            subTotal = subTotal.add(
                    listPrice.multiply(BigDecimal.valueOf((long)quantity)));
        }
        
        return subTotal;
    }
    
}
