<%@ include file="IncludeTop.jsp" %>

<table align="left" bgcolor="#008800" border="0" cellspacing="2" cellpadding="2">
  <tr><td bgcolor="#FFFF88">
      <a href="<c:url value="/shop/index.do"/>"><b><font color="BLACK" size="2">&lt;&lt; Main Menu</font></b></a>
  </td></tr>
</table>

<p>
<center>
  <b>Please confirm the information below and then press continue...</b>
</center>
<p>
<table width="60%" align="center" border="0" cellpadding="3" cellspacing="1" bgcolor="#FFFF88">
  <tr bgcolor="#FFFF88"><td align="center" colspan="2">
      <font size="4"><b>Order</b></font><br />
      <font size="3">
        <b>
          <fmt:formatDate value="${workingOrderForm.order.orderDate}" 
                          pattern="yyyy/MM/dd hh:mm:ss" />
        </b>
      </font>
  </td></tr>
  
  <tr bgcolor="#FFFF88"><td colspan="2">
      <font color="GREEN" size="4"><b>Billing Address</b></font>
  </td></tr>
  <tr bgcolor="#FFFF88"><td>
    First name:</td><td><c:out value="${workingOrderForm.order.billToFirstname}"/>
  </td></tr>
  <tr bgcolor="#FFFF88"><td>
    Last name:</td><td><c:out value="${workingOrderForm.order.billToLastname}"/>
  </td></tr>
  <tr bgcolor="#FFFF88"><td>
    Address 1:</td><td><c:out value="${workingOrderForm.order.billAddr.addr1}"/>
  </td></tr>
  <tr bgcolor="#FFFF88"><td>
    Address 2:</td><td><c:out value="${workingOrderForm.order.billAddr.addr2}"/>
  </td></tr>
  <tr bgcolor="#FFFF88"><td>
    City: </td><td><c:out value="${workingOrderForm.order.billAddr.city}"/>
  </td></tr>
  <tr bgcolor="#FFFF88"><td>
    State:</td><td><c:out value="${workingOrderForm.order.billAddr.state}"/>
  </td></tr>
  <tr bgcolor="#FFFF88"><td>
    Zip:</td><td><c:out value="${workingOrderForm.order.billAddr.zipcode}"/>
  </td></tr>
  <tr bgcolor="#FFFF88"><td>
    Country: </td><td><c:out value="${workingOrderForm.order.billAddr.country}"/>
  </td></tr>
  <tr bgcolor="#FFFF88"><td colspan="2">
      <font color="GREEN" size="4"><b>Shipping Address</b></font>
  </td></tr><tr bgcolor="#FFFF88"><td>
    First name:</td><td><c:out value="${workingOrderForm.order.shipToFirstname}"/>
  </td></tr>
  <tr bgcolor="#FFFF88"><td>
    Last name:</td><td><c:out value="${workingOrderForm.order.shipToLastname}"/>
  </td></tr>
  <tr bgcolor="#FFFF88"><td>
    Address 1:</td><td><c:out value="${workingOrderForm.order.shipAddr.addr1}"/>
  </td></tr>
  <tr bgcolor="#FFFF88"><td>
    Address 2:</td><td><c:out value="${workingOrderForm.order.shipAddr.addr2}"/>
  </td></tr>
  <tr bgcolor="#FFFF88"><td>
    City: </td><td><c:out value="${workingOrderForm.order.shipAddr.city}"/>
  </td></tr>
  <tr bgcolor="#FFFF88"><td>
    State:</td><td><c:out value="${workingOrderForm.order.shipAddr.state}"/>
  </td></tr>
  <tr bgcolor="#FFFF88"><td>
    Zip:</td><td><c:out value="${workingOrderForm.order.shipAddr.zipcode}"/>
  </td></tr>
  <tr bgcolor="#FFFF88"><td>
    Country: </td><td><c:out value="${workingOrderForm.order.shipAddr.country}"/>
  </td></tr>
  
</table>
<p>
<%-- prevent duplication submit --%>
<center><html:link page="/shop/newOrderStep3.do?step=3&newOrder=true" transaction="true">
    <img border="0" src="../images/button_continue.gif" />
  </html:link>
</center>
<%--
<center><a href="<c:url value="/shop/newOrder.do"/>">
  <img border="0" src="../images/button_continue.gif" />
</a></center>
--%>
<%@ include file="IncludeBottom.jsp" %>
