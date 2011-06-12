<%@ include file="IncludeTop.jsp" %>

<table align="left" bgcolor="#008800" border="0" cellspacing="2" cellpadding="2">
  <tr><td bgcolor="#FFFF88">
      <a href="<c:url value="/shop/index.do"/>"><b><font color="BLACK" size="2"/>&lt;&lt; Main Menu</font></b></a>
  </td></tr>
</table>

<p>
  <center>
    <h2><c:out value="${category.categoryName}"/></h2>
  </center>
  <table align="center" bgcolor="#008800" border="0" cellspacing="2" cellpadding="3">
    <tr bgcolor="#CCCCCC">  <td><b>Product Number</b></td>  <td><b>Name</b></td>  </tr>
    <c:forEach var="product" items="${productList.pageList}">
      <tr bgcolor="#FFFF88">
        <td><b><a href="<c:url value="/shop/viewProduct.do">
                        <c:param name="productNumber" value="${product.productNumber}"/>
                      </c:url>">
              <font color="BLACK"><c:out value="${product.productNumber}"/></font>
        </a></b></td>
        <td><c:out value="${product.productName}"/></td>
      </tr>
    </c:forEach>
    <tr><td>
        <c:if test="${!productList.firstPage}">
          <a href="?page=previous"><font color="white"><B>&lt;&lt; Prev</B></font></a>
        </c:if>
        <c:if test="${!productList.lastPage}">
          <a href="?page=next"><font color="white"><B>Next &gt;&gt;</B></font></a>
        </c:if>
    </td></tr>
  </table>
  
<%@ include file="IncludeBottom.jsp" %></p></p>
