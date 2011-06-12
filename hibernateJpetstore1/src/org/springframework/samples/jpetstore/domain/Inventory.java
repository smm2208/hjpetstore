/*
 * Inventory.java
 *
 * Created on 2006年10月1日, 下午10:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.springframework.samples.jpetstore.domain;

import java.util.Date;

/**
 *
 * @author pprun
 */
public class Inventory implements java.io.Serializable, Comparable {
  private Long id;
  private int version;
  
  // @OneToOne
  // @PrimaryKeyJoinColumn
  private Item item;
  
  /**
   * 原版并没有此项，为了更好的建模，加上它。
   *
   * @Column(name = "ADDED_ON")
   */
  private Date dateAdded = new Date();
  
  private int quantity;
  
  /**
   * Creates a new instance of Inventory
   */
  public Inventory() {
  }
  
  public Long getId() {
    return id;
  }
  
  public int getVersion() {
    return version;
  }
  
  public Item getItem() {
    return item;
  }
  
  public void setItem(Item item) {
    this.item = item;
  }
  
  public Date getDateAdded() {
    return dateAdded;
  }
  
  // todo: remove it after populated.
//  public void setDateAdded(Date dateAdded) {
//    this.dateAdded = dateAdded;
//  }
  
  public int getQuantity() {
    return quantity;
  }
  
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
  
  // -- common methods
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    
    if (!(o instanceof Category)) {
      return false;
    }
    
    final Inventory inventory = (Inventory) o;
    return dateAdded.getTime() == inventory.dateAdded.getTime();
  }
  
  public int hashCode() {
    return dateAdded.hashCode();
  }
  
  public String toString() {
    return "Inventory, (" + getId() + ")" +
            "Item: " + getItem().getItemName() + " " +
            "Added date: " + getDateAdded();
  }
  
  public int compareTo(Object o) {
    if (o instanceof Inventory) {
      return Long.valueOf(this.getDateAdded().getTime()).
              compareTo(Long.valueOf(((Inventory)o).getDateAdded().getTime()));
    }
    
    return 0;
  }
  // --
}
