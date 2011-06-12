package org.springframework.samples.jpetstore.web.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 进行安全相关的操作，比如修改帐户信息，查看与帐户的信息等，
 * 在这之前必须先登录，因此这个 Action 主要进行这个拦截，如果没有登录的话，
 * 会先记住当前要进行的动作，然后先呈现登录界面，当成功登录后，
 * 会跳转到真正要去的界面.
 */
public abstract class SecureBaseAction extends BaseAction {
  
  public ActionForward execute(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response)
          throws Exception {
    
    AccountActionForm acctForm = (AccountActionForm) request.getSession().
            getAttribute("accountForm");
    if (acctForm == null || acctForm.getAccount() == null) {
      String url = request.getServletPath();
      String query = request.getQueryString();
      
      if (query != null) {
        // 如：　http://localhost:8084/jpetstore/shop/viewOrder.do?id=1000
        request.setAttribute("signonForwardAction", url+"?"+query);
        
      } else {
        request.setAttribute("signonForwardAction", url);
      }
      
      return mapping.findForward("global-signon");
      
    } else {
      return doExecute(mapping, form, request, response);
    }
  }
  
  protected abstract ActionForward doExecute(ActionMapping mapping,
          ActionForm form, HttpServletRequest request,
          HttpServletResponse response) throws Exception;
  
}
