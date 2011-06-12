package org.springframework.samples.jpetstore.web.struts;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import org.springframework.samples.jpetstore.domain.Order;

public class OrderActionForm extends BaseActionForm {
  
  /* Constants */
  
  private static final List CARD_TYPE_LIST = new ArrayList();
  
  /* Private Fields */
  
  private Order order;
  private boolean shippingAddressRequired;
  
  // pp: 因为使用了 step 参数来辨认，所以不需要了
  //private boolean confirmed;
  private List cardTypeList;
  
  /**
   * 此 Form 为向导型
   * （也就是多页表单，billing info -> ship address (option) -> confirm）,
   * 加了一个 step 请求参数以便校验时作正常处理，原版本中存在 Bug
   *
   */
  private String step;
  
  /* Static Initializer */
  
  static {
    CARD_TYPE_LIST.add("Visa");
    CARD_TYPE_LIST.add("MasterCard");
    CARD_TYPE_LIST.add("American Express");
  }
  
  /* Constructors */
  
  public OrderActionForm() {
    this.order = new Order();
    this.shippingAddressRequired = false;
    this.cardTypeList = CARD_TYPE_LIST;
    //this.confirmed = false;
  }
  
  /* JavaBeans Properties */
  
  // pp: 因为使用了 step 参数来辨认，所以不需要了
//  public boolean isConfirmed() {
//    return confirmed;
//  }
//  public void setConfirmed(boolean confirmed) {
//    this.confirmed = confirmed;
//  }
  
  public Order getOrder() {
    return order;
  }
  public void setOrder(Order order) {
    this.order = order;
  }
  
  public boolean isShippingAddressRequired() {
    return shippingAddressRequired;
  }
  public void setShippingAddressRequired(boolean shippingAddressRequired) {
    this.shippingAddressRequired = shippingAddressRequired;
  }
  
  public List getCreditCardTypes() {
    return cardTypeList;
  }
  
  public String getStep() {
    return step;
  }
  
  public void setStep(String step) {
    this.step = step;
  }
  
  /* Public Methods */
  
  public void doValidate(ActionMapping mapping,
          HttpServletRequest request, List errors) {
    
    // fixed by: pprun, bug is here in original version!
    // 使用 step 参数来辨认
    if (step.equals("1")) {
      // Payment Details
      addErrorIfStringEmpty(errors, "Card type is required.",
              order.getCardType());
      addErrorIfStringEmpty(errors, "FAKE (!) credit card number required.",
              order.getCardNumber());
      addErrorIfStringEmpty(errors, "Expiry date is required.",
              order.getExpireDate());
      
      // Billing Address
      addErrorIfStringEmpty(errors, "Billing Info: first name is required.",
              order.getBillToFirstname());
      addErrorIfStringEmpty(errors, "Billing Info: last name is required.",
              order.getBillToLastname());
      addErrorIfStringEmpty(errors, "Billing Info: address is required.",
              order.getBillAddr().getAddr1());
      // Addr2 is optinal
      addErrorIfStringEmpty(errors, "Billing Info: city is required.",
              order.getBillAddr().getCity());
      addErrorIfStringEmpty(errors, "Billing Info: state is required.",
              order.getBillAddr().getState());
      addErrorIfStringEmpty(errors, "Billing Info: zip/postal code is required.",
              order.getBillAddr().getZipcode());
      addErrorIfStringEmpty(errors, "Billing Info: country is required.",
              order.getBillAddr().getCountry());
      
      if (errors.size() > 0) {
        // 用原值替换所有的 bill info 项
        order.setBillAddr(order.getBillAddr());
      }
      
    } else if (step.equals("2")) {
      
      addErrorIfStringEmpty(errors, "Shipping Info: first name is required.",
              order.getShipToFirstname());
      addErrorIfStringEmpty(errors, "Shipping Info: last name is required.",
              order.getShipToLastname());
      addErrorIfStringEmpty(errors, "Shipping Info: address is required.",
              order.getShipAddr().getAddr1());
      // Addr2 is optinal
      addErrorIfStringEmpty(errors, "Shipping Info: city is required.",
              order.getShipAddr().getCity());
      addErrorIfStringEmpty(errors, "Shipping Info: state is required.",
              order.getShipAddr().getState());
      addErrorIfStringEmpty(errors, "Shipping Info: zip/postal code is required.",
              order.getShipAddr().getZipcode());
      addErrorIfStringEmpty(errors, "Shipping Info: country is required.",
              order.getShipAddr().getCountry());
      
      if (errors.size() > 0) {
        // 用原值替换所有的 shipAddr 项
        order.setShipAddr(order.getShipAddr());
      }
    }
  }
  
  public void reset(ActionMapping mapping, HttpServletRequest request) {
    super.reset(mapping, request);
    this.shippingAddressRequired = false;
    
  }
  
}
