package org.springframework.samples.jpetstore.dao.hibernate;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.samples.jpetstore.dao.OrderDao;
import org.springframework.samples.jpetstore.domain.Order;

public class HibernateOrderDao extends HibernateDaoSupport implements OrderDao {
  
  public List getOrdersByUsername(String username) throws DataAccessException {
    return getHibernateTemplate().find(
            "select order from Order order, Account user where " +
            "order.user.id = user.id and user.username = ?", username);
  }
  
  /**
   * 改变 DAO 接口，
   * 为配合ViewOrderAction 中 order.getUser().getUsername() 的需要，但
   * 又避免使用 open session in view 模式
   */
  public List getOrderAndUserName(long id) throws DataAccessException {
    Long orderId = new Long(id);
    
    List list =  getHibernateTemplate().find(
            "select o, o.user.username " +
            "from Order o " +
            "where o.id = ?", orderId);
    
    //Order order = (Order) getHibernateTemplate().load(Order.class, parameterObject);
    
    
    // todo: pp 在映射文件中设置了：fetch="join" lazy="false"，还需要下面这样做吗？
    // 答：是的不需要。
//        if (order != null) {
//            order.setLineItems(getHibernateTemplate().find(
//                    "from LineItem lineItem where lineItem.order = ?",
//                    new Long(order.getId())));
//        }
    return list;
  }
  
  public Order getOrder(int id) throws DataAccessException {
    Long parameterObject = new Long(id);
    Order order = (Order) getHibernateTemplate().load(Order.class, parameterObject);
    
    System.out.println(">>>>>>>>>>" + order);
    return order;
  }
  
  public void insertOrder(Order order) throws DataAccessException {
    // Hibernate do it for us
    // order.setId(this.sequenceDao.getNextId("ordernum"));
    
    getHibernateTemplate().persist(order);
    
    // 因为原版中定单状态的值放在另一个表中
    // getHibernateTemplate().insert("insertOrderStatus", order);
    
    // todo: PP 在映射文件中设置了：cascade="all-delete-orphan"，还需要下面这样做吗？
    // 答：是的，不需要，因为它保证了 LineItem 在 Order 的 update, insert, save
    // 的同时进行持久化操作.
//    for (int i = 0; i < order.getLineItems().size(); i++) {
//      LineItem lineItem = (LineItem) order.getLineItems().get(i);
//      // todo: pp 需要在 Order 中把它完全初始化
//      // lineItem.setOrder(order);
//      getHibernateTemplate().persist(lineItem);
//    }
  }
  
}
