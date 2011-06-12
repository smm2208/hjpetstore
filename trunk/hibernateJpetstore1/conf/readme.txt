+Web
    |---------------- index.jsp 前端跳转首页
    |---------------- images 图片目录
    |---------------- +META-INF 应用装配配置文件
    |                                   |---------- context.xml 特于此应用的上下文配置，此例包括在使用 Tomcat 数据源的配置(早先时候的
    |---------------- WEB-INF

应用配置说明
============

# ================ Mysql 数据库初始化 =================

# 在命令行下以 root 身份运行
C:\Documents and Settings\pprun > mysql -h localhost -u root -p < $hjpetstore\conf\jpetstore_mysql.sql
Enter password: ********


# ======== 不同应用服务器/web服务器和数据库间切换 ========

1. 最简单的方式就是使用其默认配置(DBCP 本地连接池) tomcat + HSQLDB, 几乎不用配，
　　但产品时还是得使用其它应用服务器和数据库的。

2. 使用 tomcat+mysql 应该说还是比较容易，只要在jdbc.properties 文件中将相应的
    参数配好就行，唯一不不足的地方，每次重新部署的第一次总是不成功的。
　　忽略错误，再来一次，就可以了。

3. 使用 tomcat + oracle 同上，只要将 ORCL 的参数在 jdbc.properties 中配置好就行。

4. 使用 Sun App Server 的目的当然是为了使用其 JTA(包容器管理的事务及其数据库连接
   池的实现），只需要按正确的名称　(mysql)jdbc/hjpetstore 
   在管理界面配好数据库连接池和相应的数据源，运行起来还是挺方便的,
   除了在 web.xml 中按标准的方式声明：
  <resource-ref>
    <res-ref-name>jdbc/hjpetstore</res-ref-name>
    <res-type>javax.sql.DataSource</res-type>
    <res-auth>Container</res-auth>
  </resource-ref>
  
   外，IDE好象自己会生成 sun-web.xml文件，并在其中有：
  <resource-ref>
    <res-ref-name>jdbc/jpetstore-mysql</res-ref-name>
    <jndi-name>jdbc/jpetstore-mysql</jndi-name>
  </resource-ref>
 　数据源引用定义.

5. 使用 JBoss 当然也是为了上述同样的目的，也许是冲着所谓的 #1 应用服务器而来的吧，
　　但其配置有一些变化：
   第一它实现了自己的一套日志方式，所以需要把 web.xml 中的
   <listener>
    <listener-class>org.springframework.web.util.Log4jConfigListener</listener-class>
  </listener>
  注释掉。

6. 它的数据源的配法也不相同，只需要将相应的数据库的配置文件(如：mysql-ds.xml)丢到 
   jboss-4.0.4.GA\server\default\deploy 目录下，
   再在 jboss-4.0.4.GA\server\default\conf\login-config.xml 中加入:
  
    <application-policy name = "MySqlDbRealm">
      <authentication>
         <login-module code = "org.jboss.resource.security.ConfiguredIdentityLoginModule" flag = "required">
             <module-option name ="principal">hjpetstore</module-option>
             <module-option name ="userName">hjpetstore</module-option>
             <module-option name ="password">hjpetstore</module-option>
             <module-option name ="managedConnectionFactoryName">jboss.jca:service=LocalTxCM,name=hjpetstore-mysql</module-option>
         </login-module>
      </authentication>
    </application-policy>

    mysql-ds.xml 相应的内容如下：
    <?xml version="1.0" encoding="UTF-8"?>
    <!-- $Id: readme.txt,v 1.1.1.1 2010/07/16 07:47:08 pprun Exp $ -->
    <!--  Datasource config for MySQL using 3.0.9 available from:
    http://www.mysql.com/downloads/api-jdbc-stable.html
    -->

    <datasources>
      <local-tx-datasource>
        <jndi-name>hjpetstore-mysql</jndi-name>
        <connection-url>jdbc:mysql://localhost:3306/hjpetstore</connection-url>
        <driver-class>com.mysql.jdbc.Driver</driver-class>
        <user-name>hjpetstore</user-name>
        <password>hjpetstore</password>
        <exception-sorter-class-name>org.jboss.resource.adapter.jdbc.vendor.MySQLExceptionSorter</exception-sorter-class-name>
        <!-- should only be used on drivers after 3.22.1 with "ping" support
        <valid-connection-checker-class-name>org.jboss.resource.adapter.jdbc.vendor.MySQLValidConnectionChecker</valid-connection-checker-class-name>
        -->
        <!-- sql to call when connection is created
        <new-connection-sql>some arbitrary sql</new-connection-sql>
          -->
        <!-- sql to call on an existing pooled connection when it is obtained from pool - MySQLValidConnectionChecker is preferred for newer drivers
        <check-valid-connection-sql>some arbitrary sql</check-valid-connection-sql>
          -->

        <!-- corresponding type-mapping in the standardjbosscmp-jdbc.xml (optional for ejb) -->
        <metadata>
           <type-mapping>mySQL</type-mapping>
        </metadata>
      </local-tx-datasource>
    </datasources>

7. 还有，就是JBoss 的 JNDI 的名称有些怪：

　<!-- JBoss must be this -->
  <jee:jndi-lookup id="dataSource" jndi-name="java:/hjpetstore-mysql"/>

　而常规的为：
  <jee:jndi-lookup id="dataSource" jndi-name="java:comp/env/jdbc/hjpetstore-mysql"/>
  
  但其既不需要在 web.xml中定义，也不需要在 jboss-web.xml 中定义相关氖菰匆茫�
  感觉很奇怪，是不是上面的步骤完成后就不需要配这些了？


使用 jta（即包容器的数据源时）的切换步骤
----------------------------------------
1. Sun App Server + mysql
 a. web.xml
　　存在： 
 -- 
    <resource-ref>
    <res-ref-name>jdbc/hjpetstore</res-ref-name>
    <res-type>javax.sql.DataSource</res-type>
    <res-auth>Container</res-auth>
   </resource-ref> 
 --
  <listener>
    <listener-class>org.springframework.web.util.Log4jConfigListener</listener-class>
  </listener>

 --     <!-- jta DataSource and JtaTransactionManager -->
    <param-value>
      /WEB-INF/dataAccessContext-jta-1.xml  /WEB-INF/applicationContext.xml
    </param-value>
 b. sun-web.xml 中存在：
   <resource-ref>
    <res-ref-name>jdbc/hjpetstore-mysql</res-ref-name>
    <jndi-name>jdbc/hjpetstore-mysql</jndi-name>
  </resource-ref>

 c. dataAccessContext-jta-1.xml 中使用：
   <jee:jndi-lookup id="dataSource" jndi-name="java:comp/env/jdbc/hjpetstore-mysql"/>
   而不是　
  <!-- JBoss must be this -->
  <jee:jndi-lookup id="dataSource" jndi-name="java:/hjpetstore-mysql"/>
  
2. JBoss + mysql
 a. web.xml
 --　注释掉所有与数据源相关的元素： 
     <resource-ref> ... </resource-ref>

 --  注掉日志监听器
  <listener>
    <listener-class>org.springframework.web.util.Log4jConfigListener</listener-class>
  </listener>

 -- 同样保证使用：
 　<!-- jta DataSource and JtaTransactionManager -->
    <param-value>
      /WEB-INF/dataAccessContext-jta-1.xml  /WEB-INF/applicationContext.xml
    </param-value>

 b. dataAccessContext-jta-1.xml 中不是使用：
   <jee:jndi-lookup id="dataSource" jndi-name="java:comp/env/jdbc/hjpetstore-mysql"/>
   而是使用：　
  <!-- JBoss must be this -->
  <jee:jndi-lookup id="dataSource" jndi-name="java:/hjpetstore-mysql"/>

3. Oracle 相关的操作与上述同，只是　
　在数据源引用的时改为oracle 的配置，因为我特意加以了区分, -mysql 为后缀的即是
 mysql 相关，其它为 oracle 相关


后记
====
JBoss NO. 1 是徒有虚名吗？不，感觉在同样的配置下，的确要比 Sun App server 快些。

############# 在 Spring web 与 Struts web 间切换############### 
1. 原应用只需将
  <servlet-mapping>
    <!-- Spring web -->
    <servlet-name>petstore</servlet-name>
    
    <!-- Struts web 
    <servlet-name>action</servlet-name>
    -->	
   <url-pattern>*.do</url-pattern>
  </servlet-mapping>
 该元素设置为自己喜欢的框架就可，因在 Spring 的所有视图映射中也采用了 *.do 
 在这原本是 Struts 发明的后缀名映射方式

 2. 但通常 Spring 的映射方式是采用 .htm/.html 方式，所以我做了一下改动
 - 将 Spring 的页面中的 .do , 改成了 *.html
 - 在  petstore-servlet-xml 中的所有 .do 改成了 .html

 并且分别在各自的页脚里加入跳转到对方的链接, 这样就可以动态在两个框架之间切换

 所以如果是这样的话，设置就是如下：
a.
  <servlet-mapping>
    <!-- Spring web -->
    <servlet-name>petstore</servlet-name>
    <url-pattern>*.html</url-pattern>
  </servlet-mapping>
  
  <!-- Struts web -->	
  <servlet-mapping>
    <servlet-name>action</servlet-name>
    <url-pattern>*.do</url-pattern>
  </servlet-mapping>


  注意各处使用了自己的习惯的映射方式

 b.在 WEB 根下，增加一首页 index.jsp, 内容如下：
<%-- for 产品首页 --%>
<%@page contentType="text/html; charset=GBK"%>
<%@page pageEncoding="GBK"%>
<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<%-- selecting the default web framework --%>
<%-- Spring web --%>
<c:redirect url="/shop/index.html"/>

<%-- Struts web 
<c:redirect url="/shop/index.do"/>
--%>

 这样也就跳过了原来的index.html 的说明，直接跳转到默认框架容器里。


