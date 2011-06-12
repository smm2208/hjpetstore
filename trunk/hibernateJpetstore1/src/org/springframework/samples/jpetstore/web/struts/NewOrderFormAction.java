package org.springframework.samples.jpetstore.web.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.springframework.samples.jpetstore.domain.Account;

public class NewOrderFormAction extends SecureBaseAction {
  
  protected ActionForward doExecute(ActionMapping mapping, ActionForm form,
          HttpServletRequest request,
          HttpServletResponse response) throws Exception {
    // 使用 session 中的对象 accountForm 初始化相关值
    AccountActionForm acctForm =
            (AccountActionForm) request.getSession().getAttribute("accountForm");
    
    // 因为 CartActionForm 在Account的Action映射中(struts-config.xml)被设置成
    // session scope, 所以使用 getSession()
    CartActionForm cartForm =
            (CartActionForm) request.getSession().getAttribute("cartForm");
    
    if (cartForm != null) {
      OrderActionForm orderForm = (OrderActionForm) form;
      
      // Re-read account from DB at team's request. pprun: why?
      
      // 只有在从 checkout 界面进入时才 initOrder
      // 因为在后续的页面校验出错又将使用这个 Action, 所以必须作出判断，
      // 否则，将会重复把购物车中的物品加入 order 中
      if (request.getParameter("step") == null) {
        Account account =
                getPetStore().getAccount(acctForm.getAccount().getUsername());
        orderForm.getOrder().initOrder(account, cartForm.getCart());
      }
      
      // 避免重复提交
      saveToken(request);
      return mapping.findForward("success");
    } else {
      // 在 NewOrderAction中成功进行后，
      // 移除了会话状态(cartForm)，因此此检查出是否是用户按后退按钮进来的
      
      // 调试 重复提交时的代码
      // request.getSession().removeAttribute("workingOrderForm");
      // request.getSession().removeAttribute("cartForm");
      
      request.setAttribute("message", "An order could not be created because" +
              " a cart could not be found.");
      return mapping.findForward("failure");
    }
  }
  
}