<%@ page contentType="text/html;charset=GBK" %>
<%@page pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>

<html><head><title>Hibernate JPetStore Demo</title>
  <meta content="text/html; charset=GBK" http-equiv="Content-Type" />
  <META HTTP-EQUIV="Cache-Control" CONTENT="max-age=0">
  <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
  <meta http-equiv="expires" content="0">
  <META HTTP-EQUIV="Expires" CONTENT="Tue, 01 Jan 1980 1:00:00 GMT">
  <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
</head>
<body bgcolor="white">

<table background="../images/bkg-topbar.gif" border="0" cellspacing="0" cellpadding="5" width="100%">
  <tbody>
    <tr>
      <td><a href="<c:url value="/shop/index.do"/>">
          <img border="0" src="../images/logo-topbar.gif" />
        </a>
      </td>
      
      <td align="right" valign="bottom">
        <table border="0" cellpadding="0" cellspacing="0" >
          <tr>
            
            <a href="<c:url value="/shop/viewCart.do"/>">
              <img border="0" name="img_cart" src="../images/cart.gif" />
            </a>
            <img border="0" src="../images/separator.gif" />
            
            <c:if test="${empty accountForm.account}" >
              <a href="<c:url value="/shop/signonForm.do"/>">
                <img border="0" name="img_signin" src="../images/sign-in.gif" />
              </a>
            </c:if>
            
            <c:if test="${!empty accountForm.account}" >
              <a href="<c:url value="/shop/signon.do?signoff=true"/>">
                <img border="0" name="img_signout" src="../images/sign-out.gif" />
              </a>
              <img border="0" src="../images/separator.gif" />
              <a href="<c:url value="/shop/editAccountForm.do"/>">
                <img border="0" name="img_myaccount" src="../images/my_account.gif" />
              </a>
            </c:if>
            
            <img border="0" src="../images/separator.gif" />
            <a href="<c:url value="/shop/help.do"/>" target="new">
              <img border="0" name="img_help" src="../images/help.gif" />
            </a>
            
            <form action="<c:url value="/shop/searchProducts.do"/>" method="post">
              <input type="hidden" name="search" value="true"/>
              <input name="keyword" size="14"/>
              <input border="0" src="../images/search.gif" type="image" name="search"/>
            </form>
          </tr>
        </table>
      </td>
    </tr>
  </tbody>
</table>

<%@ include file="IncludeQuickHeader.jsp" %>

<!-- Support for non-traditional but simpler use of errors... -->
<c:if test="${!empty errors}">
  <br />
  <table align="center">
    <tr align="center">
      <td align="left">
      <ul> 
        <c:forEach var="error" items="${errors}">
          <li>
          <FONT color="orange">
            <c:out value="${error}"/>
          </FONT>
        </c:forEach>
      </ul>
      </td>
    </tr>
  </table>
</c:if>
