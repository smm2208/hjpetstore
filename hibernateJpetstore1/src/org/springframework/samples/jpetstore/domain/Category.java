/*
 * Category.java
 *
 * Created on 2006年10月1日, 下午8:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.springframework.samples.jpetstore.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * domain 的语义发生了改变，categoryId 不再为与 categoryName 重复的列，而是
 * 由 hibernate 自动维护的值，因此原先的 categoryId 现在与 categoryName 同.
 * @author pprun
 */
public class Category implements java.io.Serializable, Comparable {
  private Long id;
  private int version;
  private String categoryName;
  private String categoryDesc;
  
  // @OneToMany(mappedBy = "product")
  private Set<Product> products = new HashSet<Product>();
  
  /** Creates a new instance of Category */
  public Category() {
  }
  
  public Long getId() {
    return id;
  }
  
  public int getVersion() {
    return version;
  }
  
  public String getCategoryName() {
    return categoryName;
  }
  
  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }
  
  public String getCategoryDesc() {
    return categoryDesc;
  }
  
  public void setCategoryDesc(String categoryDesc) {
    this.categoryDesc = categoryDesc;
  }
  
  public Set<Product> getProducts() {
    return products;
  }
  
  public void setProducts(Set<Product> products) {
    this.products = products;
  }
  
  // scaffold code for collection field
  public void addItem(Product product) {
    if (product == null)
      throw new IllegalArgumentException("Can't add a null product.");
    this.getProducts().add(product);
  }
  
  // -- common methods
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    
    if (!(o instanceof Category)) {
      return false;
    }
    
    final Category category = (Category) o;
    return getCategoryName().equals(category.getCategoryName());
  }
  
  public int hashCode() {
    return getCategoryName().hashCode();
  }
  
  public String toString() {
    return  getCategoryName();
  }
  
  public int compareTo(Object o) {
    if (o instanceof Category) {
      return this.getCategoryName().compareTo(((Category)o).getCategoryName());
    }
    
    return 0;
  }
  // --
}
