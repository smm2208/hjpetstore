package org.springframework.samples.jpetstore.web.struts;

import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.samples.jpetstore.domain.Product;

public class ViewProductAction extends BaseAction {
  
  public ActionForward execute(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    String productNumber = request.getParameter("productNumber");
    
    if (productNumber != null) {
      PagedListHolder itemList = new PagedListHolder(getPetStore().
              getItemListByProduct(productNumber));
      itemList.setPageSize(4);
      
      // 改变 DAO 的接口，返回一个List，包含 product  和 categoryName
      // 为解决著名的: org.hibernate.LazyInitializationException:
      //               could not initialize proxy - the owning Session was closed
      // (由页面 Product.jsp 中 ${product.category.categoryName} 引起).
      //
      // 虽然采用 "Open Session in View pattern" 模式, 对应 Spring 中即：
      // @see org.springframework.orm.hibernate3.support.OpenSessionInViewFilter
      // @see org.springframework.orm.hibernate3.support.OpenSessionInViewInterceptor
      // 但其并不是高吞吐量应用的首选方案
      // 因此这里要把 many-to-one 关系的一端加(category.categoryName)入到 
      // request attribute, 以便页面可访问.
      List list = getPetStore().getProduct(productNumber);
      Product product = null;
      String categoryName = null;
      for (Iterator it = list.iterator(); it.hasNext();) {
        Object[] pair = (Object[]) it.next();
        product = (Product)pair[0];
        categoryName = (String)pair[1];
      }
      
      // -- for debug
//      System.out.println(categoryName);
//      System.out.println(product);
      // -- 
      request.getSession().setAttribute("ViewProductAction_itemList", itemList);
      request.getSession().setAttribute("ViewProductAction_product", product);
      request.setAttribute("itemList", itemList);
      request.setAttribute("product", product);
      request.setAttribute("categoryName", categoryName);
      
    } else {
      PagedListHolder itemList = (PagedListHolder) request.getSession().
              getAttribute("ViewProductAction_itemList");
      Product product = (Product) request.getSession().
              getAttribute("ViewProductAction_product");
      String page = request.getParameter("page");
      if ("next".equals(page)) {
        itemList.nextPage();
      } else if ("previous".equals(page)) {
        itemList.previousPage();
      }
      request.setAttribute("itemList", itemList);
      request.setAttribute("product", product);
    }
    // 防止多重提交
    saveToken(request);
    //System.out.println(request.getSession().getAttribute(Globals.TRANSACTION_TOKEN_KEY));
    return mapping.findForward("success");
  }
  
}
