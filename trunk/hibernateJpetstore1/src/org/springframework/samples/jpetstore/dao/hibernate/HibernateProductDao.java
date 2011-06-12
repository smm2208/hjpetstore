package org.springframework.samples.jpetstore.dao.hibernate;

import java.util.List;
import java.util.Scanner;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.samples.jpetstore.dao.ProductDao;

/**
 * domain 的语义发生了改变，productId 成了 productNumber，而 productId 成了
 * 由 hibernate 自动维护的 id 值，因此原先的 productId 现在与 productNumber 同.
 */
public class HibernateProductDao extends HibernateDaoSupport implements ProductDao {
  
  public List getProductListByCategory(String categoryName) throws DataAccessException {
    return getHibernateTemplate().find(
            "from Product product where product.category.categoryName = ?", categoryName);
  }
  
  /**
   * 改变 DAO 的接口，返回一个List，包含 product  和 categoryName
   * // 为解决著名的: org.hibernate.LazyInitializationException:
   * //               could not initialize proxy - the owning Session was closed
   * // (由页面 Product.jsp 中 ${product.category.categoryName} 引起).
   * //
   * // 虽然采用 "Open Session in View pattern" 模式, 对应 Spring 中即：
   * // @see org.springframework.orm.hibernate3.support.OpenSessionInViewFilter
   * // @see org.springframework.orm.hibernate3.support.OpenSessionInViewInterceptor
   * // 但其并不是高吞吐量应用的首选方案
   * // 因此这里要把 many-to-one 关系的一端加入到 request attribute, 以便页面可
   * // 访问.
   * todo: pp renamed productId to productNumber
   */
  public List getProduct(String productNumber) throws DataAccessException {
    return getHibernateTemplate().find(
            "select p , c.categoryName " +
            "from Product p, Category c " +
            "where c.id = p.category.id and p.productNumber = ?", productNumber);
    
//            "select p , c.categoryName " +
//            "from Product p inner join Category c " +
//            "where c.id = p.category.id and p.productNumber = ?", productNumber)
//            .get(0);
  }

  /**
   * 去掉原版中 iBatis 方便映射的内部类，取而代之手工组装 like '%' 的
   * 匹配的字符串.
   */
  public List searchProductList(String keywords) throws DataAccessException {
    // 组装查询 like(%) 字符串
    StringBuilder likeString = new StringBuilder();
    
    Scanner s = new Scanner(keywords);
    while (s.hasNext()) {
      String keyword = s.next().toUpperCase();
      likeString.append("upper(p.productName) like '%" + keyword + "%'");
      likeString.append(" OR upper(p.productDesc) like '%" + keyword + "%'");
      likeString.append(" OR upper(c.categoryName) like '%" + keyword + "%'");
    }
    
    return getHibernateTemplate().find(
            // distinct
            "select distinct p " +
            "from Product p, Category c " +
            "where c.id = p.category.id and ( " + likeString.toString() + " )");
  }
  
  
  /**
   * 内部类，从输入的搜索关键字，得到查询字符串.
   * not used in hibernate
   */
//  public static class ProductSearch {
//
//    private List keywordList = new ArrayList();
//
//    public ProductSearch(String keywords) {
//      StringTokenizer splitter = new StringTokenizer(keywords, " ", false);
//      while (splitter.hasMoreTokens()) {
//        this.keywordList.add("%" + splitter.nextToken() + "%");
//      }
//    }
//
//    public List getKeywordList() {
//      return keywordList;
//    }
//  }
  
}
