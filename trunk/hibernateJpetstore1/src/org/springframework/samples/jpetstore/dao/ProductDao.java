package org.springframework.samples.jpetstore.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.jpetstore.domain.Product;

/**
 * 由于和原版的 domain 对象的不同，所以重新定义了一个接口.
 */
public interface ProductDao {
  
  List getProductListByCategory(String categoryName) throws DataAccessException;
  
  List searchProductList(String keywords) throws DataAccessException;
  
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
  List getProduct(String productNumber) throws DataAccessException;
  
}
