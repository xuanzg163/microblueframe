package com.unico.microbluedirectsale.controller;

import com.unico.microbluedirectsale.service.WxService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;


/**
 * @author zhangxuan
 * @version 1.0
 * @date 2021/8/12 22:02
 */
public class OrderControl {


    @RestController
    @RequestMapping("/order")
    public class OrderController {

        @Autowired
        private WxService wxService;

        @PostMapping("/wx")
        public Map wxAdd() throws Exception {
            return wxService.doUnifiedOrder();
        }

        @PostMapping("/notify")
        @ApiOperation("微信回调")
        public String wxPayNotify(HttpServletRequest request) {
            String resXml = "";
            try {
                InputStream inputStream = request.getInputStream();
                //将InputStream转换成xmlString
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder sb = new StringBuilder();
                String line = null;
                try {
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                } finally {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                resXml = sb.toString();
                String result = wxService.payBack(resXml);
                return result;
            } catch (Exception e) {
                System.out.println("微信手机支付失败:" + e.getMessage());
                String result = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                return result;
            }
        }

    }


}
