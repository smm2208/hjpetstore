# Design Document #
> Download:
  * [PDF](http://hjpetstore.googlecode.com/files/young-architect.pdf)
  * [ODT(Open Office)](http://hjpetstore.googlecode.com/files/young-architect.odt)
  * [DOC(MS Word)](http://hjpetstore.googlecode.com/files/young-architect.doc)

> ![http://hjpetstore.googlecode.com/files/pdf.png](http://hjpetstore.googlecode.com/files/pdf.png)
> <br /><br />

# Description #
> <p>
<blockquote>The following technologies are being used in the current implementation: <br /></blockquote></li></ul>

<blockquote><ul>
<blockquote><li><a href='http://maven.apache.org/'>Maven based build architecture</a></li>
<li><a href='http://jquery.com/'>JQuery fisheye</a></li>
<li><a href='http://code.google.com/p/kaptcha/'>kaptcha</a></li>
<li><a href='http://static.springsource.org/spring/docs/3.0.x/spring-framework-reference/htmlsingle/spring-framework-reference.html#mvc'>Spring MVC 3</a></li>
<li><a href='http://hi.baidu.com/quest2run/blog/item/2e1cd8266c2e997335a80f7d.html'>OAuth</a></li>
<li><a href='http://opensource.atlassian.com/projects/hibernate/browse/HHH/fixforversion/11051'>Hibernate 3.5</a></li>
<li><a href='http://community.jboss.org/wiki/ReferenceManualJBossCache3asaHibernate35SecondLevelCache'>JBoss Cache 3</a><a href='http://community.jboss.org/wiki/usinginfinispanasjpahibernatesecondlevelcacheprovider'>(alternative infinispan)</a></li>
<li><a href='https://mq.dev.java.net/'>JMS External Integration</a></li>
<li><a href='https://glassfish.dev.java.net/'>GlassFish 3 cluster</a></li>
<li><a href='http://dev.mysql.com/doc/refman/5.1/en/mysql-cluster.html'>Mysql fail-over and cluster</a></li>
<li><a href='http://www.zabbix.org/'>Zabbix</a> / <a href='http://www.kjkoster.org/zapcat/Zapcat_JMX_Zabbix_Bridge.html'>Zapcat</a></li></blockquote></blockquote>

> </ul>
<blockquote><p>
<blockquote><a href='http://hi.baidu.com/quest2run/blog/item/2526f64672654a2bcefca3a4.html'>Check out the source code</a>
</blockquote></p>
> <p>
<blockquote><a href='http://hi.baidu.com/quest2run/blog/item/7d3cdd8106f300b26d8119fb.html'>build and deploy</a>
</blockquote></p>

> <p>
<blockquote><i>The deprecated <a href='index-v1.html'>hjpetstore v1</a> (elementary combined with Hibernate, Spring, Strut1) is<br>
still available and if you are Chinese, a thoroughly introduction can be found in the chapter 17 of book<br>
<a href='http://www.china-pub.com/37805'>零基础学Java Web开发 (刘聪)</a>.<br>
</i>
</blockquote></p>
> <br />
> <p>The following are the screenshots for hjpetstore 2.0</p>

# Requriements #
> ## DataBase ##
> > <ul>
<blockquote><li>Mysql 5.x</li>
</blockquote><blockquote></ul>
</blockquote>
> ## Java EE (J2EE) server / Servlet Container ##
> > <ul>
<blockquote><li>GlassFish v3</li></blockquote></blockquote>


> </ul>
<blockquote>Depends on JTA datasource that would be the JNDI resource set up in GlassFish, this is the requirement of Jboss Transaction cache.<br>
<br></blockquote>

<h1>Documentation</h1>
<blockquote>All configuration documentation will be composed and put on my blog <a href='http://hi.baidu.com/quest2run'> P.P.Run </a>, which will cover: <br /></blockquote>


<blockquote><b>Architecture design</b>
<ol>
<blockquote><li>load balance in Web layer</li>
<li>cluster and failover in Database and Application Server</li>
<li>ERD</li>
<li>security</li>
<li>transaction model and strategy, including discussion of business transaction (long term transaction)</li>
</blockquote></ol></blockquote>

<blockquote><b>Implementation detail</b>
<ol>
<blockquote><li>Hibernate mapping trick and performance consideration</li>
<li>JMS loose-coupling Integration</li>
<li>Spring MVC 3 Restful</li>
<li>Spring security 3</li>
<li>Jboss second level cache</li>
<li>Spring RestTemplate (the Restful client)</li>
</blockquote></ol></blockquote>

<h1>Run Instructions</h1>
<blockquote><b>Setup before run</b>
<ol>
<blockquote><li>Download NetBeans with GlassFish and install, if you still have not</li>
<li>Download Mysql and install, if you still have not</li>
<li>Mysql intializaton,<br>
<blockquote>see <a href='http://hjpetstore.googlecode.com/files/mysql_readme.txt'>mysql readme</a>
</blockquote></li></blockquote></blockquote>

<blockquote><li>install maven, if you still have not<br>
<blockquote>Several artifacts missed in community maven repository, we need to import them into your local repository,<br>
see <a href='http://hjpetstore.googlecode.com/files/maven-readme.txt'>maven readme.txt</a></li>
</blockquote></blockquote><blockquote></ol></blockquote>

<blockquote><b>Glassfish configurations</b>
<ol>
<blockquote><li>JGroup ip v6 issue: （only linux) change glassfish to disable ipv6, otherwise jgroup will be failed.<br>
<blockquote>start up glassfish admin console if still not <br />
JVM setting -> JVM options -> add JVM option： -Djava.net.preferIPv4Stack=true<br>
</blockquote></li>
<li>Mysql connectorJ </li>
Download Connector/J, the MySQL JDBC driver (e.g., Connector/J 5.1.x), which can be found here: <a href='http://dev.mysql.com/downloads/connector/j/'>http://dev.mysql.com/downloads/connector/j/</a>
added mysql-connector-java-xxx.jar into glassfish-3.0.1/glassfish/lib for datasource setup<br>
<li> Create XA datasource 'jdbc/hjpetstore',<br>
<blockquote>see <a href='http://hi.baidu.com/quest2run/blog/item/4cc48eff5e6e368fb801a009.html'>Create JNDI resource for hjpetstore 2.0</a></li>
</blockquote><li>Create JMS resource,<br>
<blockquote>see <a href='http://hi.baidu.com/quest2run/blog/item/4cc48eff5e6e368fb801a009.html'>Create JNDI resource for hjpetstore 2.0</a></li>
</blockquote><li>Launch the project <br />
<blockquote>(assume you downloaded the hjpetstore 2.0) <br />
In Netbeans, right click the project -> run</li></blockquote></blockquote></blockquote>

<blockquote></ol></blockquote>

<h1>Screenshoots</h1>
<blockquote><b>Fish eye </b>
<br />
<br /></blockquote>

<blockquote><img src='http://hjpetstore.googlecode.com/files/fish.png' />
<br>
<br></blockquote>

<blockquote><img src='http://hjpetstore.googlecode.com/files/dog.png' />
<br>
<br></blockquote>

<blockquote><br>
<img src='http://hjpetstore.googlecode.com/files/cat-bird.png' />
<br>
<br></blockquote>

<blockquote><b>Kaptcha</b></blockquote>

<blockquote><img src='http://hjpetstore.googlecode.com/files/kaptcha.png' />
<br>
<br></blockquote>


<blockquote><b>Spring MVC 3 RestTemplate Client</b>
<p>which requests a RESTful controller SearchProducts of Hjpetstore2 and<br>
<blockquote>uses Xpath parse the xml response and then send another Rest request<br>
to pull all image which url contained in the previous response,<br>
then use org.springframework.http.converter.BufferedImageHttpMessageConverter<br>
to return a list of BufferedImage for rending in Swing Component:<br>
</blockquote></p><br />
<img src='http://hjpetstore.googlecode.com/files/rest-client.png' />
</blockquote><blockquote><br></blockquote>
