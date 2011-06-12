package org.springframework.samples.jpetstore.web.struts;

import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.samples.jpetstore.domain.Account;

public class SignonAction extends BaseAction {
  
  public ActionForward execute(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.getSession().removeAttribute("workingAccountForm");
    request.getSession().removeAttribute("accountForm");
    
    if (request.getParameter("signoff") != null) {
      request.getSession().invalidate();
      return mapping.findForward("success");
      
    } else {
      AccountActionForm acctForm = (AccountActionForm) form;
      String username = acctForm.getUsername();
      String password = acctForm.getPassword();
      
      List list = getPetStore().getAccount(username, password);
      Account account = null;
      String categoryName = null;
      for (Iterator it = list.iterator(); it.hasNext();) {
        Object[] pair = (Object[]) it.next();
        account = (Account)pair[0];
        categoryName = (String)pair[1];
      }
      
      // added by pprun
      String bannerName = getPetStore().getBannerNameForUser(username);
      
      if (account == null) {
        request.setAttribute("message", "Invalid username or password.  " +
                "Signon failed.");
        
        // 使用了全局 forward, 因为此 SignonAction 没有定义局部
        // forward name= ="failure"
        return mapping.findForward("failure");
        
      } else {
        String forwardAction = acctForm.getForwardAction();
        acctForm = new AccountActionForm();
        acctForm.setForwardAction(forwardAction);
        acctForm.setAccount(account);
        acctForm.getAccount().setPassword(null);
        
        // -- added by pprun
        if (bannerName != null) {
          acctForm.setBannerName(bannerName);
        }
        
        if (categoryName != null) {
          acctForm.setFavCategoryName(categoryName);
        }
        // --
        
        PagedListHolder myList = new PagedListHolder(
                getPetStore().getProductListByCategory(categoryName));
        myList.setPageSize(4);
        acctForm.setMyList(myList);
        request.getSession().setAttribute("accountForm", acctForm);
        
        if (acctForm.getForwardAction() == null ||
                acctForm.getForwardAction().length() < 1) {
          
          return mapping.findForward("success");
        } else {
          response.sendRedirect(acctForm.getForwardAction());
          return null;
        }
      }
    }
  }
  
}
