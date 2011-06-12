package org.springframework.samples.jpetstore.web.struts;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.springframework.samples.jpetstore.domain.CartItem;

public class UpdateCartQuantitiesAction extends BaseAction {
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, 
          HttpServletRequest request, HttpServletResponse response) throws Exception {
    CartActionForm cartForm = (CartActionForm) form;
    Iterator cartItems = cartForm.getCart().getAllCartItems();
    
    while (cartItems.hasNext()) {
      CartItem cartItem = (CartItem) cartItems.next();
      String itemName = cartItem.getItem().getItemName();
      
      try {
        // 利用 request.getParameter(itemName) 得到从表单提交的对应的 itemName 的数量
        int quantity = Integer.parseInt(request.getParameter(itemName));
        cartForm.getCart().setQuantityByItemName(itemName, quantity);
        if (quantity < 1) {
          cartItems.remove();
        }
      } catch (NumberFormatException e) {
        //ignore on purpose
        // 如果输入的不是一个有效的合法数字，忽略
      }
    }
    return mapping.findForward("success");
  }
  
}
