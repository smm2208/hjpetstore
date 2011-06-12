<%@ page contentType="text/html;charset=GBK" %>
<%@page pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<table align="center">
    <tr>
        <td>
            <font color=gray size=-1>
                Powered by 
            </font>
        </td>
        <td>
            <a href="http://www.hibernate.org/">
                <img height="50" border="0" align="center" src="../images/poweredByHibernate.gif" alt="Powered by the Hibernate"/>
            </a>
        </td>
        <td> , <td>
        <td>
            <a href="http://www.springframework.org">
                <img height="50" border="0" align="center" src="../images/poweredBySpring.gif" alt="Powered by the Spring Framework"/>
            </a>
        </td>
        <td> , <td>
        <td>
            <a href="http://struts.apache.org/">
                <img height="40" border="0" align="center" src="../images/poweredByStruts.gif" alt="Powered by the Apache Struts"/>
            </a>
        </td>
    </tr>
</table>

<p align="center">  
    <font color=gray size=-1>
        Implemented by 
        <a href="mailto:clinton.begin@ibatis.com">Clinton Begin</a>
    </font>
    ,
    <font color=gray size=-1>
        <a href="http://springframework.org/">Spring members</a>
    </font>
    and
    <font color=gray size=-1>
        <a href="mailto:pzgyuanf@gmail.com">P.p.run</a>
    </font>
</p>

<p align="center">
<font color="green">
    Running on &nbsp; 
</font>
<c:out value="${pageContext.servletContext.serverInfo}" />

<!-- 以下　关闭 IncludeTop.jsp 中的打开的标签 -->
</body>
</html>