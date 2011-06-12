/*
 * Banner.java
 *
 * Created on 2006年10月1日, 下午8:00
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.springframework.samples.jpetstore.domain;

/**
 *
 * @author pprun
 */
public class Banner implements java.io.Serializable, Comparable {
  private Long id;
  private int version;
  private Category favCategory;
  private String bannerName;
  
  /** Creates a new instance of Banner */
  public Banner() {
  }
  
  public Long getId() {
    return id;
  }
  
  public int getVersion() {
    return version;
  }
  
  public Category getFavCategory() {
    return favCategory;
  }
  
  public void setFavCategory(Category favCategory) {
    this.favCategory = favCategory;
  }
  
  public String getBannerName() {
    return bannerName;
  }
  
  public void setBannerName(String bannerName) {
    this.bannerName = bannerName;
  }
  
  // -- Common Methods
  
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Banner)) return false;
    
    final Banner banner = (Banner) o;
    
    if (!getFavCategory().equals(banner.getFavCategory())) return false;
    if (!getBannerName().equals(banner.getBannerName())) return false;
    
    return true;
  }
  
  public int hashCode() {
    return getBannerName().hashCode();
  }
  
  public String toString() {
    return "Banner: " + getFavCategory().toString() + ", " +
            "Banner Name: " + getBannerName();
  }
  
  public int compareTo(Object o) {
    if (o instanceof Banner) {
      return getBannerName().compareTo(((Banner)o).getBannerName());
    }
    
    return 0;
  }
  // --
}
