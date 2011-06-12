/*
 * StringEnumUserType.java
 *
 * Created on 2006年10月1日, 下午9:27
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.springframework.samples.jpetstore.domain;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.util.ReflectHelper;
import org.hibernate.usertype.EnhancedUserType;
import org.hibernate.usertype.ParameterizedType;

/**
 * JDK5.0 枚举用户自定义类型.
 *
 * A generic UserType that handles String-based JDK 5.0 Enums.
 *
 * @author Gavin King
 * @author pprun
 */
public class StringEnumUserType implements EnhancedUserType, ParameterizedType {
  
  private Class<Enum> enumClass;
  
  public void setParameterValues(Properties parameters) {
    //<typedef class="org.springframework.samples.jpetstore.domain.hibernate.StringEnumUserType" name="rating">
    // 		<param name="enumClassname">org.springframework.samples.jpetstore.domain.hibernate.ShipmentState</param>
    // 	</typedef>
    String enumClassName = parameters.getProperty("enumClassname");
    try {
      enumClass = ReflectHelper.classForName(enumClassName);
    } catch (ClassNotFoundException cnfe) {
      throw new HibernateException("Enum class not found", cnfe);
    }
  }
  
  
  public Class returnedClass() {
    return enumClass;
  }
  
  public int[] sqlTypes() {
    return new int[] { Hibernate.STRING.sqlType() };
  }
  
  public boolean isMutable() {
    return false;
  }
  
  public Object deepCopy(Object value) {
    return value;
  }
  
  public Serializable disassemble(Object value) {
    return (Enum) value;
  }
  
  public Object replace(Object original, Object target, Object owner) {
    return original;
  }
  
  public Object assemble(Serializable cached, Object owner) {
    return cached;
  }
  
  public boolean equals(Object x, Object y) {
    return x==y;
  }
  
  public int hashCode(Object x) {
    return x.hashCode();
  }
  
  public Object fromXMLString(String xmlValue) {
    return Enum.valueOf(enumClass, xmlValue);
  }
  
  public String objectToSQLString(Object value) {
    return '\'' + ( (Enum) value ).name() + '\'';
  }
  
  public String toXMLString(Object value) {
    return ( (Enum) value ).name();
  }
  
  public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
  throws SQLException {
    String name = rs.getString( names[0] );
    return rs.wasNull() ? null : Enum.valueOf(enumClass, name);
  }
  
  public void nullSafeSet(PreparedStatement st, Object value, int index)
  throws SQLException {
    if (value==null) {
      st.setNull(index, Hibernate.STRING.sqlType());
    } else {
      st.setString( index, ( (Enum) value ).name() );
    }
  }
  
}