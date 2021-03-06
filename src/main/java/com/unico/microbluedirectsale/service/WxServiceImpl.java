package com.unico.microbluedirectsale.service;

/**
 * @author zhangxuan
 * @version 1.0
 * @date 2021/8/12 21:58
 */

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.unico.microbluedirectsale.controller.WXConfigUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author saodiseng
 * @date 2019/2/18
 */
@Service
@Slf4j
public class WxServiceImpl implements WxService {
    public static final String SPBILL_CREATE_IP = "192.168.1.1";
    public static final String NOTIFY_URL = "回调接口地址";
    public static final String TRADE_TYPE_APP = "JSAPI";


    @Override
    public String payBack(String resXml) {
        WXConfigUtil config = null;
        try {
            config = new WXConfigUtil();
        } catch (Exception e) {
            e.printStackTrace();
        }

        WXPay wxpay = new WXPay(config);
        String xmlBack = "";
        Map<String, String> notifyMap = null;
        try {
            notifyMap = WXPayUtil.xmlToMap(resXml);         // 调用官方SDK转换成map类型数据
            if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {//验证签名是否有效，有效则进一步处理

                String return_code = notifyMap.get("return_code");//状态
                String out_trade_no = notifyMap.get("out_trade_no");//商户订单号
                if (return_code.equals("SUCCESS")) {
                    if (out_trade_no != null) {
                        // 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户的订单状态从退款改成支付成功
                        // 注意特殊情况：微信服务端同样的通知可能会多次发送给商户系统，所以数据持久化之前需要检查是否已经处理过了，处理了直接返回成功标志
                        //业务数据持久化
//                        log.info("微信手机支付回调成功订单号:{}", out_trade_no);
                        xmlBack = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                    } else {
//                        log.info("微信手机支付回调失败订单号:{}", out_trade_no);
                        xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                    }
                }
                return xmlBack;
            } else {
                // 签名错误，如果数据里没有sign字段，也认为是签名错误
                //失败的数据要不要存储？
//                log.error("手机支付回调通知签名错误");
                xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                return xmlBack;
            }
        } catch (Exception e) {
//            log.error("手机支付回调通知失败", e);
            xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
        return xmlBack;
    }

    @Override
    public Map doUnifiedOrder() throws Exception {
        try {
            WXConfigUtil config = new WXConfigUtil();
            WXPay wxpay = new WXPay(config);
            Map<String, String> data = new HashMap<>();
            //生成商户订单号，不可重复
            String out_trade_no = UUID.randomUUID().toString();
            data.put("appid", "wx415c2b8117b1789c");
            data.put("mch_id", "10033639");
            data.put("nonce_str", WXPayUtil.generateNonceStr());
            data.put("body", "腾讯充值中心-QQ会员充值");
            //订单号
            data.put("out_trade_no", "123456789s123xx");
            data.put("total_fee", "10");
            data.put("spbill_create_ip", SPBILL_CREATE_IP);
            data.put("notify_url", "https://v7.unicdata.com/wx/order/create/");
            data.put("trade_type", "JSAPI");
            data.put("openid","12123asdasda");
            //完成签名
            data.put("sign", WXPayUtil.generateSignature(data, "053RKv1w3M8tSW24ax0w3upjVu3RKv1Y",
                    WXPayConstants.SignType.MD5));
            //使用官方API请求预付订单,调用统一下单接口
            Map<String, String> response = wxpay.unifiedOrder(data);

            //二次签名--商户server调用再次签名，返还签名参数给小程序，小程序调用支付
            if ("SUCCESS".equals(response.get("return_code"))) {//主要返回以下5个参数，给小程序调用支付
                Map<String, String> param = new HashMap<>();
                param.put("appid", "wx415c2b8117b1789c");
                param.put("partnerid", response.get("mch_id"));
                param.put("prepayid", response.get("prepay_id"));
                param.put("package", "Sign=WXPay");
                param.put("noncestr", WXPayUtil.generateNonceStr());
                param.put("timestamp", System.currentTimeMillis() / 1000 + "");

                return param;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("下单失败");
        }
        throw new Exception("下单失败");
    }

}