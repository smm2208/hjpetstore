package org.springframework.samples.jpetstore.dao.hibernate;

import java.util.List;
import org.hibernate.LockMode;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.samples.jpetstore.dao.AccountDao;
import org.springframework.samples.jpetstore.domain.Account;

/**
 * In this and other DAOs in this package, a DataSource property
 * is inherited from the HibernateDaoSupport convenience superclass
 * supplied by Spring.
 * DAOs don't need to extend such superclasses, but it saves coding in many
 * cases.
 * There are analogous superclasses for
 * JDBC (JdbcDaoSupport),
 * Hibernate (HibernateDaoSupport),
 * JDO (JdoDaoSupport) etc.
 *
 * <p>This and other DAOs are configured using Dependency Injection.
 * This means, for example, that Spring can source the DataSource
 * from a local class, such as the Commons DBCP BasicDataSource,
 * or from JNDI, concealing the JNDI lookup from application code.
 *
 * domain 的语义发生了改变，userId 成了 username，而 userId 成了
 * 由 hibernate 自动维护的 id 值，因此原先的 userId 现在与 username 同.
 * 且将原先的 Profile, Singon 都合并到此表中了。
 *
 * @author Pprun
 * @author Juergen Hoeller
 * @author Colin Sampaleanu
 */
public class HibernateAccountDao extends HibernateDaoSupport implements AccountDao {
//  public static final String getAccountByUsername =
//          "select username, email, firstname, lastname, status, " +
//          "addr1, addr2, city, state, zipcode, country, phone, langPreference, " +
//          "favcategory, mylistopt, banneropt, " +
//          "bannerdata.bannername " +
//          "from Account user, Banner banner " +
//          "where user.username = :username and " +
//          "and user.favcategory = banner.favcategory";
  
  public Account getAccount(String username) throws DataAccessException {
    Account account = null;
    List ls = getHibernateTemplate().find(
            "select user " +
            "from Account user, Banner banner " +
            "where user.favCategory.categoryName = banner.favCategory.categoryName " +
            "and user.username = ?", username);
    if (ls != null && ls.size() > 0) {
      account =  (Account) ls.get(0);
    }
    
    return account;
  }
  
  public List getAccount(String username, String password) throws DataAccessException {
    String[] paramsName = {"username", "password"};
    String[] paramsVal = {username, password};
    
    Account account = null;
    List ls = getHibernateTemplate().findByNamedParam(
            "select user, user.favCategory.categoryName from Account user " +
            "where user.username = :username and user.password = :password",
            paramsName, paramsVal);
    
    return ls;
  }
  
  /**
   * added by pprun
   */
  public String getBannerNameForUser(String username) throws DataAccessException {
    String bannerName = null;
    
    List ls = getHibernateTemplate().find(
            "select banner.bannerName " +
            "from Account user, Banner banner " +
            "where user.favCategory.id = banner.favCategory.id and " +
            "user.username = ?", username);
    
    if (ls != null && ls.size() > 0) {
      bannerName =  (String) ls.get(0);
    }
    
    return bannerName;
  }
  
  public void insertAccount(Account account) throws DataAccessException {
    // todo , 它的favCategory 怎么办？ 在控制器前端维护
    // getHibernateTemplate().merge(account); // 的语义有歧义
    getHibernateTemplate().persist(account);
  }
  
  public void updateAccount(Account account) throws DataAccessException {
    if (account.getPassword() != null && account.getPassword().length() > 0) {
      // todo review it
      getHibernateTemplate().update(account, LockMode.UPGRADE);
    }
  }
  
  public List getUsernameList() throws DataAccessException {
    return getHibernateTemplate().find("select user.username from Account user");
  }
  
}
