package org.springframework.samples.jpetstore.domain.logic;

import java.util.List;

import org.springframework.samples.jpetstore.dao.AccountDao;
import org.springframework.samples.jpetstore.dao.CategoryDao;
import org.springframework.samples.jpetstore.dao.ItemDao;
import org.springframework.samples.jpetstore.dao.OrderDao;
import org.springframework.samples.jpetstore.dao.ProductDao;
import org.springframework.samples.jpetstore.domain.Account;
import org.springframework.samples.jpetstore.domain.Category;
import org.springframework.samples.jpetstore.domain.Item;
import org.springframework.samples.jpetstore.domain.Order;

/**
 * JPetStore primary business object.
 *
 * <p>This object makes use of five DAO objects, decoupling it
 * from the details of working with persistence APIs. Thus
 * although this application uses iBATIS for data access,
 * another persistence tool could be dropped in without
 * breaking this class.
 *
 * <p>The DAOs are made available to the instance of this object
 * using Dependency Injection. (The DAOs are in turn configured
 * using Dependency Injection.) We use Setter Injection here,
 * exposing JavaBean setter methods for each DAO. This means there is
 * a JavaBean "property" for each DAO. In this case the properties
 * are write-only: there is no getter method to accompany the
 * setter methods. Getter methods are optional: implement them
 * only if you want to expose access to the properties in your
 * business object.
 *
 * <p>There is one instance of this class in the JPetStore application.
 * In Spring terminology, it is a "singleton". This means a
 * per-Application Context singleton. The factory creates a single
 * instance; there is no need for a private constructor, static
 * factory method etc as in the traditional implementation of
 * the Singleton Design Pattern.
 *
 * <p>This is a POJO. It does not depend on any Spring APIs.
 * It's usable outside a Spring container, and can be instantiated
 * using new in a JUnit test. However, we can still apply declarative
 * transaction management to it using Spring AOP.
 *
 * <p>This class defines a default transaction attribute for all methods.
 * Note that this attribute definition is only necessary if using Commons
 * Attributes auto-proxying (see the "attributes" directory under the root of
 * JPetStore). No attributes are required with a TransactionFactoryProxyBean,
 * as in the default applicationContext.xml in the war/WEB-INF directory.
 *
 * <p>The following attribute definition uses Commons Attributes attribute syntax.
 * @@org.springframework.transaction.interceptor.DefaultTransactionAttribute()
 *
 * @author Juergen Hoeller
 * @since 30.11.2003
 */
public class PetStoreImpl implements PetStoreFacade, OrderService {
  
  private AccountDao accountDao;
  
  private CategoryDao categoryDao;
  
  private ProductDao productDao;
  
  private ItemDao itemDao;
  
  private OrderDao orderDao;
  
  //-------------------------------------------------------------------------
  // Setter methods for dependency injection
  //-------------------------------------------------------------------------
  
  public void setAccountDao(AccountDao accountDao) {
    this.accountDao = accountDao;
  }
  
  public void setCategoryDao(CategoryDao categoryDao) {
    this.categoryDao = categoryDao;
  }
  
  public void setProductDao(ProductDao productDao) {
    this.productDao = productDao;
  }
  
  public void setItemDao(ItemDao itemDao) {
    this.itemDao = itemDao;
  }
  
  public void setOrderDao(OrderDao orderDao) {
    this.orderDao = orderDao;
  }
  
  
  //-------------------------------------------------------------------------
  // Operation methods, implementing the PetStoreFacade interface
  //-------------------------------------------------------------------------
  
  public Account getAccount(String username) {
    return this.accountDao.getAccount(username);
  }
  
  public List getAccount(String username, String password) {
    return this.accountDao.getAccount(username, password);
  }
  
  // added by pprun
  public String getBannerNameForUser(String username) {
    return this.accountDao.getBannerNameForUser(username);
  }
  
  public void insertAccount(Account account) {
    this.accountDao.insertAccount(account);
  }
  
  public void updateAccount(Account account) {
    this.accountDao.updateAccount(account);
  }
  
  public List getUsernameList() {
    return this.accountDao.getUsernameList();
  }
  
  public List getCategoryList() {
    return this.categoryDao.getCategoryList();
  }
  
  public Category getCategory(String categoryName) {
    return this.categoryDao.getCategory(categoryName);
  }
  
  public List getProductListByCategory(String categoryName) {
    return this.productDao.getProductListByCategory(categoryName);
  }
  
  public List searchProductList(String keywords) {
    return this.productDao.searchProductList(keywords);
  }
  
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
  public List getProduct(String productNumber) {
    return this.productDao.getProduct(productNumber);
  }
  
  public List getItemListByProduct(String productNumber) {
    return this.itemDao.getItemListByProduct(productNumber);
  }
  
  public List getItem(String itemName) {
    return this.itemDao.getItem(itemName);
  }
  
  // added by pprun
  public int getItemQuantity(String itemName) {
    return this.itemDao.getItemQuantity(itemName);
  }
  
  public List getItem(Item item) {
    return this.itemDao.getItem(item);
  }
  
  public boolean isItemInStock(String itemName) {
    return this.itemDao.isItemInStock(itemName);
  }
  
  public void insertOrder(Order order) {
    this.orderDao.insertOrder(order);
    this.itemDao.updateQuantity(order);
  }
  
  public Order getOrder(int id) {
    return this.orderDao.getOrder(id);
  }
  
  public List getOrderAndUserName(long id) {
    return this.orderDao.getOrderAndUserName(id);
  }
  
  public List getOrdersByUsername(String username) {
    return this.orderDao.getOrdersByUsername(username);
  }
  
}
