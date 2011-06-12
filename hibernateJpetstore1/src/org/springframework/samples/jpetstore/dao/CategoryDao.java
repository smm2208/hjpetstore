package org.springframework.samples.jpetstore.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.jpetstore.domain.Category;


/**
 * 由于和原版的 domain 对象的不同，所以重新定义了一个接口.
 */
public interface CategoryDao {
  
  List getCategoryList() throws DataAccessException;
  
  Category getCategory(String categoryName) throws DataAccessException;
  
}
