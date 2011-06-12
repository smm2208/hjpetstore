/*
 * LineItem.java
 *
 * Created on 2006年10月1日, 下午10:32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.springframework.samples.jpetstore.domain;

//import com.sun.org.apache.bcel.internal.verifier.statics.LONG_Upper;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author pprun
 */
public class LineItem  implements java.io.Serializable, Comparable {
  // -- 尽量不要使用复合键，将复杂化 实体关系 映射 -- //
//  public static class Id implements Serializable {
//    // @Column(name = "orderId")
//    private Long orderId;
//    // @Column(name = "lineNumber")
//    private Integer lineNumber;
//
//    public Id() {}
//
//    public Id(Long orderId, Integer lineNumber) {
//      this.orderId = orderId;
//      this.lineNumber = lineNumber;
//    }
//
//    public boolean equals(Object o) {
//      if (o instanceof Id) {
//        Id that = (Id)o;
//        return this.orderId.equals(that.orderId) &&
//                this.lineNumber.equals(that.lineNumber);
//      } else {
//        return false;
//      }
//    }
//
//    public int hashCode() {
//      return orderId.hashCode() + lineNumber.hashCode();
//    }
//
//    public String toString() {
//      return orderId.toString() + lineNumber.toString();
//    }
//  }
  
  private Long id;
  private int version;
  
  // @ManyToOne
  // @JoinColumn(name="orderId", nullable = false, insertable = false, updatable = false)
  private Order order;
  
  private Integer lineNumber;
  
  // @ManyToOne
  // @JoinColumn(name="itemId", insertable = false, updatable = false)
  private Item item;
  
  private int quantity;
  
  // 非此 domain 对象的成员，在数据层被映射到 item 的 listPrice
  //BigDecimal unitprice;
  
  /** Creates a new instance of LineItem */
  public LineItem() {
  }
  
  public LineItem(int lineNumber, CartItem cartItem) {
    this.lineNumber = lineNumber;
    this.quantity = cartItem.getQuantity();
    //this.id = cartItem.getItem().getItemId();
    //this.unitPrice = cartItem.getItem().getListPrice();
    this.item = cartItem.getItem();
  }
  
  public Long getId() {
    return id;
  }
  
  public int getVersion() {
    return version;
  }
  
  public Order getOrder() {
    return order;
  }
  
  public Integer getLineNumber() {
    return lineNumber;
  }
  
  public Item getItem() {
    return item;
  }
  
  public int getQuantity() {
    return quantity;
  }
  
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
  
  /**
   * 非此 domain 对象的成员，在数据层被映射到 item 中
   * 此方法在此，仅为了供页面 (ViewOrder.jsp）中 ${lineItem.unitPrice} 调用.
   */
  public BigDecimal getUnitPrice() {
    return item.getListPrice();
  }
  
  /**
   * 非此 domain 对象的成员，在数据层被映射到 item 中
   */
//  public void setUnitPrice(BigDecimal unitprice) {
//    this.unitPrice = unitprice;
//  }
  
  
  public BigDecimal getTotalPrice() {
    // 单价 X 数量
    return this.getUnitPrice().multiply(BigDecimal.valueOf(quantity));
  }
  
  // -- common methods
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    
    if (!(o instanceof LineItem)) {
      return false;
    }
    
    final LineItem lineItem = (LineItem) o;
    return getId().equals(lineItem.getId());
  }
  
  public int hashCode() {
    int result;
    result = this.getId().hashCode();
    result = 31 * result + quantity;
    
    return result;
  }
  
  public String toString() {
    return "LineItem (" + getId().toString() + "), " +
            "Quantity: " + getQuantity();
  }
  
  public int compareTo(Object o) {
    if (o instanceof LineItem) {
      Integer lineNumber = getLineNumber();
      
      Integer thatLineNumber = ((LineItem)o).getLineNumber();
      
      return lineNumber.compareTo(thatLineNumber);
    }
    
    return 0;
  }
  // --
}
