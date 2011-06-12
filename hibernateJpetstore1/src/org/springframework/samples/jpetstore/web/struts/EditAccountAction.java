package org.springframework.samples.jpetstore.web.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.samples.jpetstore.domain.Account;
import org.springframework.samples.jpetstore.domain.Category;

public class EditAccountAction extends SecureBaseAction {
  
  protected ActionForward doExecute(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    AccountActionForm acctForm = (AccountActionForm) form;
    if (AccountActionForm.VALIDATE_EDIT_ACCOUNT.equals(acctForm.getValidate())) {
      // checkBox 在 Struts TAG 是特殊的
//      acctForm.getAccount().setDisplayMylist(
//              request.getParameter("account.displayMylist") != null);
//      acctForm.getAccount().setDisplayBanner(
//              request.getParameter("account.displayBanner") != null);
      
      Account account = acctForm.getAccount();
      
      // 注意要设置其的关联对象
      String favCategory = acctForm.getFavCategoryName();
      Category category = getPetStore().getCategory(favCategory);
      account.setFavCategory(category);
      
      // 保存更新信息到模型层
      getPetStore().updateAccount(account);
      
      // 将更新拉出到控制层，以供表示层用
      acctForm.setAccount(getPetStore().getAccount(account.getUsername()));
      
      // and 对应的 username, password
      acctForm.setUsername(account.getUsername());
      acctForm.setPassword(account.getPassword());
      
      // -- 因为如果改变了感兴趣的宠物的话，需要重新将数据初始化。
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
        String bannerName = getPetStore().getBannerNameForUser("username");
        acctForm.setBannerName(bannerName);
      }
      
      request.getSession().setAttribute("accountForm", acctForm);
      
      // 修改帐户信息到此结束，因此将 workingAcctForm 清除，回到
      // accountForm 的日子.
      request.getSession().removeAttribute("workingAccountForm");
      return mapping.findForward("success");
    } else {
      request.setAttribute("message",
              "Your account was not updated because the submitted information" +
              " was not validated.");
      return mapping.findForward("failure");
    }
  }
  
}
