package org.springframework.samples.jpetstore.web.struts;

import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.samples.jpetstore.domain.Item;
import org.springframework.samples.jpetstore.domain.Product;

public class ViewItemAction extends BaseAction {
  
  public ActionForward execute(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    String itemName = request.getParameter("itemName");
    
    // modified DAO interface
    List list = getPetStore().getItem(itemName);
    Item item = null;
    Product product = null;
    for (Iterator it = list.iterator(); it.hasNext();) {
      Object[] pair = (Object[]) it.next();
      item = (Item)pair[0];
      product = (Product)pair[1];
    }
    
    // added by pprun
    item.setQuantity(getPetStore().getItemQuantity(itemName));
    request.setAttribute("item", item);
    request.setAttribute("product", product);
    
    // 防止多重提交
    saveToken(request);
    return mapping.findForward("success");
  }
  
}
