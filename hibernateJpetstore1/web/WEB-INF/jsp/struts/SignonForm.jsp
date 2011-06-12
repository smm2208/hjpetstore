<%@page pageEncoding="GBK"%> <!-- 为了使中文的注释正确保存和显示，必须加这行！又一个对中文的侮辱。 -->
<%@ include file="IncludeTop.jsp" %>

<c:if test="${!empty message}">
<b><font color="RED"><c:url value="${message}"/></font></b>
</c:if>

<form action="<c:url value="/shop/signon.do"/>" method="POST">
  <!-- 保存用户真正要去的地方，主要用于那些没有登录的用户，直接想进行与帐户相关的
       操作，也有可能是用户使用了 bookmark 
  -->
  <c:if test="${!empty signonForwardAction}">
  <input type="hidden" name="forwardAction" value="<c:url value="${signonForwardAction}"/>"/>
  </c:if>
      
  <table align="center" border="0">
    <tr>
      <td colspan="2">Please enter your username and password.
      <br />&nbsp;</td>
    </tr>
    <tr>
      <td>Username:</td>
      <td><input type="text" name="username" value="test" /></td> 
    </tr>
    
    <!-- 仅为了演示方便，产品时不能暴露安全特性 -->
    <tr>
      <td>Password:</td>
      <td><input type="password" name="password" value="test" /></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td><input type="image" border="0" src="../images/button_submit.gif" name="update" /></td>
    </tr>
  </table>
  
</form>

<center>
  <a href="<c:url value="/shop/newAccountForm.do"/>">
    <img border="0" src="../images/button_register_now.gif" />
  </a>
</center>

<%@ include file="IncludeBottom.jsp" %>

