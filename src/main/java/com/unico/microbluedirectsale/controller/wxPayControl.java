package com.unico.microbluedirectsale.controller;

/**
 * @author zhangxuan
 * @version 1.0
 * @date 2021/8/12 21:45
 */
public class wxPayControl {

    //平台下单，发起支付 /order/create 接口
        //生成 平台订单createOrderService() 创建orderNumber
        //拼接 参数（加上支付回调地址），调用 微信统一下单接口 https://api.mch.weixin.qq.com/pay/unifiedorder openid
        //获取 统一下单返回参数--解析参数（预付标识（带签名的支付信息））
            //将参数 再次签名-- 返给--小程序

    //小程序发起向微信支付--参数:二次签名参数
    //用户完成支付
    //接收微信支付回调通知/order/notify 15s一次
        //接收输入流resXML-转换获取return_code状态
        //更新订单状态--成功
        //返回状态到小程序

}
