<%@ page pageEncoding="GBK" %>
<%@ include file="IncludeTop.jsp" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>

<%-- 加了一个 step 请求参数以便校验时作正常处理，原版本中存在 Bug --%>
<html:form action="/shop/newOrderStep2.do?step=2" styleId="workingOrderForm" method="post" >
  
  <TABLE align="center" bgcolor="#008800" border=0 cellpadding=3 cellspacing=1 bgcolor="#FFFF88">
    <TR bgcolor="#FFFF88"><TD colspan=2>
        <FONT color=GREEN size=4><B>Shipping Address</B></FONT>
    </TD></TR>
    
    <TR bgcolor="#FFFF88"><TD>
      First name:</TD><TD><html:text name="workingOrderForm" property="order.shipToFirstname" />
    </TD></TR>
    <TR bgcolor="#FFFF88"><TD>
      Last name:</TD><TD><html:text name="workingOrderForm" property="order.shipToLastname" />
    </TD></TR>
    <TR bgcolor="#FFFF88"><TD>
      Address 1:</TD><TD><html:text size="40" name="workingOrderForm" property="order.shipAddr.addr1" />
    </TD></TR>
    <TR bgcolor="#FFFF88"><TD>
      Address 2:</TD><TD><html:text size="40" name="workingOrderForm" property="order.shipAddr.addr2" />
    </TD></TR>
    <TR bgcolor="#FFFF88"><TD>
      City: </TD><TD><html:text name="workingOrderForm" property="order.shipAddr.city" />
    </TD></TR>
    <TR bgcolor="#FFFF88"><TD>
      State:</TD><TD><html:text size="4" name="workingOrderForm" property="order.shipAddr.state" />
    </TD></TR>
    <TR bgcolor="#FFFF88"><TD>
      Zip:</TD><TD><html:text size="10" name="workingOrderForm" property="order.shipAddr.zipcode" />
    </TD></TR>
    <TR bgcolor="#FFFF88"><TD>
      Country: </TD><TD><html:text size="15" name="workingOrderForm" property="order.shipAddr.country" />
    </TD></TR>
    
  </TABLE>
  <P>
  <center><input type="image" src="../images/button_submit.gif"></center>
  
</html:form>

<%@ include file="IncludeBottom.jsp" %>