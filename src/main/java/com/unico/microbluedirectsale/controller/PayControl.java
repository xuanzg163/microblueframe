package com.unico.microbluedirectsale.controller;

import com.github.wxpay.sdk.WXPayUtil;
import com.unico.microbluedirectsale.utils.HttpClientM;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangxuan
 * @version 1.0
 * @date 2021/8/14 1:51
 */
@ApiOperation("支付2")
@RestController
@RequestMapping("/pay")
public class PayControl {

    @ApiOperation("测试支付")
    @PostMapping("/wxpay")
    public String wxPayNotify() {

        Map<String, String> data = new HashMap<>();
        data.put("appid", "wx415c2b8117b1789c");
        data.put("mch_id", "10033639");
        data.put("nonce_str", WXPayUtil.generateNonceStr());
        data.put("body", "测试数据");
        // 商户订单号
        data.put("out_trade_no", "12321sdasc4444");
        // 总金额（分）
        data.put("total_fee", 1 + "");
        data.put("spbill_create_ip", "127.0.0.1");
        data.put("notify_url", "https://v7.unicdata.com/wx/order/create/");
        data.put("trade_type", "JSAPI");
        data.put("openid", "111223dasdasdsad");

        try {
            // 2.生成要发送的xml
            String xmlParam = WXPayUtil.generateSignedXml(data, "05228cef7a938c818b26c97bccc091b6");
            System.out.println("参数-----" + xmlParam);
            HttpClientM client = new HttpClientM("https://api.mch.weixin.qq.com/pay/unifiedorder");
            client.setHttps(true);
            client.setXmlParam(xmlParam);
            client.post();

            // 3.获得结果
            String result = client.getContent();
            System.out.println(result + "=====结果");
            Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
            Map<String, String> map = new HashMap<>();
            Map<String, String> reqData = new HashMap<>();

            String timeStamp = "" + (System.currentTimeMillis() / 1000);
            // 时间戳
            map.put("timeStamp", timeStamp);
            // 随机字符串
            map.put("nonceStr", resultMap.get("nonce_str"));
            // 拼接package返回给前端
            map.put("package", resultMap.get("prepay_id") + "");
            // 签名类型
            map.put("signType", "MD5");
            // 预支付订单id
            map.put("prepay_id", resultMap.get("prepay_id"));

            // 小程序支付需要二次签名  注意这里的appId的I字母是大写
            // 二次签名所用的参数
            reqData.put("appId", "wx415c2b8117b1789c");
            // 时间戳
            reqData.put("timeStamp", timeStamp);
            // 随机字符串
            reqData.put("nonceStr", resultMap.get("nonce_str"));
            // 拼接package
            reqData.put("package", "prepay_id=" + resultMap.get("prepay_id"));
            // 签名类型
            reqData.put("signType", "MD5");
            // partnerkey是商户秘钥
            map.put("paySign", WXPayUtil.generateSignature(reqData, "053RKv1w3M8tSW24ax0w3upjVu3RKv1Y"));
//            return map;
        } catch (Exception e) {
            e.printStackTrace();
//            return new HashMap<>();
        }
        return null;
    }
}
