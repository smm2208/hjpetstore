package org.springframework.samples.jpetstore.web.struts;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class BaseActionForm extends ActionForm {
    /**
     * 此乃最原始的错误处理方法，将所有错误信息加入到一个列表后，然后存入到
     * Servlet 请求属性中供页面使用.
     * 更现代的方法是使用 Struts1.1 之后的 commons-validator,关于这个功能在
     * 各种关于 Struts 的参考或书籍中都有介绍。
     *
     * 是否为调用此方法是通过属性 validate 来控制的，如：
     * <action path="/shop/signon" type="org.springframework.samples.jpetstore.web.struts.SignonAction"
     * name="accountForm" scope="session" validate="false">
     * 是不会调用的，因为 validate="false".
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errorMessages = null;
        
        // 整个系统的错误信息列表，通过调用 doValidate(mapping, request, errorList);
        // addErrorIfStringEmpty 会将错误信息加入到列表当中，并且它被存入了请求属性当中.
        ArrayList errorList = new ArrayList();
        doValidate(mapping, request, errorList);
        request.setAttribute("errors", errorList);
        if (!errorList.isEmpty()) {
            errorMessages = new org.apache.struts.action.ActionErrors();
            errorMessages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("global.error"));
        }
        return errorMessages;
    }
    
    /**
     * 此方法被设计为供子类覆盖的(overriding).
     * 任何子类实现了这个方法，将自动被上面的 validate 方法调用。
     * 这也是设计模式的一种，基类提供了模板，子类将其实现。
     * @param mapping
     * @param request
     * @param errors
     */
    public void doValidate(ActionMapping mapping, HttpServletRequest request, List errors) {
    }
    
    /**
     * 此辅助方法被用来给定的页面输入内容是否为空，如果是空的话，将显示给定的出错信息。
     *
     * @param errors 错误信息列表
     * @param message 当 value 为空时，将显示这个错误信息
     * @param value 页面元素对应的值
     */
    protected void addErrorIfStringEmpty(List errors, String message, String value) {
        if (value == null || value.trim().length() < 1) {
            errors.add(message);
        }
    }
}
