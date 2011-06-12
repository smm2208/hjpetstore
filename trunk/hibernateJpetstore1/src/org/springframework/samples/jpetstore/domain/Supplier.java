/*
 * Supplier.java
 *
 * Created on 2006年10月1日, 下午7:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.springframework.samples.jpetstore.domain;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author pprun
 */
public class Supplier implements java.io.Serializable , Comparable{
  private Long id;
  private int version;
  private String supplierName;
  private String status;
  private Address supplierAddr;
  private String phone;
  
  // @OneToMany(mappedBy = "supplier")
  private Set<Item> items = new HashSet<Item>();
  
  /** Creates a new instance of Supplier */
  public Supplier() {
  }
  
  public Long getId() {
    return id;
  }
  
  public int getVersion() {
    return version;
  }
  
  public String getSupplierName() {
    return supplierName;
  }
  
  public void setSupplierName(String supplierName) {
    this.supplierName = supplierName;
  }
  
  public String getStatus() {
    return status;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
  
  public String getPhone() {
    return phone;
  }
  
  public void setPhone(String phone) {
    this.phone = phone;
  }
  
  public Address getSupplierAddr() {
    return supplierAddr;
  }
  
  public void setSupplierAddr(Address supplierAddr) {
    this.supplierAddr = supplierAddr;
  }
  
  public Set getItems() {
    return items;
  }
  
  public void setItems(Set<Item> items) {
    this.items = items;
  }
  
  // scaffold code for collection field
  public void addItem(Item item) {
    if (item == null)
      throw new IllegalArgumentException("Can't add a null Item.");
    this.getItems().add(item);
  }
  
  // -- common methods
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    
    if (!(o instanceof Supplier)) {
      return false;
    }
    
    final Supplier supplier = (Supplier) o;
    if(!getSupplierAddr().equals(supplier.getSupplierAddr())) return false;
    if(!getSupplierName().equals(supplier.getSupplierName())) return false;
    if (!(getPhone().equals(supplier.getPhone()))) return false;
    
    return true;
  }
  
  public int hashCode() {
    int result;
    result = getSupplierAddr().hashCode();
    result = 31 * result + getSupplierName().hashCode();
    result = 31 * result + getPhone().hashCode();
    return result;
  }
  
  public String toString() {
    return  "Supplier (" + getId() + "), " +
            "Name: " + getSupplierName() + " " +
            "Phone: " + getPhone() + " " +
            "Address: " + getSupplierAddr();
  }
  
  public int compareTo(Object o) {
    if (o instanceof Supplier) {
      return getSupplierName().compareTo(((Supplier)o).getSupplierName());
    }
    
    return 0;
  }
// --
}
