<%@ page pageEncoding="GBK" %>
<%@ include file="IncludeTop.jsp" %>

<table align="left" bgcolor="#008800" border="0" cellspacing="2" cellpadding="2">
  <tr><td bgcolor="#FFFF88">
      <a href="<c:url value="/shop/viewCategory.do">
        <c:param name="categoryName" value="${categoryName}"/>
        <%--<c:param name="categoryName" value="${requestScope['product_category.categoryName']}"/>--%>
      </c:url>">
        <b><font color="BLACK" size="2">&lt;&lt; <c:out value="${product.productName}"/></font></b>
      </a>
  </td></tr>
</table>

<p>
  
  <center>
    <b><font size="4"><c:out value="${product.productName}"/></font></b>
  </center>
  <html:form action="/shop/addItemToCart.do" method="post" >
  <table align="center" bgcolor="#008800" border="0" cellspacing="2" cellpadding="3">
    <tr bgcolor="#CCCCCC">  <td><b>Item Name</b></td>  <td><b>Product Number</b></td>  <td><b>Description</b></td>  <td><b>List Price</b></td>  <td>&nbsp;</td></tr>
    <c:forEach var="item" items="${itemList.pageList}">
    <tr bgcolor="#FFFF88">
      <td><b>
          <a href="<c:url value="/shop/viewItem.do"><c:param name="itemName" value="${item.itemName}"/></c:url>">
            <c:out value="${item.itemName}"/>
      </a></b></td>
      <td><c:out value="${product.productNumber}"/></td>
      <td>
        <c:out value="${item.attr1}"/>
        <c:out value="${item.attr2}"/>
        <c:out value="${item.attr3}"/>
        <c:out value="${item.attr4}"/>
        <c:out value="${item.attr5}"/>
        <c:out value="${product.productName}"/>
      </td>
      <td><fmt:formatNumber value="${item.listPrice}" pattern="$#,##0.00"/></td>
      
      <%-- 避免多重提交 --%>
      <td valign="bottom">
        <html:link paramId="workingItemName" paramName="item" paramProperty="itemName" 
        page="/shop/addItemToCart.do" transaction="true">
        <img border="0" src="../images/button_add_to_cart.gif" />
        </html:link>
      </td>
      <%-- 
      <td><a href="<c:url value="/shop/addItemToCart.do"><c:param name="workingItemName" value="${item.itemName}"/></c:url>">
          <img border="0" src="../images/button_add_to_cart.gif"/>
      </a></td>
      --%>
    </tr>
    </c:forEach>
    <tr><td>
        <c:if test="${!itemList.firstPage}">
        <a href="?page=previous"><font color="white"><B>&lt;&lt; Prev</B></font></a>
        </c:if>
        <c:if test="${!itemList.lastPage}">
        <a href="?page=next"><font color="white"><B>Next &gt;&gt;</B></font></a>
        </c:if>
    </td></tr>
  </table>
  </html:form>
<%@ include file="IncludeBottom.jsp" %></p></p>
