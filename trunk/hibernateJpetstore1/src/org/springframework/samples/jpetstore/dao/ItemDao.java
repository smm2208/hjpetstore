package org.springframework.samples.jpetstore.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.jpetstore.domain.Item;
import org.springframework.samples.jpetstore.domain.Order;

/**
 * 由于和原版的 domain 对象的不同，所以重新定义了一个接口.
 */
public interface ItemDao {
  
  public void updateQuantity(Order order) throws DataAccessException;
  
  boolean isItemInStock(String itemName) throws DataAccessException;
  
  List getItemListByProduct(String productNumber) throws DataAccessException;
  
  List getItem(String itemName) throws DataAccessException;
  
  // added by pprun
  List getItem(Item item) throws DataAccessException;
  int getItemQuantity(String itemName) throws DataAccessException;
}
