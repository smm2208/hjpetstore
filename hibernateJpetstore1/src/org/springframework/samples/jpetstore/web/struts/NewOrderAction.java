package org.springframework.samples.jpetstore.web.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.springframework.samples.jpetstore.domain.Order;

public class NewOrderAction extends SecureBaseAction {
  
  protected ActionForward doExecute(ActionMapping mapping, ActionForm form,
          HttpServletRequest request, HttpServletResponse response) throws Exception {
    if (!isTokenValid(request, false)) {
      // 不是同一个令牌，为多重提交
      //resetToken(request); // 判断完不自动销毁，留待下面的逻辑处理
      
      request.setAttribute("message", "多重提交！");
      
      request.getSession().removeAttribute("workingOrderForm");
      request.getSession().removeAttribute("cartForm");
      
      // Fixed by pprun for duplicate-submit and bug in the next time submit:
      // 竟然不再需要确认了！
      request.getSession().removeAttribute("orderForm");
      
      return mapping.findForward("failure");
    } else {
      // 多页表单
      OrderActionForm orderForm = (OrderActionForm) form;
      // 是否要进入可选的 shipingAddress 页面
      if (orderForm.isShippingAddressRequired() && 
              orderForm.getStep().equals("1")) {
        // 需要将物品寄给别人，而不是自己
        return mapping.findForward("shipping");
        
        // 两种情况：
        // 1. 从页面1直接进入确认页面（不需要寄到不同的地址时）
        // 2. 从 shipingAddress 进入到确认页面
      } else if ((orderForm.getStep().equals("1") && 
              orderForm.isShippingAddressRequired() == false) || 
                    orderForm.getStep().equals("2")) {
        // 进入确认页面
        return mapping.findForward("confirm");
        
      } else if (orderForm.getOrder() != null) {
        // 最终处理
        
        // 销毁事务标记(放在此处，最开始处很重要，
        // 以保证不管再快的多重提交都会得到无效的判断的)
        resetToken(request);
        
        Order order = orderForm.getOrder();
        // todo 这段逻辑应该放在 DAO 层？
        
        getPetStore().insertOrder(order);
        // 成功进行后，移除会话状态，
        // 以便 NewOrderFormAction 中检查出是否用户后退操作
        request.getSession().removeAttribute("workingOrderForm");
        request.getSession().removeAttribute("cartForm");
        
        // Fixed by pprun for duplicate-submit and bug in the next time submit:
        // 竟然不再需要确认了！所以必须移除它
        request.getSession().removeAttribute("orderForm");
        
        request.setAttribute("order", order);
        request.setAttribute("message", "Thank you, your order has been submitted.");
        
        // 选择 ViewOrder.jsp 中的显示方式
        request.setAttribute("newOrder", true);
        return mapping.findForward("success");
      } else {
        request.setAttribute("message", "An error occurred processing your order (order was null).");
        return mapping.findForward("failure");
      }
    }
  }
  
}
