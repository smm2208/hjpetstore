<%@ include file="IncludeTop.jsp" %>
<center>
  <H3><font color="red">Error!</font></H3>
  <p>
    <font color="orange">
          <c:out value="${message}" default="No further information was provided."/>
          </font>
  </p>
  <br />
  Go to index page 
  <a href="<c:url value="/shop/index.do"/>">here</a>.
</center>
<%@ include file="IncludeBottom.jsp" %>
