package org.springframework.samples.jpetstore.web.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.springframework.samples.jpetstore.domain.Account;
import org.springframework.samples.jpetstore.domain.Address;

public class NewAccountFormAction extends BaseAction {
  
  public ActionForward execute(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) throws Exception {
    // 由于登录和注册重用了 AccountActionForm 的结果，所以需要手工进行初始化？
    // 而不是通过 (AccountActionForm)form 得到.
    // 因为需要使刚注册的用户成为当前用户，而不是之前的老帐户。
    AccountActionForm workingAcctForm = new AccountActionForm();
    
    request.getSession().removeAttribute("workingAccountForm");
    request.getSession().setAttribute("workingAccountForm", workingAcctForm);
    
    if (workingAcctForm.getAccount() == null) {
      // PP: 必须初始化 account 的 address 组件
      Account account = new Account();
      account.setUserAddr(new Address());
      workingAcctForm.setAccount(account);
    }
    
    // 在哪个地方用到了这个成员? 答：选择感兴趣的下拉列表中用到了！
    if (workingAcctForm.getCategories() == null) {
      workingAcctForm.setCategories(getPetStore().getCategoryList());
    }
    return mapping.findForward("success");
  }
  
}
