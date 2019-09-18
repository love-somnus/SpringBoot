package com.somnus.springboot.pay.thirdpay;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.OrderComparator;
import org.springframework.stereotype.Service;

/**
 * @ClassName:     PaymentChannelHandlerAdapter.java
 * @Description:   回调请求处理器适配器
 * @author         Somnus
 * @version        V1.0  
 * @Date           2016年12月28日 上午10:21:00
 */
@Service
public class PaymentChannelHandlerAdapter implements InitializingBean, ApplicationListener<ContextRefreshedEvent> {

    private boolean                    detectAllHandlers = true;

    private List<PaymentChannelHandler> callbackHandlers;

    /**
     * 获得一个能够处理当前回调请求的处理器
     * @param parameter 回调请求参数
     * @return 能够处理当前请求的处理器(如果未找到,则抛异常)
     */
    public <P, R> PaymentChannelHandler getHandler(RequestParameter<P, R> parameter){
        int count = callbackHandlers.size();
        for (int i = 0; i < count; i++) {
            PaymentChannelHandler handler = callbackHandlers.get(i);
            if(handler.isSupport(parameter)){
                return handler;
            }
        }
        return null;
    }

    public boolean isDetectAllHandlers() {
        return detectAllHandlers;
    }

    public void setDetectAllHandlers(boolean detectAllHandlers) {
        this.detectAllHandlers = detectAllHandlers;
    }

    public List<PaymentChannelHandler> getCallbackHandlers() {
        return callbackHandlers;
    }

    public void setCallbackHandlers(List<PaymentChannelHandler> callbackHandlers) {
        this.callbackHandlers = callbackHandlers;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        callbackHandlers = callbackHandlers == null ? new ArrayList<PaymentChannelHandler>() : callbackHandlers;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        if(context.getParent() == null && this.detectAllHandlers){
            Map<String, PaymentChannelHandler> matchingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(context, PaymentChannelHandler.class, true, false);
            if (!matchingBeans.isEmpty()) {
                callbackHandlers.addAll(matchingBeans.values());
                OrderComparator.sort(this.callbackHandlers);
            }
        }
    }

}
