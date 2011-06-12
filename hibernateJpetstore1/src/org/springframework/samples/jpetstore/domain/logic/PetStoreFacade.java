package org.springframework.samples.jpetstore.domain.logic;

import java.util.List;

import org.springframework.samples.jpetstore.domain.Account;
import org.springframework.samples.jpetstore.domain.Category;
import org.springframework.samples.jpetstore.domain.Item;
import org.springframework.samples.jpetstore.domain.Order;

/**
 * JPetStore primary business interface.
 *
 * @author Juergen Hoeller
 * @since 30.11.2003
 */
public interface PetStoreFacade {
  
    /**
     * 
     * @param username 
     * @return 
     */
  Account getAccount(String username);
  
  List getAccount(String username, String password);
  
  // added by pprun
  String getBannerNameForUser(String username);
  void insertAccount(Account account);
  
  void updateAccount(Account account);
  
  List getUsernameList();
  
  
  List getCategoryList();
  
  Category getCategory(String categoryName);
  
  
  List getProductListByCategory(String categoryName);
  
  List searchProductList(String keywords);
  
  // 改变 DAO 的接口，返回一个List，包含 product  和 categoryName
  // 为解决著名的: org.hibernate.LazyInitializationException:
  //               could not initialize proxy - the owning Session was closed
  // (由页面 Product.jsp 中 ${product.category.categoryName} 引起).
  //
  // 虽然采用 "Open Session in View pattern" 模式, 对应 Spring 中即：
  // @see org.springframework.orm.hibernate3.support.OpenSessionInViewFilter
  // @see org.springframework.orm.hibernate3.support.OpenSessionInViewInterceptor
  // 但其并不是高吞吐量应用的首选方案
  // 因此这里要把 many-to-one 关系的一端加入到 request attribute, 以便页面可
  // 访问.
  List getProduct(String productNumber);
  
  
  List getItemListByProduct(String productNumber);
  
  List getItem(String itemName);
  
  // added by pprun
  int getItemQuantity(String itemName);
  List getItem(Item item);
  boolean isItemInStock(String itemName);
  
  
  void insertOrder(Order order);
  
  Order getOrder(int id);
  List getOrderAndUserName(long id);
  List getOrdersByUsername(String username);
  
}
