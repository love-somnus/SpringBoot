package com.somnus.springboot.pay.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
/*@Table(name = "t_user_payment_channel")*/
public class PaymentChannel implements Serializable {

    private static final long serialVersionUID = -3887045404428381695L;
    private Integer id; // 渠道id
    private String keyName; // 渠道关键字
    private String name; // 渠道名称
    private String title; // 渠道显示
    private String blockId; // 页面表单ParentID
    private String blockClass; // 页面表单ParentClass
    private String formId; // 页面表单ID
    private String formValue; // 页面表单value
    private String formClass; // 页面表单class
    private String dataMps; // 页面表单data_mps
    private String dataId; // 页面表单data_id
    private String channelImg; // 渠道logo  #如果有多个使用,号分隔
    private Integer status; // 渠道状态 # 1 正常 | 0 关闭
    private Integer supportPlatform; //  # com.somnus.pay.payment.frame.enums.UAType ：pc wap app(android,ios),wxBrowser
    private Date createTime; // 渠道创建时间
    private Date updateTime; // 最后更新时间
    private Integer isUnionPayChannel; // 是否为网银支付主通道
    private Integer isFastPayChannel; // 是否为快捷支付主通道
    private Integer orderNum; // 默认排序
    private Integer displayChannelType; // 显示类型
    private boolean defaultChecked; // 是否默认选中
    private Integer thirdPayType; //第三方支付渠道
    private String desc;//相关描述

}
