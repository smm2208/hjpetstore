package org.springframework.samples.jpetstore.web.struts;

import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.springframework.samples.jpetstore.domain.Cart;
import org.springframework.samples.jpetstore.domain.Item;

public class AddItemToCartAction extends BaseAction {
  
  public ActionForward execute(ActionMapping mapping, ActionForm form,
          HttpServletRequest request,
          HttpServletResponse response) throws Exception {
    //System.out.println(request.getSession().getAttribute(Globals.TRANSACTION_TOKEN_KEY));
    if (!isTokenValid(request, true)) {
      // 不是同一个令牌，为多重提交
      //resetToken(request); // 判断完自动销毁
      
      request.setAttribute("message", "多重提交！");
      return mapping.findForward("failure");
    } else {
      CartActionForm cartForm = (CartActionForm) form;
      Cart cart = cartForm.getCart();
      String workingItemName = cartForm.getWorkingItemName();
      if (cart.containsItemName(workingItemName)) {
        cart.incrementQuantityByItemName(workingItemName);
      } else {
        // isInStock is a "real-time" property that must be updated
        // every time an item is added to the cart, even if other
        // item details are cached.
        boolean isInStock = getPetStore().isItemInStock(workingItemName);
        //Item item = getPetStore().getItem(workingItemName);
        List list = getPetStore().getItem(workingItemName);
        Item item = null;
        
        for (Iterator it = list.iterator(); it.hasNext();) {
          Object[] pair = (Object[]) it.next();
          item = (Item)pair[0];
        }
        
        cartForm.getCart().addItem(item, isInStock);
      }
      return mapping.findForward("success");
    }
  }
  
}
