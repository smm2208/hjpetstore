package org.springframework.samples.jpetstore.dao.hibernate;

import java.util.List;
import org.hibernate.LockMode;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.samples.jpetstore.dao.ItemDao;
import org.springframework.samples.jpetstore.domain.Inventory;
import org.springframework.samples.jpetstore.domain.Item;
import org.springframework.samples.jpetstore.domain.LineItem;
import org.springframework.samples.jpetstore.domain.Order;

/**
 * domain 的语义发生了改变，itemId 成了 itemName，而 itemId 成了
 * 由 hibernate 自动维护的 id 值，因此原先的 itemId 现在与 itemName 同.
 *
 */
public class HibernateItemDao extends HibernateDaoSupport implements ItemDao {
  
  public void updateQuantity(Order order) throws DataAccessException {
    for (int i = 0; i < order.getLineItems().size(); i++) {
      LineItem lineItem = (LineItem) order.getLineItems().get(i);
      //String itemId = lineItem.getItemId();
      //String itemName = lineItem.getItem().getItemName();
      Item item = lineItem.getItem();
      Integer increment = new Integer(lineItem.getQuantity());
      
      // 加载对应的 innventory
      List ls = getHibernateTemplate().find(
              "from Inventory inv where inv.item.itemName = ?",
              item.getItemName()); // 原先的itemId，现在为 itemName
      if (ls != null && ls.size() > 0) {
        Inventory inv = (Inventory) ls.get(0);
        
        // 将库存数减去给定的值
        inv.setQuantity(inv.getQuantity() - increment);
        getHibernateTemplate().saveOrUpdate(inv);
      }
    }
  }
  
  /**
   * todo: pp, renamed itemId to itemName
   */
  public boolean isItemInStock(String itemName) throws DataAccessException {
    boolean result = false;
    
    List ls = getHibernateTemplate().find(
            "select inv.quantity from Inventory inv where " +
            "inv.item.itemName = ?", itemName);
    if (ls != null && ls.size() > 0) {
      Integer i = (Integer) ls.get(0);
      
      result = (i != null && i.intValue() >0);
    }
    
//    List ls = getHibernateTemplate().find(
//             "from Item item where item.itemName = ?", itemName);
//    if (ls != null) {
//      Item item = (Item)ls.get(0);
//
//      ls = null;
//      // 加载对应的 innventory
//      ls = getHibernateTemplate().find(
//              "select ity.quantity from Inventory ity where ity.item = ?", item);
//      if (ls != null) {
//        Integer i = (Integer) ls.get(0);
//
//        result = (i != null && i.intValue() >0);
//      }
//    }
    
    return result;
  }
  
  /**
   * todo: pp, renamed productId to productNumber
   */
  public List getItemListByProduct(String productNumber) throws DataAccessException {
    List list =  getHibernateTemplate().find(
            "select item from Item item, Product product " +
            "where item.product.id = product.id " +
            "and product.productNumber = ?", productNumber);
    
    // 不需要这样的笨方法了，
    // 通过设置 log4j 's log4j.logger.org.hibernate.type=all' 效果很清楚
    //    System.out.println(">>>>>>>>>>>>>>>" + list.size());
    return list;
  }
  
  /**
   * 改变 DAO 接口，因为 view 需要 product
   *
   * 由于将原版本中的混合模型一分为二：
   * item 和 从 inventory 中得到 quantity
   * 所以在客户端调用时，除需要调用此方法外，
   * 还需要调用 getItemQuantity(String itemName)，以达到同样的逻辑.
   *
   * select
   * i.itemid, listprice, unitcost, supplier, i.productid, name,
   * descn, category, status, attr1, attr2, attr3, attr4, attr5, qty
   * from item i, inventory v, product p where p.productid = i.productid
   * and i.itemid = v.itemid and i.itemid = #value#
   *
   * todo: pp, renamed itemId to itemName
   */
  public List getItem(String itemName) throws DataAccessException {
    List ls = getHibernateTemplate().find(
            "select item, item.product " +
            "from Item item " +
            "where item.itemName = ?", itemName);
    
    return ls;
    
//            "select item.itemName, item.listPrice, item.unitCost, " +
//            "item.supplier.supplierName, item.product.productNumber, " +
//            "item.product.productName, item.product.productDesc, " +
//            "item.product.category.categoryName" +
//            "item.status, item.attr1, item.attr2, item.attr3, item.attr4," +
//            "item.attr5, inv.quantity " +
//            "from Item item" +
//            "where item.itemName = ?", itemName);
    
//    if (ls != null && ls.size() > 0) {
//      item =  (Item) ls.get(0);
//    }
//
//    return item;
  }
  
  /**
   * added by pprun:
   * 为避免使用 open session in view 模式，但又要配合 viewOrder.jsp 中:
   * lineItem.item.product.productName
   * lineItem.item.*
   * 表达式的访问.
   * 注意，在整个购物流程中，是不需要此方法的，因为在那个过程中 item 已经初始化
   * 好了。
   * 只是在查看用户信息时的 listOrder.jsp 中，点击相应的购物清单时
   * (viewOrder.jsp)才会使用.
   *
   */
  public List getItem(Item item) throws DataAccessException {
    // 付诸于实践： retrive detached entity
    // 因为 item 是通过上一次的 session 中获得的 LineItem 中得到的(detached)，
    // 所以需要重新关联到到当前 session
    getHibernateTemplate().lock(item, LockMode.NONE);
    List ls = getHibernateTemplate().find(
            "select item, item.product.productName " +
            "from Item item " +
            "where item.id = ?", item.getId());
    
    return ls;
  }
  
  /**
   * added by pprun
   * 由于将原版本中的混合模型一分为二：
   * item 和 从 inventory 中得到 quantity
   * 所以在客户端调用时，除需要调用getItem(String itemName)方法外，
   * 还需要调用此方法，以达到同样的逻辑.
   */
  public int getItemQuantity(String itemName) throws DataAccessException {
    int quantity = 0;
    
    List ls = getHibernateTemplate().find(
            "select inv.quantity from Item item, Inventory inv " +
            "where inv.item.id = item.id and item.itemName = ?", itemName);
    
    if (ls != null && ls.size() > 0) {
      Integer i = (Integer) ls.get(0);
      
      quantity =  i==null ? 0 : i.intValue();
    }
    
    return quantity;
  }
}
