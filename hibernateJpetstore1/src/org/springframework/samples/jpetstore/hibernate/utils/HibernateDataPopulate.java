/*
 * HibernateDataPopulate.java
 *
 * Created on 2006年10月4日, 上午3:27
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.springframework.samples.jpetstore.hibernate.utils;

import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.samples.jpetstore.domain.Account;
import org.springframework.samples.jpetstore.domain.Address;
import org.springframework.samples.jpetstore.domain.Banner;
import org.springframework.samples.jpetstore.domain.Category;
import org.springframework.samples.jpetstore.domain.Inventory;
import org.springframework.samples.jpetstore.domain.Item;
import org.springframework.samples.jpetstore.domain.Product;
import org.springframework.samples.jpetstore.domain.Supplier;

/**
 * don't worked.
 *
 * @author pprun
 */
public class HibernateDataPopulate {
  private static Log log = LogFactory.getLog(HibernateDataPopulate.class);
  /** Creates a new instance of HibernateDataPopulate */
  public HibernateDataPopulate() {
  }
  
  public static void main(String[] args) {
    log.info("Start populate hjpetstore database data ...");
    Session session = null;
    try {
      // get session
      session = HibernateUtil.getSessionFactory().openSession();
      // begin transaction
      session.beginTransaction();
      
      // persist entity to db
      insertCategories(session);
      insertBanners(session);
      insertUsers(session);
      insertSuppliers(session);
      insertProducts(session);
      insertItems(session);
      insertInventories(session);
      
      // commit and end current transaction
      session.getTransaction().commit();
      log.info("Successfully populate hjpetstore database data!");
      System.out.println("Successfully populate hjpetstore database data!");
    } catch (Throwable t) {
      if (session.getTransaction() != null) {
        session.getTransaction().rollback();
      }
      log.info("Failly populate hjpetstore database data!");
      System.out.println("Failly populate hjpetstore database data!");
    } finally {
      if (session != null) session.close();
    }
  }
  
  private static void insertCategories(Session s) {
    log.info("populating Category ...");
    // 1
    Category c = new Category();
    c.setCategoryName("Fish");
    c.setCategoryDesc("<image src=\"../images/fish_icon.gif\"><font size=\"5\" color=\"blue\"> Fish</font>");
    
    s.persist(c);
    //s.flush();
    
    // 2
    c = null;
    c = new Category();
    c.setCategoryName("DogS");
    c.setCategoryDesc("<image src=\"../images/dogs_icon.gif\"><font size=\"5\" color=\"blue\"> Dogs</font>");
    
    s.persist(c);
    //s.flush();
    
    // 3
    c = null;
    c = new Category();
    c.setCategoryName("REPTILES");
    c.setCategoryDesc("<image src=\"../images/reptiles_icon.gif\"><font size=\"5\" color=\"blue\"> Reptiles</font>");
    
    s.persist(c);
    //s.flush();
    
    // 4
    c = null;
    c = new Category();
    c.setCategoryName("Cats");
    c.setCategoryDesc("<image src=\"../images/cats_icon.gif\"><font size=\"5\" color=\"blue\"> Cats</font>");
    
    s.persist(c);
    //s.flush();
    
    // 5
    c = null;
    c = new Category();
    c.setCategoryName("Birds");
    c.setCategoryDesc("<image src=\"../images/birds_icon.gif\"><font size=\"5\" color=\"blue\"> Birds</font>");
    
    s.persist(c);
    //s.flush();
    
    log.info("populating Category end.");
  }
  
  private static void insertBanners(Session s) {
    log.info("populating Banner ...");
    Category c1 = (Category)s.get(Category.class, Long.valueOf(1));
    Category c2 = (Category)s.get(Category.class, Long.valueOf(2));
    Category c3 = (Category)s.get(Category.class, Long.valueOf(3));
    Category c4 = (Category)s.get(Category.class, Long.valueOf(4));
    Category c5 = (Category)s.get(Category.class, Long.valueOf(5));
    
    // 1
    Banner b = new Banner();
    b.setFavCategory(c1);
    b.setBannerName("<image src=\"../images/banner_fish.gif\">");
    
    s.persist(b);
    //s.flush();
    
    // 2
    b = null;
    b = new Banner();
    b.setFavCategory(c4);
    b.setBannerName("<image src=\"../images/banner_cats.gif\">");
    
    s.persist(b);
    //s.flush();
    
    // 3
    b = null;
    b = new Banner();
    b.setFavCategory(c3);
    b.setBannerName("<image src=\"../images/banner_dogs.gif\">");
    
    s.persist(b);
    //s.flush();
    
    // 4
    b = null;
    b = new Banner();
    b.setFavCategory(c2);
    b.setBannerName("<image src=\"../images/banner_reptiles.gif\">");
    
    s.persist(b);
    //s.flush();
    
    // 5
    b = null;
    b = new Banner();
    b.setFavCategory(c5);
    b.setBannerName("<image src=\"../images/banner_birds.gif\">");
    
    s.persist(b);
    //s.flush();
    
    log.info("populating Banner end.");
  }
  
  private static void insertUsers(Session s) {
    log.info("populating Account ...");
    
    Address ad = new Address();
    ad.setAddr1("901 San Antonio Road");
    ad.setAddr2("MS UCUP02-206");
    ad.setCity("Palo Alto");
    ad.setState("CA");
    ad.setZipcode("94303");
    ad.setCountry("USA");
    
    Category c1 = (Category)s.get(Category.class, Long.valueOf(3));
    Category c2 = (Category)s.get(Category.class, Long.valueOf(4));
    
    // 1
    Account a = new Account();
    a.setUsername("j2ee");
    a.setPassword("j2ee");
    a.setEmail("admin@pprun.org");
    a.setFirstname("ABC");
    a.setLastname("XYX");
    a.setStatus("OK");
    a.setUserAddr(ad);
    a.setPhone("555-555-5555");
    a.setLangPreference("english");
    
    a.setFavCategory(c1);
    a.setDisplayMylist(true);
    a.setDisplayBanner(true);
    
    s.persist(a);
    s.flush();
    
    // 2
    a = null;
    a = new Account();
    a.setUsername("ACID");
    a.setPassword("ACID");
    a.setEmail("admin@pprun.org");
    a.setFirstname("ABC");
    a.setLastname("XYX");
    a.setStatus("OK");
    a.setUserAddr(ad);
    a.setPhone("555-555-5555");
    a.setLangPreference("english");
    
    a.setFavCategory(c2);
    a.setDisplayMylist(true);
    a.setDisplayBanner(true);
    
    s.persist(a);
    s.flush();
    
    log.info("populating Account end.");
  }
  
  private static void insertSuppliers(Session s) {
    log.info("populating Supplier ...");
    // 1
    Address ad = new Address();
    ad.setAddr1("600 Avon Way");
    ad.setAddr2("");
    ad.setCity("Los Angeles");
    ad.setState("CA");
    ad.setZipcode("94024");
    ad.setCountry("USA");
    
    // 1
    Supplier sp = new Supplier();
    sp.setSupplierName("XYZ Pets");
    sp.setStatus("AC");
    sp.setSupplierAddr(ad);
    sp.setPhone("212-947-0797");
    
    s.persist(sp);
    s.flush();
    
    // 2
    ad = null;
    ad = new Address();
    ad.setAddr1("700 Abalone Way");
    ad.setAddr2("");
    ad.setCity("San Francisco");
    ad.setState("CA");
    ad.setZipcode("94024");
    ad.setCountry("USA");
    
    sp = null;
    sp = new Supplier();
    sp.setSupplierName("ABC Pets");
    sp.setStatus("AC");
    sp.setSupplierAddr(ad);
    sp.setPhone("415-947-0797");
    
    s.persist(sp);
    s.flush();
    
    log.info("populating Supplier end.");
  }
  
  private static void insertProducts(Session s) {
    log.info("populating Product ...");
    
    Category c1 = (Category)s.get(Category.class, Long.valueOf(1));
    Category c2 = (Category)s.get(Category.class, Long.valueOf(2));
    Category c3 = (Category)s.get(Category.class, Long.valueOf(3));
    Category c4 = (Category)s.get(Category.class, Long.valueOf(4));
    Category c5 = (Category)s.get(Category.class, Long.valueOf(5));
    
    // 1
    Product p = new Product();
    p.setProductNumber("FI-SW-01");
    p.setCategory(c1);
    p.setProductName("Angelfish");
    p.setProductDesc("<image src=\"../images/fish1.jpg\">Salt Water fish from Australia");
    
    s.persist(p);
    s.flush();
    
    // 2
    p = null;
    p = new Product();
    p.setProductNumber("FI-SW-02");
    p.setCategory(c1);
    p.setProductName("Tiger Shark");
    p.setProductDesc("<image src=\"../images/fish4.jpg\">Salt Water fish from Australia");
    
    s.persist(p);
    s.flush();
    
    // 3
    p = null;
    p = new Product();
    p.setProductNumber("FI-FW-01");
    p.setCategory(c1);
    p.setProductName("Koi");
    p.setProductDesc("<image src=\"../images/fish3.gif\">Fresh Water fish from Japan");
    
    s.persist(p);
    s.flush();
    
    // 4
    p = null;
    p = new Product();
    p.setProductNumber("FI-FW-02");
    p.setCategory(c1);
    p.setProductName("Goldfish");
    p.setProductDesc("<image src=\"../images/fish2.gif\">Fresh Water fish from China");
    
    s.persist(p);
    s.flush();
    
    // 5
    p = null;
    p = new Product();
    p.setProductNumber("K9-BD-01");
    p.setCategory(c2);
    p.setProductName("Bulldog");
    p.setProductDesc("<image src=\"../images/dog2.gif\">Friendly dog from England");
    
    s.persist(p);
    s.flush();
    
    // 6
    p = null;
    p = new Product();
    p.setProductNumber("K9-PO-02");
    p.setCategory(c2);
    p.setProductName("Poodle");
    p.setProductDesc("<image src=\"../images/dog6.gif\">Cute dog from France");
    
    s.persist(p);
    s.flush();
    
    // 7
    p = null;
    p = new Product();
    p.setProductNumber("K9-DL-01");
    p.setCategory(c2);
    p.setProductName("Dalmation");
    p.setProductDesc("<image src=\"../images/dog5.gif\">Great dog for a Fire Station");
    
    s.persist(p);
    s.flush();
    
    // 8
    p = null;
    p = new Product();
    p.setProductNumber("K9-RT-01");
    p.setCategory(c2);
    p.setProductName("Golden Retriever");
    p.setProductDesc("<image src=\"../images/dog1.gif\">Great family dog");
    
    s.persist(p);
    s.flush();
    
    // 9
    p = null;
    p = new Product();
    p.setProductNumber("K9-RT-02");
    p.setCategory(c2);
    p.setProductName("Labrador Retriever");
    p.setProductDesc("<image src=\"../images/dog5.gif\">Great hunting dog");
    
    s.persist(p);
    s.flush();
    
    // 10
    p = null;
    p = new Product();
    p.setProductNumber("K9-CW-01");
    p.setCategory(c2);
    p.setProductName("Chihuahua");
    p.setProductDesc("<image src=\"../images/dog4.gif\">Great companion dog");
    
    s.persist(p);
    s.flush();
    
    // 11
    p = null;
    p = new Product();
    p.setProductNumber("RP-SN-01");
    p.setCategory(c3);
    p.setProductName("Rattlesnake");
    p.setProductDesc("<image src=\"../images/lizard3.gif\">Doubles as a watch dog");
    
    s.persist(p);
    s.flush();
    
    // 12
    p = null;
    p = new Product();
    p.setProductNumber("RP-LI-02");
    p.setCategory(c3);
    p.setProductName("Iguana");
    p.setProductDesc("<image src=\"../images/lizard2.gif\">Friendly green friend");
    
    s.persist(p);
    s.flush();
    
    // 13
    p = null;
    p = new Product();
    p.setProductNumber("FL-DSH-01");
    p.setCategory(c4);
    p.setProductName("Manx");
    p.setProductDesc("<image src=\"../images/cat3.gif\">Great for reducing mouse populations");
    
    s.persist(p);
    s.flush();
    
    // 14
    p = null;
    p = new Product();
    p.setProductNumber("FL-DLH-02");
    p.setCategory(c4);
    p.setProductName("Persian");
    p.setProductDesc("<image src=\"../images/cat1.gif\">Friendly house cat, doubles as a princess");
    
    s.persist(p);
    s.flush();
    
    // 15
    p = null;
    p = new Product();
    p.setProductNumber("AV-CB-01");
    p.setCategory(c5);
    p.setProductName("Amazon Parrot");
    p.setProductDesc("<image src=\"../images/bird4.gif\">Great companion for up to 75 years");
    
    s.persist(p);
    s.flush();
    
    // 16
    p = null;
    p = new Product();
    p.setProductNumber("AV-SB-02");
    p.setCategory(c5);
    p.setProductName("Finch");
    p.setProductDesc("<image src=\"../images/bird1.gif\">Great stress reliever");
    
    s.persist(p);
    s.flush();
    
    log.info("populating Product end.");
  }
  
  private static void insertItems(Session s) {
//    log.info("populating Item ...");
//    Supplier sp1 = (Supplier)s.get(Supplier.class, Long.valueOf(1));
//    Supplier sp2 = (Supplier)s.get(Supplier.class, Long.valueOf(2));
//    
//    // FI-SW-01
//    Product p1 = (Product)s.get(Product.class, Long.valueOf(1));
//    // FI-SW-02
//    Product p2 = (Product)s.get(Product.class, Long.valueOf(2));
//    // FI-FW-01
//    Product p3 = (Product)s.get(Product.class, Long.valueOf(3));
//    // FI-FW-02
//    Product p4 = (Product)s.get(Product.class, Long.valueOf(4));
//    // K9-BD-01
//    Product p5 = (Product)s.get(Product.class, Long.valueOf(5));
//    // K9-PO-02
//    Product p6 = (Product)s.get(Product.class, Long.valueOf(6));
//    // K9-DL-01
//    Product p7 = (Product)s.get(Product.class, Long.valueOf(7));
//    // K9-RT-01
//    Product p8 = (Product)s.get(Product.class, Long.valueOf(8));
//    // K9-RT-02
//    Product p9 = (Product)s.get(Product.class, Long.valueOf(9));
//    // K9-CW-01
//    Product p10 = (Product)s.get(Product.class, Long.valueOf(10));
//    // RP-SN-01
//    Product p11 = (Product)s.get(Product.class, Long.valueOf(11));
//    // RP-LI-02
//    Product p12 = (Product)s.get(Product.class, Long.valueOf(12));
//    // FL-DSH-01
//    Product p13 = (Product)s.get(Product.class, Long.valueOf(13));
//    // FL-DLH-02
//    Product p14 = (Product)s.get(Product.class, Long.valueOf(14));
//    // AV-CB-01
//    Product p15 = (Product)s.get(Product.class, Long.valueOf(15));
//    // AV-SB-02
//    Product p16 = (Product)s.get(Product.class, Long.valueOf(16));
//    
//    // 1
//    Item i = new Item();
//    i.setItemName("EST-1");
//    i.setProduct(p1);
//    i.setListPrice(BigDecimal.valueOf(16.50));
//    i.setUnitCost(BigDecimal.valueOf(10.00));
//    i.setSupplier(sp1);
//    i.setStatus("P");
//    i.setAttr1("Large");
//    
//    s.persist(i);
//    s.flush();
//    
//    // 2
//    i = null;
//    i = new Item();
//    i.setItemName("EST-2");
//    i.setProduct(p1);
//    i.setListPrice(BigDecimal.valueOf(16.50));
//    i.setUnitCost(BigDecimal.valueOf(10.00));
//    i.setSupplier(sp1);
//    i.setStatus("P");
//    i.setAttr1("Small");
//    
//    s.persist(i);
//    s.flush();
//    
//    // 3
//    i = null;
//    i = new Item();
//    i.setItemName("EST-3");
//    i.setProduct(p2);
//    i.setListPrice(BigDecimal.valueOf(16.50));
//    i.setUnitCost(BigDecimal.valueOf(12.00));
//    i.setSupplier(sp1);
//    i.setStatus("P");
//    i.setAttr1("Toothless");
//    
//    s.persist(i);
//    s.flush();
//    
//    // 4
//    i = null;
//    i = new Item();
//    i.setItemName("EST-4");
//    i.setProduct(p3);
//    i.setListPrice(BigDecimal.valueOf(18.50));
//    i.setUnitCost(BigDecimal.valueOf(12.00));
//    i.setSupplier(sp1);
//    i.setStatus("P");
//    i.setAttr1("Spotted");
//    
//    s.persist(i);
//    s.flush();
//    
//    // 5
//    i = null;
//    i = new Item();
//    i.setItemName("EST-5");
//    i.setProduct(p3);
//    i.setListPrice(BigDecimal.valueOf(18.50));
//    i.setUnitCost(BigDecimal.valueOf(12.00));
//    i.setSupplier(sp1);
//    i.setStatus("P");
//    i.setAttr1("Spotless");
//    
//    s.persist(i);
//    s.flush();
//    
//    // 6
//    i = null;
//    i = new Item();
//    i.setItemName("EST-6");
//    i.setProduct(p5);
//    i.setListPrice(BigDecimal.valueOf(18.50));
//    i.setUnitCost(BigDecimal.valueOf(12.00));
//    i.setSupplier(sp1);
//    i.setStatus("P");
//    i.setAttr1("Male Adult");
//    
//    s.persist(i);
//    s.flush();
//    
//    // 7
//    i = null;
//    i = new Item();
//    i.setItemName("EST-7");
//    i.setProduct(p5);
//    i.setListPrice(BigDecimal.valueOf(18.50));
//    i.setUnitCost(BigDecimal.valueOf(12.00));
//    i.setSupplier(sp1);
//    i.setStatus("P");
//    i.setAttr1("Female Puppy");
//    
//    s.persist(i);
//    s.flush();
//    
//    // 8
//    i = null;
//    i = new Item();
//    i.setItemName("EST-8");
//    i.setProduct(p6);
//    i.setListPrice(BigDecimal.valueOf(18.50));
//    i.setUnitCost(BigDecimal.valueOf(12.00));
//    i.setSupplier(sp1);
//    i.setStatus("P");
//    i.setAttr1("Male Puppy");
//    
//    s.persist(i);
//    s.flush();
//    
//    // 9
//    i = null;
//    i = new Item();
//    i.setItemName("EST-9");
//    i.setProduct(p7);
//    i.setListPrice(BigDecimal.valueOf(18.50));
//    i.setUnitCost(BigDecimal.valueOf(12.00));
//    i.setSupplier(sp1);
//    i.setStatus("P");
//    i.setAttr1("Spotless Male Puppy");
//    
//    s.persist(i);
//    s.flush();
//    
//    // 10
//    i = null;
//    i = new Item();
//    i.setItemName("EST-10");
//    i.setProduct(p7);
//    i.setListPrice(BigDecimal.valueOf(18.50));
//    i.setUnitCost(BigDecimal.valueOf(12.00));
//    i.setSupplier(sp1);
//    i.setStatus("P");
//    i.setAttr1("Spotted Adult Female");
//    
//    s.persist(i);
//    s.flush();
//    
//    // 11
//    i = null;
//    i = new Item();
//    i.setItemName("EST-11");
//    i.setProduct(p11);
//    i.setListPrice(BigDecimal.valueOf(18.50));
//    i.setUnitCost(BigDecimal.valueOf(12.00));
//    i.setSupplier(sp1);
//    i.setStatus("P");
//    i.setAttr1("Venomless");
//    
//    s.persist(i);
//    s.flush();
//    
//    // 12
//    i = null;
//    i = new Item();
//    i.setItemName("EST-12");
//    i.setProduct(p11);
//    i.setListPrice(BigDecimal.valueOf(18.50));
//    i.setUnitCost(BigDecimal.valueOf(12.00));
//    i.setSupplier(sp1);
//    i.setStatus("P");
//    i.setAttr1("Rattleless");
//    
//    s.persist(i);
//    s.flush();
//    
//    // 13
//    i = null;
//    i = new Item();
//    i.setItemName("EST-13");
//    i.setProduct(p12);
//    i.setListPrice(BigDecimal.valueOf(18.50));
//    i.setUnitCost(BigDecimal.valueOf(12.00));
//    i.setSupplier(sp1);
//    i.setStatus("P");
//    i.setAttr1("Green Adult");
//    
//    s.persist(i);
//    s.flush();
//    
//    // 14
//    i = null;
//    i = new Item();
//    i.setItemName("EST-14");
//    i.setProduct(p13);
//    i.setListPrice(BigDecimal.valueOf(58.50));
//    i.setUnitCost(BigDecimal.valueOf(12.00));
//    i.setSupplier(sp1);
//    i.setStatus("P");
//    i.setAttr1("Tailless");
//    
//    s.persist(i);
//    s.flush();
//    
//    // 15
//    i = null;
//    i = new Item();
//    i.setItemName("EST-15");
//    i.setProduct(p13);
//    i.setListPrice(BigDecimal.valueOf(58.50));
//    i.setUnitCost(BigDecimal.valueOf(12.00));
//    i.setSupplier(sp1);
//    i.setStatus("P");
//    i.setAttr1("With tail");
//    
//    s.persist(i);
//    s.flush();
//    
//    // 16
//    i = null;
//    i = new Item();
//    i.setItemName("EST-16");
//    i.setProduct(p14);
//    i.setListPrice(BigDecimal.valueOf(93.50));
//    i.setUnitCost(BigDecimal.valueOf(12.00));
//    i.setSupplier(sp1);
//    i.setStatus("P");
//    i.setAttr1("Adult Female");
//    
//    s.persist(i);
//    s.flush();
//    
//    // 17
//    i = null;
//    i = new Item();
//    i.setItemName("EST-17");
//    i.setProduct(p14);
//    i.setListPrice(BigDecimal.valueOf(93.50));
//    i.setUnitCost(BigDecimal.valueOf(12.00));
//    i.setSupplier(sp1);
//    i.setStatus("P");
//    i.setAttr1("Adult Male");
//    
//    s.persist(i);
//    s.flush();
//    
//    // 18
//    i = null;
//    i = new Item();
//    i.setItemName("EST-17");
//    i.setProduct(p15);
//    i.setListPrice(BigDecimal.valueOf(193.50));
//    i.setUnitCost(BigDecimal.valueOf(92.00));
//    i.setSupplier(sp1);
//    i.setStatus("P");
//    i.setAttr1("Adult Male");
//    
//    s.persist(i);
//    s.flush();
//    
//    // 19
//    i = null;
//    i = new Item();
//    i.setItemName("EST-19");
//    i.setProduct(p16);
//    i.setListPrice(BigDecimal.valueOf(15.50));
//    i.setUnitCost(BigDecimal.valueOf(2.00));
//    i.setSupplier(sp1);
//    i.setStatus("P");
//    i.setAttr1("Adult Male");
//    
//    s.persist(i);
//    s.flush();
//    
//    // 20
//    i = null;
//    i = new Item();
//    i.setItemName("EST-20");
//    i.setProduct(p2);
//    i.setListPrice(BigDecimal.valueOf(5.50));
//    i.setUnitCost(BigDecimal.valueOf(2.00));
//    i.setSupplier(sp1);
//    i.setStatus("P");
//    i.setAttr1("Adult Male");
//    
//    s.persist(i);
//    s.flush();
//    
//    // 21
//    i = null;
//    i = new Item();
//    i.setItemName("EST-21");
//    i.setProduct(p2);
//    i.setListPrice(BigDecimal.valueOf(5.29));
//    i.setUnitCost(BigDecimal.valueOf(1.00));
//    i.setSupplier(sp1);
//    i.setStatus("P");
//    i.setAttr1("Adult Female");
//    
//    s.persist(i);
//    s.flush();
//    
//    // 22
//    i = null;
//    i = new Item();
//    i.setItemName("EST-22");
//    i.setProduct(p9);
//    i.setListPrice(BigDecimal.valueOf(135.50));
//    i.setUnitCost(BigDecimal.valueOf(100.00));
//    i.setSupplier(sp1);
//    i.setStatus("P");
//    i.setAttr1("Adult Male");
//    
//    s.persist(i);
//    s.flush();
//    
//    // 23
//    i = null;
//    i = new Item();
//    i.setItemName("EST-23");
//    i.setProduct(p9);
//    i.setListPrice(BigDecimal.valueOf(145.50));
//    i.setUnitCost(BigDecimal.valueOf(100.00));
//    i.setSupplier(sp1);
//    i.setStatus("P");
//    i.setAttr1("Adult Female");
//    
//    s.persist(i);
//    s.flush();
//    
//    // 24
//    i = null;
//    i = new Item();
//    i.setItemName("EST-24");
//    i.setProduct(p9);
//    i.setListPrice(BigDecimal.valueOf(255.50));
//    i.setUnitCost(BigDecimal.valueOf(92.00));
//    i.setSupplier(sp1);
//    i.setStatus("P");
//    i.setAttr1("Adult Male");
//    
//    s.persist(i);
//    s.flush();
//    
//    // 25
//    i = null;
//    i = new Item();
//    i.setItemName("EST-25");
//    i.setProduct(p9);
//    i.setListPrice(BigDecimal.valueOf(325.29));
//    i.setUnitCost(BigDecimal.valueOf(90.00));
//    i.setSupplier(sp1);
//    i.setStatus("P");
//    i.setAttr1("Adult Male");
//    
//    s.persist(i);
//    s.flush();
//    
//    // 26
//    i = null;
//    i = new Item();
//    i.setItemName("EST-26");
//    i.setProduct(p10);
//    i.setListPrice(BigDecimal.valueOf(125.50));
//    i.setUnitCost(BigDecimal.valueOf(92.00));
//    i.setSupplier(sp1);
//    i.setStatus("P");
//    i.setAttr1("Adult Male");
//    
//    s.persist(i);
//    s.flush();
//    
//    // 27
//    i = null;
//    i = new Item();
//    i.setItemName("EST-27");
//    i.setProduct(p10);
//    i.setListPrice(BigDecimal.valueOf(155.29));
//    i.setUnitCost(BigDecimal.valueOf(90.00));
//    i.setSupplier(sp1);
//    i.setStatus("P");
//    i.setAttr1("Adult Female");
//    
//    s.persist(i);
//    s.flush();
//    // 28
//    i = null;
//    i = new Item();
//    i.setItemName("EST-28");
//    i.setProduct(p8);
//    i.setListPrice(BigDecimal.valueOf(155.29));
//    i.setUnitCost(BigDecimal.valueOf(90.00));
//    i.setSupplier(sp1);
//    i.setStatus("P");
//    i.setAttr1("Adult Female");
//    
//    s.persist(i);
//    s.flush();
//    log.info("populating Item end.");
  }
  
  private static void insertInventories(Session s) {
//    log.info("populating Inventory ...");
//    for (int k = 1; k < 29; k++) {
//      Item i = (Item)s.get(Item.class, Long.valueOf(k));
//      Inventory in = new Inventory();
//      in.setItem(i);
//      in.setDateAdded(new Date());
//      in.setQuantity(10000);
//      s.persist(in);
//    }
//    log.info("populating Inventory end.");
  }
}
