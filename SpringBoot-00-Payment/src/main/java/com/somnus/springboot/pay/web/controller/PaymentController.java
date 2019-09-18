package com.somnus.springboot.pay.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.somnus.springboot.pay.enums.PaymentOrderType;
import com.somnus.springboot.pay.pojo.PaymentOrder;
import com.somnus.springboot.pay.service.PayService;
import com.somnus.springboot.pay.service.PaymentService;
import com.somnus.springboot.pay.support.exceptions.PayException;
import com.somnus.springboot.pay.support.util.SystemUtil;
import com.somnus.springboot.pay.support.util.WebUtil;

/**
 * @description: 支付流程抽象基类,为所有渠道提供统一的支付流程模板
 * @author: Somnus
 * @version: 1.0
 * @createdate: 2015-11-27
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2015-12-1       Somnus       1.0            初始化
 */
public abstract class PaymentController{

    private final static Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    protected PayService payService;
    @Autowired
    protected PaymentService paymentService;

    @Value("#{configProperties['staticPath']}")
    private String staticPath;
    @Value("#{configProperties['staticWebPath']}")
    private String staticWebPath;
    @Value("#{configProperties['environment']}")
    private String environment;
    @Value("#{configProperties['cdnTJPath']}")
    private String cdnTJPath;

    /**
     * 获取支付建单失败时跳转的页面
     *
     * @return 支付建单失败时跳转的页面
     */
    protected abstract String getCreateOrderFailedPage();

    /**
     * 支付建单
     * @param paymentOrder 支付信息
     * @param request HTTP请求
     * @param model MVC数据模型
     */
    protected void createOrder(PaymentOrder paymentOrder, HttpServletRequest request, Model model) {
        model.addAttribute("paymentOrder", paymentOrder);
        //用户中心系统,wap充值帮钻分配的支付渠道不能使用帮钻
        /*boolean isOrderEnabledBz = paymentService.isBZEnabledSoucreIds(paymentOrder.getSource());*/
        /*boolean isEnabledBz = isOrderEnabledBz && configService.getIsEnabledBz(paymentOrder.getCrossPay()) && (paymentOrder.getBzBalance()>=0);
        model.addAttribute("isEnabledBz", isEnabledBz);*/
        /*model.addAttribute("isDisabledEnabledBz", configService.isDisabledEnabledBz(paymentOrder));*/
        /*model.addAttribute("bzMaxValue", StringUtils.defaultIfBlank(configService.getBzMaxValue(paymentOrder), paymentOrder.getBzUsable() + ""));*/
        /*model.addAttribute("defaultSelectedBz", configService.defaultSelectedBz(paymentOrder));*/
        /*model.addAttribute("isBZModeDisplay", configService.isBZModeDisplay(paymentOrder));*/
        /*model.addAttribute("bzTipInfo", configService.getBzTipInfo(paymentOrder));*/
        /*model.addAttribute("bzWapTipInfo", configService.getBzWapTipInfo(paymentOrder));*/
        /*model.addAttribute("isLessBalance", paymentOrder.getAmount() > (Double.parseDouble(paymentOrder.getBzBalance()+"")/100));*/
        /*boolean isSuperBzPayPWD = payPasswordService.hadSetPassword(paymentOrder.getUserId());
        model.addAttribute("isSuperBzPayPWD", isSuperBzPayPWD);*/
        /*model.addAttribute("isB5CBusiness", (PaySource.isB5CBusiness(paymentOrder.getSource())));*/
        /*model.addAttribute("isBHBBusiness", (PaySource.isBHBBusiness(paymentOrder.getSource())));*/
    }

    /**
     * 获取支付建单成功时跳转的页面
     * @return 支付建单成功时跳转的页面
     * @param paymentOrder
     * @param model
     */
    protected abstract String getCreateOrderSuccessPage(PaymentOrder paymentOrder, Model model);

    /**
     * 真正的支付动作
     *
     * @param paymentOrder
     * @param request
     * @param model
     * @return
     */
    protected abstract String doPay(PaymentOrder paymentOrder, HttpServletRequest request, HttpServletResponse response, Model model);

    @RequestMapping("order")
    public String order(@Valid PaymentOrder paymentOrder, BindingResult result, HttpServletRequest request, Model model) {
        if (logger.isInfoEnabled()) {
            logger.info("处理来自[{}]支付请求:[{}]", SystemUtil.getRemoteIP(request), paymentOrder.toString());
        }
        try {
            //防止页面修改篡改status
            paymentOrder.setStatus(PaymentOrderType.NOTDONE.getValue());
            /*paymentOrder.setBzStatus(PaymentOrderType.NOTDONE.getValue());
            paymentOrder.setUsebz(PaymentOrderType.NOTDONE.getValue()+"");*/
            boolean isFromCache = payService.mergePaymentOrder(paymentOrder);
            if (!isFromCache && result.hasErrors()) { // 如果合并成功,则代表使用新的支付模式,参数校验错误提示可以直接跳过
                String error = result.getAllErrors().get(0).getDefaultMessage();
                throw new PayException(error);
            }
            payService.checkPaymentOrder(paymentOrder, !isFromCache);
        } catch (PayException e) {
            logger.warn("支付建单参数校验失败", e);
            model.addAttribute("message", e.getMessage());
            return getCreateOrderFailedPage();
        } catch (Exception e) {
            logger.warn("支付建单参数校验失败", e);
            model.addAttribute("message", e.getMessage());
            return getCreateOrderFailedPage();
        }
        /*CacheServiceExcutor.put(iPayService.createPaymentStandbyKey(paymentOrder.getOrderId()), paymentOrder, 60 * 10);*/ // 10分钟未支付即过期
        createOrder(paymentOrder, request, model);
        model.addAttribute("rootPath", WebUtil.getRootPath());
        model.addAttribute("staticPath", staticPath);
        model.addAttribute("staticWebPath", staticWebPath);
        model.addAttribute("environment", environment);
        model.addAttribute("cdnTJPath", cdnTJPath);
        return getCreateOrderSuccessPage(paymentOrder, model);
    }

    @RequestMapping(value = "pay", produces = { "text/html;charset=UTF-8" })
    @ResponseBody
    public String pay(PaymentOrder paymentOrder, BindingResult result, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (logger.isInfoEnabled()) {
            logger.info("处理来自[{}]的支付请求:[{}]", SystemUtil.getRemoteIP(request), paymentOrder);
        }
        String text = "";
        try {
            if (result.hasErrors()) {
                if(logger.isWarnEnabled()){
                    logger.warn("支付参数校验失败" + result);
                }
                String error = result.getAllErrors().get(0).getDefaultMessage();
                throw new PayException(error);
            }
            PaymentOrder cachedPaymentOrder = new PaymentOrder();/*(PaymentOrder) CacheServiceExcutor.get(iPayService.createPaymentStandbyKey(paymentOrder.getOrderId()))*/
            if (logger.isInfoEnabled()) {
                logger.info("来自缓存的支付请求:[{}]", cachedPaymentOrder);
            }
            Assert.isTrue(cachedPaymentOrder != null, "ERRRO_PAYMENT_INFO");
            cachedPaymentOrder.setThirdPayType(paymentOrder.getThirdPayType()); // 第三方支付方式
            cachedPaymentOrder.setDefaultBank(paymentOrder.getDefaultBank()); // 支付渠道
            cachedPaymentOrder.setInviterId(paymentOrder.getInviterId()); // 邀请者ID
            cachedPaymentOrder.setTrafficSource(paymentOrder.getTrafficSource()); // 流量跟踪标签
            /*cachedPaymentOrder.setSuperBzPayPwd(paymentOrder.getSuperBzPayPwd());*/ // 支付密码
            /*cachedPaymentOrder.setNeedSuperBzPayPwd(paymentOrder.isNeedSuperBzPayPwd());*/ // 是否需要校验支付密码
            /*cachedPaymentOrder.setUsebz(paymentOrder.getUsebz());*/ // 是否使用帮钻抵扣
            /*cachedPaymentOrder.setBzAmount(paymentOrder.getBzAmount());*/ // 超级帮钻抵扣额度
            // 兼容页面参数未传输情况，如未使用超级帮钻支付
            /*if (paymentOrder != null && (paymentOrder.getBzAmount() == null || StringUtils.isBlank(paymentOrder.getUsebz()) || !paymentOrder.getUsebz().equals("1"))) {
                cachedPaymentOrder.setBzAmount(0L);
            }*/
            cachedPaymentOrder.setPayFrom(paymentOrder.getPayFrom()); // 请求来源
            paymentOrder = cachedPaymentOrder; // 切换数据
            text = doPay(paymentOrder, request, response, model);
        } catch (Exception e) {
            text = payService.buildResponse(paymentOrder, e);
            if (logger.isWarnEnabled()) {
                logger.warn("支付失败", e);
            }
        }
        if (logger.isInfoEnabled()) {
            logger.info("支付返回:" + text);
        }
        return text;
    }

}
