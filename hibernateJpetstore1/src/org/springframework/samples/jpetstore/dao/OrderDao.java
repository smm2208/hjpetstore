package org.springframework.samples.jpetstore.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.jpetstore.domain.Order;

/**
 * 由于和原版的 domain 对象的不同，所以重新定义了一个接口.
 */
public interface OrderDao {

  List getOrdersByUsername(String username) throws DataAccessException;

  List getOrderAndUserName(long id) throws DataAccessException;
  Order getOrder(int id) throws DataAccessException;
  void insertOrder(Order order) throws DataAccessException;

}
