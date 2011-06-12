<%@ page pageEncoding="UTF8" %>
<%@ include file="IncludeTop.jsp" %>

<table align="left" bgcolor="#008800" border="0" cellspacing="2" cellpadding="2">
  <tr><td bgcolor="#FFFF88">
      <a href="<c:url value="/shop/index.do"/>"><b><font color="BLACK" size="2">&lt;&lt; Main Menu</font></b></a>
  </td></tr>
  <tr><td bgcolor="#FFFF88">
      <%--
<html:link paramId="id" paramName="order" paramProperty="id" page="/shop/viewOrder.do?webservice=true"><b><font color="BLACK" size="2">Use Web Service</font></b></c:url>
      --%>
  </td></tr>
</table>

<c:if test="${!empty message}">
  <center><b><c:out value="${message}"/></b></center>
</c:if>

<p>

<table width="60%" align="center" border="0" cellpadding="3" cellspacing="1" bgcolor="#FFFF88">
  <tr bgcolor="#FFFF88"><td align="center" colspan="2">
      <font size="4"><b>Order #<c:out value="${order.id}"/></b></font>
      <br /><font size="3"><b><fmt:formatDate value="${order.orderDate}" pattern="yyyy/MM/dd hh:mm:ss" /></b></font>
  </td></tr>
  <tr bgcolor="#FFFF88"><td colspan="2">
      <font color="GREEN" size="4"><b>Payment Details</b></font>
  </td></tr>
  <tr bgcolor="#FFFF88"><td>
    Card Type:</td><td>
      <c:out value="${order.cardType}"/>
  </td></tr>
  <tr bgcolor="#FFFF88"><td>
    Card Number:</td><td><c:out value="${order.cardNumber}"/> <font color="red" size="2">* Fake number!</font>
  </td></tr>
  <tr bgcolor="#FFFF88"><td>
    Expiry Date (MM/YYYY):</td><td><c:out value="${order.expireDate}"/>
  </td></tr>
  <tr bgcolor="#FFFF88"><td colspan="2">
      <font color="GREEN" size="4"><b>Billing Address</b></font>
  </td></tr>
  <tr bgcolor="#FFFF88"><td>
    First name:</td><td><c:out value="${order.billToFirstname}"/>
  </td></tr>
  <tr bgcolor="#FFFF88"><td>
    Last name:</td><td><c:out value="${order.billToLastname}"/>
  </td></tr>
  <tr bgcolor="#FFFF88"><td>
    Address 1:</td><td><c:out value="${order.billAddr.addr1}"/>
  </td></tr>
  <tr bgcolor="#FFFF88"><td>
    Address 2:</td><td><c:out value="${order.billAddr.addr2}"/>
  </td></tr>
  <tr bgcolor="#FFFF88"><td>
    City: </td><td><c:out value="${order.billAddr.city}"/>
  </td></tr>
  <tr bgcolor="#FFFF88"><td>
    State:</td><td><c:out value="${order.billAddr.state}"/>
  </td></tr>
  <tr bgcolor="#FFFF88"><td>
    Zip:</td><td><c:out value="${order.billAddr.zipcode}"/>
  </td></tr>
  <tr bgcolor="#FFFF88"><td>
    Country: </td><td><c:out value="${order.billAddr.country}"/>
  </td></tr>
  <tr bgcolor="#FFFF88"><td colspan="2">
      <font color="GREEN" size="4"><b>Shipping Address</b></font>
  </td></tr><tr bgcolor="#FFFF88"><td>
    First name:</td><td><c:out value="${order.shipToFirstname}"/>
  </td></tr>
  <tr bgcolor="#FFFF88"><td>
    Last name:</td><td><c:out value="${order.shipToLastname}"/>
  </td></tr>
  <tr bgcolor="#FFFF88"><td>
    Address 1:</td><td><c:out value="${order.shipAddr.addr1}"/>
  </td></tr>
  <tr bgcolor="#FFFF88"><td>
    Address 2:</td><td><c:out value="${order.shipAddr.addr2}"/>
  </td></tr>
  <tr bgcolor="#FFFF88"><td>
    City: </td><td><c:out value="${order.shipAddr.city}"/>
  </td></tr>
  <tr bgcolor="#FFFF88"><td>
    State:</td><td><c:out value="${order.shipAddr.state}"/>
  </td></tr>
  <tr bgcolor="#FFFF88"><td>
    Zip:</td><td><c:out value="${order.shipAddr.zipcode}"/>
  </td></tr>
  <tr bgcolor="#FFFF88"><td>
    Country: </td><td><c:out value="${order.shipAddr.country}"/>
  </td></tr>
  <tr bgcolor="#FFFF88"><td>
    Courier: </td><td><c:out value="${order.courier}"/>
  </td></tr>
  <tr bgcolor="#FFFF88"><td colspan="2">
      <b><font color="GREEN" size="4">Status:</font> <c:out value="${order.shipStatus}"/></b>
  </td></tr>
  <tr bgcolor="#FFFF88"><td colspan="2">
      <table width="100%" align="center" bgcolor="#008800" border="0" cellspacing="2" cellpadding="3">
        <tr bgcolor="#CCCCCC">
          <td><b>Item Name</b></td>
          <td><b>Description</b></td>
          <td><b>Quantity</b></td>
          <td><b>Price</b></td>
          <td><b>Total Cost</b></td>
        </tr>
        
        <%-- 通过帐户信息页中的 viewOrder 进入到此页面 --%>
        <c:if test="${!empty viewOrder}">
          <%--
        Table 5.5 The loop status variable (exposed by the varStatus attribute) 
        includes properties for determining information about the current loop, 
        such as whether it¡¯s the first or last iteration. 
        You can also use it to determine the position of the current 
        item in its original collection. 
        Property    Type      Description
        ------------------------------------------
        index       number    The index of the current item in the underlying collection
        count       number    The position of the current round of iteration, starting with 1
        first       boolean   Whether the current round of iteration is the first
        last        boolean   Whether the current round of iteration is the last
        --%>
          <c:forEach var="lineItem" items="${order.lineItems}" varStatus="i">
            <tr bgcolor="#FFFF88">
              <td><b><a href="<c:url value="/shop/viewItem.do">
                              <c:param name="itemName" value="${items[i.count-1].itemName}"/>
                            </c:url>">
                    <font color="BLACK"><c:out value="${items[i.count-1].itemName}"/></font>
              </a></b></td>
              <td>
                <c:out value="${items[i.count-1].attr1}"/>
                <c:out value="${items[i.count-1].attr2}"/>
                <c:out value="${items[i.count-1].attr3}"/>
                <c:out value="${items[i.count-1].attr4}"/>
                <c:out value="${items[i.count-1].attr5}"/>
                <c:out value="${productNames[i.count-1]}"/>
              </td>
              <td><c:out value="${lineItem.quantity}"/></td>
              
              <%-- 
          ${lineItem.unitPrice} 调用的 LineItem 中的方法getUnitPrice() 
          --%>
              <td align="right"><fmt:formatNumber value="${lineItem.unitPrice}" pattern="$#,##0.00"/></td>
              <td align="right"><fmt:formatNumber value="${lineItem.totalPrice}" pattern="$#,##0.00"/></td>
            </tr>
          </c:forEach>
        </c:if>
        
        <%-- 正常购物流程进入到此页面 --%>
        <c:if test="${!empty newOrder}">
          <c:forEach var="lineItem" items="${order.lineItems}" varStatus="i">
            <tr bgcolor="#FFFF88">
              <td><b><a href="<c:url value="/shop/viewItem.do">
                              <c:param name="itemName" value="${lineItem.item.itemName}"/>
                            </c:url>">
                    <font color="BLACK"><c:out value="${lineItem.item.itemName}"/></font>
              </a></b></td>
              <td>
                <c:out value="${lineItem.item.attr1}"/>
                <c:out value="${lineItem.item.attr2}"/>
                <c:out value="${lineItem.item.attr3}"/>
                <c:out value="${lineItem.item.attr4}"/>
                <c:out value="${lineItem.item.attr5}"/>
                <c:out value="${lineItem.item.product.productName}"/>
              </td>
              <td><c:out value="${lineItem.quantity}"/></td>
              
              <td align="right"><fmt:formatNumber value="${lineItem.unitPrice}" pattern="$#,##0.00"/></td>
              <td align="right"><fmt:formatNumber value="${lineItem.totalPrice}" pattern="$#,##0.00"/></td>
            </tr>
          </c:forEach>
        </c:if>
        <tr bgcolor="#FFFF88">
          <td colspan="5" align="right"><b>Total: <fmt:formatNumber value="${order.totalPrice}" pattern="$#,##0.00"/></b></td>
        </tr>
      </table>
  </td></tr>
  
</table>

<%@ include file="IncludeBottom.jsp" %>