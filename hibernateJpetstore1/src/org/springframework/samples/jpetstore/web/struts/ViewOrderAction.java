package org.springframework.samples.jpetstore.web.struts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.samples.jpetstore.domain.Item;
import org.springframework.samples.jpetstore.domain.LineItem;

import org.springframework.samples.jpetstore.domain.Order;

public class ViewOrderAction extends SecureBaseAction {
  
  protected ActionForward doExecute(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    AccountActionForm acctForm = (AccountActionForm) form;
    long id = Integer.parseInt(request.getParameter("id")); // orderId
    
    // 改变 DAO 接口，
    //为配合 order.getUser().getUsername() 的需要，但
    // 又避免使用 open session in view 模式
    List list = getPetStore().getOrderAndUserName(id);
    Order order = null;
    String username = null;
    for (Iterator it = list.iterator(); it.hasNext();) {
      Object[] pair = (Object[]) it.next();
      order = (Order)pair[0];
      username = (String)pair[1];
    }
    
    // 初始化单向的关系 LineItem -> Item (需不需要初始化是由 Hibernate 来判断的
    // 比如：在整个购物的流程结果后，也要显示 ViewOrder.jsp, 但那时是不需要初始
    // 的，因为当时所有的实体都初始化出来了，只有在直接从 EditAccountForm 中
    // 的链接　My Orders 时，才需要显示的初始化关系： LineItem -> Item )
    // 因为 LineItem 中的 Item 被设置为 update = false, 所有没有 setItem() 方法，
    // 因此这里另开辟 ..., 以供页面访问
    List<Item> items = new ArrayList<Item>();
    List<String> productNames = new ArrayList<String>();
    
    List<LineItem> ls = order.getLineItems();
    for (LineItem li : ls) {
      // detached entiry, 在 getPetStore().getItem(i) 被再次关联到 session
      Item i = li.getItem();
      Object[] pair = (Object[])getPetStore().getItem(i).get(0);
      Item item = (Item)pair[0];
      String pn = (String)pair[1]; 
      items.add(item);
      productNames.add(pn);
    }
    // 放入 request attribut
    request.setAttribute("items", items);
    request.setAttribute("productNames", productNames);
    
    if (acctForm.getAccount().getUsername().equals(username)) {
      request.setAttribute("order", order);
      
      // 选择 ViewOrder.jsp 中的显示方式
      request.setAttribute("viewOrder", true);
      return mapping.findForward("success");
    } else {
      request.setAttribute("message", "You may only view your own orders.");
      return mapping.findForward("failure");
    }
  }
  
}
