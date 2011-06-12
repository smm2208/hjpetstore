/*
 * Account.java
 *
 * Created on 2006年10月1日, 下午7:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.springframework.samples.jpetstore.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * domain 的语义发生了改变，userId 成了 username，而 userId 成了
 * 由 hibernate 自动维护的 id 值，因此原先的 userId 现在与 username 同.
 * 且将原先的 Profile, Singon 都合并到此表中了。
 *
 * @author pprun
 */
public class Account implements java.io.Serializable ,Comparable {
  private Long id;
  private int version;
  private String username;
  private String password;
  private String firstname;
  private String lastname;
  private String email;
  private String status;
  private Address userAddr;
  private String phone;
  private String langPreference;
  private Category favCategory;
  private boolean displayMylist;
  private boolean displayBanner;
  
  // @OneToMany(mappedBy = "buyer")
  private Set<Order> orders = new HashSet<Order>();
  
  /**
   * Creates a new instance of Account
   */
  public Account() {
  }
  
  public Long getId() {
    return id;
  }
  
  public int getVersion() {
    return version;
  }
  
  public String getUsername() {
    return username;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }
  
  public String getPassword() {
    return password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public String getFirstname() {
    return firstname;
  }
  
  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }
  
  public String getLastname() {
    return lastname;
  }
  
  public void setLastname(String lastname) {
    this.lastname = lastname;
  }
  
  public String getEmail() {
    return email;
  }
  
  public void setEmail(String email) {
    this.email = email;
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
  
  public String getLangPreference() {
    return langPreference;
  }
  
  public void setLangPreference(String langPreference) {
    this.langPreference = langPreference;
  }
  
  public Category getFavCategory() {
    return favCategory;
  }
  
  public void setFavCategory(Category favCategory) {
    this.favCategory = favCategory;
  }
  
  public boolean isDisplayMylist() {
    return displayMylist;
  }
  
  public void setDisplayMylist(boolean displayMylist) {
    this.displayMylist = displayMylist;
  }
  
  public boolean isDisplayBanner() {
    return displayBanner;
  }
  
  public void setDisplayBanner(boolean displayBanner) {
    this.displayBanner = displayBanner;
  }
  
  public Address getUserAddr() {
    return userAddr;
  }
  
  public void setUserAddr(Address userAddr) {
    this.userAddr = userAddr;
  }

  public Set getOrders() {
    return orders;
  }
  
  public void setOrders(Set<Order> orders) {
    this.orders = orders;
  }
  
  // scaffold code for collection field
  public void addOrder(Order order) {
    if (order == null)
      throw new IllegalArgumentException("Can't add a null Order.");
    this.getOrders().add(order);
  }
  
  // -- common methods
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    
    if (!(o instanceof Account)) {
      return false;
    }
    
    final Account user = (Account) o;
    return getUsername().equals(user.getUsername());
  }
  
  public int hashCode() {
    return getUsername().hashCode();
  }
  
  public String toString() {
    return  "User (" + getId() + "), Username: " + getUsername();
  }
  
  public int compareTo(Object o) {
    if (o instanceof Account) {
      return getUsername().compareTo(((Account)o).getUsername());
    }
    
    return 0;
  }
  // --
}
