package org.springframework.samples.jpetstore.web.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.samples.jpetstore.domain.Account;
import org.springframework.samples.jpetstore.domain.Category;

public class NewAccountAction extends BaseAction {
  
  public ActionForward execute(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) throws Exception {
    AccountActionForm acctForm = (AccountActionForm) form;
    if (AccountActionForm.VALIDATE_NEW_ACCOUNT.equals(acctForm.getValidate())) {
//      acctForm.getAccount().setDisplayMylist(
//              request.getParameter("account.displayMylist") != null);
//      acctForm.getAccount().setDisplayBanner(
//              request.getParameter("account.displayBanner") != null);
      Account account = acctForm.getAccount();
      String username = acctForm.getAccount().getUsername();
      
      // todo 是否要将这段逻辑放在 DAO 层？
      // 设置 account 的 favCategory
      String favCategory = acctForm.getFavCategoryName();
      Category category = getPetStore().getCategory(favCategory);
      account.setFavCategory(category);
      
      getPetStore().insertAccount(account);
      
      // set account
      acctForm.setAccount(getPetStore().getAccount(username));
      
      // and 对应的 username, password
      acctForm.setUsername(account.getUsername());
      acctForm.setPassword(account.getPassword());
      
      // set myList
      if (request.getParameter("account.displayMylist") != null) {
        PagedListHolder myList = new PagedListHolder(
                getPetStore().getProductListByCategory(
                acctForm.getFavCategoryName()));
        myList.setPageSize(4);
        acctForm.setMyList(myList);
      }

      // set banner name
      if (request.getParameter("account.displayBanner") != null) {
        String bannerName = getPetStore().getBannerNameForUser(username);
        acctForm.setBannerName(bannerName);
      }
      
      request.getSession().setAttribute("accountForm", acctForm);
      request.getSession().removeAttribute("workingAccountForm");
      return mapping.findForward("success");
    } else {
      request.setAttribute("message", "Your account was not created because " +
              "the submitted information was not validated.");
      return mapping.findForward("failure");
    }
  }
  
}
