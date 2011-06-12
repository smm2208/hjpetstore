package org.springframework.samples.jpetstore.web.struts;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import org.springframework.samples.jpetstore.domain.Cart;

public class CartActionForm extends BaseActionForm {
  
  /* Private Fields */
  
  private Cart cart = new Cart();
  private String workingItemName;
  
  /* JavaBeans Properties */
  
  public Cart getCart() {
    return cart;
  }
  
  public void setCart(Cart cart) {
    this.cart = cart;
  }
  
  public String getWorkingItemName() {
    return workingItemName;
  }
  
  public void setWorkingItemName(String workingItemName) {
    this.workingItemName = workingItemName;
  }
  
  /* Public Methods */
  
  public void reset(ActionMapping mapping, HttpServletRequest request) {
    super.reset(mapping, request);
    workingItemName = null;
  }
}
