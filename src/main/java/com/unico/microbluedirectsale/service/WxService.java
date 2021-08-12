package com.unico.microbluedirectsale.service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author saodiseng
 * @date 2019/2/18
 */
public interface WxService {

    String payBack(String resXml);

    Map doUnifiedOrder() throws Exception;
}