/*
 * ShipStatus.java
 *
 * Created on 2006年10月1日, 下午9:34
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.springframework.samples.jpetstore.domain;

/**
 * 发货状态枚举：
 * <p>
 *
 * <li>未决: 交易货单还未通过校验
 * <li>商定: 交易双方达达一致，货单创建
 * <li>已付款：买方已经付了此交易的款项
 * <li>运送中: 卖方已经将物品发出
 * <li>收到: 买方言已经在规定的周期内收到物品
 * <li>交易完成: 交易的款项已到达卖方，交易完成
 *
 * State of a shipment under escrow（货单/契约）.
 *
 * @author pprun
 */
public enum ShipStatus {
    /** 未决: 交易货单还未通过校验 */
    PENDING("PENDING"),
    /** 商定: 交易双方达达一致，货单创建 */
    AGREED("AGREED"),
    /** 已付款：买方已经付了此交易的款项 */
    PAYED("PAYED"), 
    /** 运送中: 卖方已经将物品发出 */
    IN_TRANSIT("IN_TRANSIT"),
    /** 收到: 买方言已经在规定的周期内收到物品 */
    ACCEPTED("ACCEPTED"),
    /** 交易完成: 交易的款项已到达卖方，交易完成 */
    COMPLETE("COMPLETE");
    
    private final String state;
    
    private ShipStatus(String state) {
      this.state = state;
    }
    
    public String value() {
      return state;
    }
    
    public String toString() {
      return state;
    }
}
