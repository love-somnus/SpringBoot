package com.somnus.springboot.pay.service;

import java.util.Map;

import com.somnus.springboot.pay.enums.CallbackStatus;
import com.somnus.springboot.pay.pojo.Callback;
import com.somnus.springboot.pay.pojo.Callback.CallbackId;
import com.somnus.springboot.pay.pojo.Page;
import com.somnus.springboot.pay.pojo.QueryResult;

/**
 * @ClassName:     CallbackService.java
 * @Description:   第三方支付渠道服务
 * @author         Somnus
 * @version        V1.0  
 * @Date           2016年12月28日 下午1:40:02
 */
public interface CallbackService {

    /**
     * 批量保存回调通知
     * @param callbacks
     */
    public void save(Callback... callbacks);

    /**
     * 更新回调通知
     * @param callback
     */
    public void update(Callback callback);

    /**
     * 根据ID更新支付回调通知状态
     * @param id
     * @param status
     * @param memo
     */
    public void changeStatus(CallbackId id, CallbackStatus status, String memo);

    /**
     * 根据ID获取支付回调通知
     * @param id
     * @return
     */
    public Callback get(CallbackId id);

    /**
     * 分页查询所有支付回调
     * @param page
     * @return
     */
    public QueryResult<Callback> list(Page page,Map<String,Object> params);

    /**
     * 指定的支付回调通知是否已存在
     * @param id
     * @return
     */
    public boolean exist(CallbackId id);

}
