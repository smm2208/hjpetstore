/*
 * Order.java
 *
 * Created on 2006年10月1日, 下午8:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.springframework.samples.jpetstore.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * todo: 没有象其它模型对象把 orderId 改为"自然键",
 * 这里保留了它，并改名 id.
 *
 * Order 为数据库关键字，所以表名不能为 Order.
 * 这可是花了几小时的代价得出的经验
 * @author pprun
 */
public class Order implements java.io.Serializable, Comparable {
  private Long id;
  private int version;
  
  // @ManyToOne
  // @JoinColumn(name="userId", nullable = true, updatable = false)
  private Account user;
  private Date orderDate;
  private Address shipAddr;
  private Address billAddr;
  private String courier;
  private BigDecimal totalPrice;
  private String billToFirstname;
  private String billToLastname;
  private String shipToFirstname;
  private String shipToLastname;
  private String cardNumber;
  private String expireDate;
  private String cardType;
  private ShipStatus shipStatus;
  private Locale locale;
  
  //  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  //  @JoinColumn(name = "orderId", nullable = false)
  //  @org.hibernate.annotations.IndexColumn(name = "lineItem_POSITION")
  private List<LineItem> lineItems = new ArrayList<LineItem> ();
  
  /**
   * Creates a new instance of Order
   */
  public Order() {
  }
  
  public Long getId() {
    return id;
  }
  
  public int getVersion() {
    return version;
  }
  
  public Account getUser() {
    return user;
  }
  
  public Date getOrderDate() {
    return orderDate;
  }
  
  public Address getShipAddr() {
    return shipAddr;
  }
  
  public void setShipAddr(Address shipAddr) {
    this.shipAddr = shipAddr;
  }
  
  public Address getBillAddr() {
    return billAddr;
  }
  
  public void setBillAddr(Address billAddr) {
    this.billAddr = billAddr;
  }
  
  public BigDecimal getTotalPrice() {
    return totalPrice;
  }
  
  public void setTotalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
  }
  
  public String getCourier() {
    return courier;
  }
  
  public void setCourier(String courier) {
    this.courier = courier;
  }
  
  public String getBillToFirstname() {
    return billToFirstname;
  }
  
  public void setBillToFirstname(String billToFirstname) {
    this.billToFirstname = billToFirstname;
  }
  
  public String getBillToLastname() {
    return billToLastname;
  }
  
  public void setBillToLastname(String billToLastname) {
    this.billToLastname = billToLastname;
  }
  
  public String getShipToFirstname() {
    return shipToFirstname;
  }
  
  public void setShipToFirstname(String shipToFirstname) {
    this.shipToFirstname = shipToFirstname;
  }
  
  public String getShipToLastname() {
    return shipToLastname;
  }
  
  public void setShipToLastname(String shipToLastname) {
    this.shipToLastname = shipToLastname;
  }
  
  public String getCardNumber() {
    return cardNumber;
  }
  
  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }
  
  public String getExpireDate() {
    return expireDate;
  }
  
  public void setExpireDate(String expireDate) {
    this.expireDate = expireDate;
  }
  
  public String getCardType() {
    return cardType;
  }
  
  public void setCardType(String cardType) {
    this.cardType = cardType;
  }
  
  
  public ShipStatus getShipStatus() {
    return shipStatus;
  }
  
  public void setShipStatus(ShipStatus shipStatus) {
    this.shipStatus = shipStatus;
  }
  
  public Locale getLocale() {
    return locale;
  }
  
  public void setLocale(Locale locale) {
    this.locale = locale;
  }
  
  public List<LineItem> getLineItems() {
    return lineItems;
  }
  
  public void setLineItems(List<LineItem> lineItems) {
    this.lineItems = lineItems;
  }
  
  public void addLineItem(CartItem cartItem) {
    LineItem lineItem = new LineItem(getLineItems().size() + 1, cartItem);
    addLineItem(lineItem);
  }
  
  public void addLineItem(LineItem lineItem) {
    if (lineItem == null) {
      throw new IllegalArgumentException("Can't add a null lineItem.");
    }
    
    getLineItems().add(lineItem);
  }
  
  // scafflod code
//  public void addLineItem(LineItem lineItem) {
//    if (lineItem == null)
//      throw new IllegalArgumentException("Can't add a null lineItem.");
//    this.getLineItems().add(lineItem);
//    // Don't have to set the "other" side,
//    // LineItem can only be instantiated with an Order given
//  }
  
  // -- Business methods
  
  public void initOrder(Account account, Cart cart) {
    // 设置帐单对应的客户信息
    user = account;
    orderDate = new Date();
    
    // 客户的地址信息初始化帐单地址信息
    Address ua = user.getUserAddr();
    
    // 必须使用拷贝构造函数，否则实例之间的值会相互影响
    // 即页面billAddr里的值会影响 shipAddr和 userAddr 的值
    shipAddr = new Address(ua);
    billAddr = new Address(ua);
    
    // 几个其它信息
    shipToFirstname = user.getFirstname();
    shipToLastname = user.getLastname();
    billToFirstname = user.getFirstname();
    billToLastname = user.getLastname();
    
    totalPrice = cart.getSubTotal();
    
    cardNumber = "999 9999 9999 9999";
    expireDate = "12/2008";
    cardType = "Visa";
    setCourier("UPS");
    setLocale(Locale.CANADA); // CA
    shipStatus = ShipStatus.PENDING;
    
    Iterator i = cart.getAllCartItems();
    while (i.hasNext()) {
      CartItem cartItem = (CartItem) i.next();
      addLineItem(cartItem);
    }
  }
  // --
  
  // -- common methods
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    
    if (!(o instanceof Order)) {
      return false;
    }
    
    final Order order = (Order) o;
    if (!(this.getTotalPrice().equals(order.getTotalPrice()))) return false;
    return this.getOrderDate().getTime() == order.getOrderDate().getTime();
  }
  
  public int hashCode() {
    return getOrderDate().hashCode();
  }
  
  public String toString() {
    return  "Order (" + getId().toString() + "), " +
            "Order Date: " + getOrderDate() + " " +
            "Total Price: " + getTotalPrice();
  }
  
  public int compareTo(Object o) {
    if (o instanceof Order) {
      return Long.valueOf(this.getOrderDate().getTime()).compareTo(
              Long.valueOf(((Order)o).getOrderDate().getTime()));
    }
    
    return 0;
  }
// --
}
