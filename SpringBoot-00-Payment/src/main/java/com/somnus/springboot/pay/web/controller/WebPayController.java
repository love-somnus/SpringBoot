package com.somnus.springboot.pay.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.somnus.springboot.pay.pojo.PaymentOrder;
import com.somnus.springboot.pay.pojo.PaymentPageChannel;
import com.somnus.springboot.pay.support.util.WebUtil;

/**
 * @description: WEB渠道支付流程控制器
 * @author: Somnus
 * @version: 1.0
 * @createdate: 2015-11-27
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2015-12-1     Somnus       1.0            初始化
 */
@Controller
@RequestMapping("third")
public class WebPayController extends PaymentController {

    private final static Logger logger = LoggerFactory.getLogger(WebPayController.class);

    @Value("#{configProperties['ucenter.server']}")
    private String ucenterPath;
    @Value("#{configProperties['URL_VIEW_PAY_ORDER_0']}")
    private String createOrderSuccessPage;
    @Value("#{configProperties['URL_VIEW_PAY_B5C_ORDER']}")
    private String createB5COrderPage;
    @Value("#{configProperties['www.server']}")
    private String b5mPath;

    @Override
    protected String getCreateOrderFailedPage() {
        return "redirect:" + ucenterPath + "/trade/myorder.htm";
    }
    @Override
    protected String getCreateOrderSuccessPage(PaymentOrder paymentOrder, Model model) {
        //帮我采业务切换新的支付页，以后待替换成接口方式
        /*if(PaySource.B5C_ORDER == PaySource.valueOf(paymentOrder.getSource())){
            return createB5COrderPage;
        }*/
        return createOrderSuccessPage;
    }

    @Override
    protected void createOrder(PaymentOrder paymentOrder, HttpServletRequest request, Model model) {
        super.createOrder(paymentOrder, request, model);
        // 生成扫码支付短链接      加入 memcache缓存
        payService.getShortUrl4Sina(WebUtil.getRootPath(request), paymentOrder);
        logger.info("为用户[{}]生成可用的银行列表", paymentOrder.getUserId());
        Map<String, List<String>> typeListMap = payService.getBankTypeIndexByUserId(paymentOrder.getUserId());
        model.addAttribute("typeListMap", typeListMap);
        /*String returnUrl = request.getParameter("returnUrl");*/
        /*returnUrl = StringUtils.defaultString(returnUrl, configService.getPaymentSourceUrlById(paymentOrder.getSource() + ""));
        model.addAttribute("returnUrl", returnUrl);*/
        model.addAttribute("b5mPath", b5mPath);
        model.addAttribute("ucenterPath", ucenterPath);
        logger.info("查询用户[{}]是否绑定了手机号码", paymentOrder.getUserId());
        /*String userBindMobile = clientService.getUserBindMobile(paymentOrder.getUserId());
        model.addAttribute("userBindMobile", userBindMobile);
        model.addAttribute("bankMaxLength", StringUtils.defaultIfBlank(configService.getStringValue(Constants.BANK_MAX_LENGTH_KEY), Constants.BANK_MAX_LENGTH));*/
        logger.info("正在根据跨境支付类型({})为订单[{}]生成可用的支付渠道", paymentOrder.getCrossPay(), paymentOrder.getOrderId());
        PaymentPageChannel paymentPageChannel = /*configService.getPaymentPageChannel(paymentOrder.getCrossPay())*/new PaymentPageChannel();
        model.addAttribute("zfPlatform", paymentPageChannel.getZfPlatformList());
        model.addAttribute("payOnlineChannel", paymentPageChannel.getPayOnlineChannel());
        model.addAttribute("payOnlinePlatform", paymentPageChannel.getPayOnlinePlatformList());
        model.addAttribute("fastpayChannel", paymentPageChannel.getFastPayChannel());
        model.addAttribute("fastPayPlatform", paymentPageChannel.getFastPayPlatformList());
        model.addAttribute("payOnlineTips", paymentPageChannel.getPayOnlineTips());
        model.addAttribute("fastPayTips", paymentPageChannel.getFastPayTips());
        model.addAttribute("paymentDivDataMps", paymentPageChannel.getPayOnlineDivDataMps());
        /*if(paymentPageChannel.isNeedBankDirect()){
            model.addAttribute("payOnlinePlatform", paymentPageChannel.getPayOnlineMorePlatformList());
        }
        if(paymentPageChannel.isNeedFPBankDirect()){
            model.addAttribute("fastPayPlatform", paymentPageChannel.getFastPayMorePlatformList());
        }*/
    }

    @Override
    protected String doPay(PaymentOrder paymentOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
        logger.info("为订单[{}]执行支付动作", paymentOrder.getOrderId());
        return paymentService.pay(paymentOrder);
    }

}
