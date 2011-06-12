<%@ page pageEncoding="GBK" %>
<%@ include file="IncludeTop.jsp" %>

<%-- 加了一个 step 请求参数以便校验时作正常处理，原版本中存在 Bug --%>
<html:form action="/shop/newOrderStep1.do?step=1" styleId="workingOrderForm" method="post" >
  
  <TABLE align="center" bgcolor="#008800" border=0 cellpadding=3 cellspacing=1 bgcolor="#FFFF88">
    <TR bgcolor="#FFFF88"><TD colspan=2>
        <FONT color=GREEN size=4><B>Payment Details</B></FONT>
    </TD></TR><TR bgcolor="#FFFF88"><TD>
      Card Type:</TD><TD>
        <html:select name="workingOrderForm" property="order.cardType">
          <html:options name="workingOrderForm" property="creditCardTypes" />
        </html:select>
    </TD></TR>
    <TR bgcolor="#FFFF88"><TD>
      Card Number:</TD><TD><html:text name="workingOrderForm" property="order.cardNumber" /> 
        <font color=red size=2>* Use a fake number!</font>
    </TD></TR>
    <TR bgcolor="#FFFF88"><TD>
      Expiry Date (MM/YYYY):</TD><TD><html:text name="workingOrderForm" property="order.expireDate" />
    </TD></TR>
    <TR bgcolor="#FFFF88"><TD colspan=2>
        <FONT color=GREEN size=4><B>Billing Address</B></FONT>
    </TD></TR>
    
    <TR bgcolor="#FFFF88"><TD>
      First name:</TD><TD><html:text name="workingOrderForm" property="order.billToFirstname" />
    </TD></TR>
    <TR bgcolor="#FFFF88"><TD>
      Last name:</TD><TD><html:text name="workingOrderForm" property="order.billToLastname" />
    </TD></TR>
    <TR bgcolor="#FFFF88"><TD>
      Address 1:</TD><TD><html:text size="40" name="workingOrderForm" property="order.billAddr.addr1" />
    </TD></TR>
    <TR bgcolor="#FFFF88"><TD>
      Address 2:</TD><TD><html:text size="40" name="workingOrderForm" property="order.billAddr.addr2" />
    </TD></TR>
    <TR bgcolor="#FFFF88"><TD>
      City: </TD><TD><html:text name="workingOrderForm" property="order.billAddr.city" />
    </TD></TR>
    <TR bgcolor="#FFFF88"><TD>
      State:</TD><TD><html:text size="4" name="workingOrderForm" property="order.billAddr.state" />
    </TD></TR>
    <TR bgcolor="#FFFF88"><TD>
      Zip:</TD><TD><html:text size="10" name="workingOrderForm" property="order.billAddr.zipcode" />
    </TD></TR>
    <TR bgcolor="#FFFF88"><TD>
      Country: </TD><TD><html:text size="15" name="workingOrderForm" property="order.billAddr.country" />
    </TD></TR>
    
    <TR bgcolor="#FFFF88"><TD colspan=2>
        <html:checkbox name="workingOrderForm" property="shippingAddressRequired" /> Ship to different address...
    </TD></TR>
    
  </TABLE>
  <P>
  <center><input type="image" src="../images/button_submit.gif"></center>
  
</html:form>

<%@ include file="IncludeBottom.jsp" %>
