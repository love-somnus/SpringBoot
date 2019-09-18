package com.somnus.springboot.pay.service;

import java.util.List;
import java.util.Map;

import com.somnus.springboot.pay.enums.PayChannel;
import com.somnus.springboot.pay.pojo.Page;
import com.somnus.springboot.pay.pojo.QueryResult;
import com.somnus.springboot.pay.pojo.Switch;
import com.somnus.springboot.pay.thirdpay.RequestType;

public interface PaymentChannelSwitchService {

    public void toggle(PayChannel channel, RequestType type);

    public void toggle(String key);

    public void setValue(PayChannel channel, RequestType type, boolean value);

    public boolean getValue(PayChannel channel, RequestType type);

    public List<Switch> getAll();

    public QueryResult<Switch> list(Page page);

    public QueryResult<Switch> list(Page page, Map<String, Object> params);

}
