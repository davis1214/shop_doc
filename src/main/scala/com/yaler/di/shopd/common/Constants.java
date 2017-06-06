package com.yaler.di.shopd.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 17/5/24.
 */
@Component
//@PropertySource(value = "classpath:/config.properties", ignoreResourceNotFound = true)
public class Constants {

//    //shop.doc.shop.basic.ename
//    static static final String SHOP_DOC_BASIC_INFO = "shop.doc.shop.basic.ename";
//
//    // Merchandise/order data
//    static static final String SHOP_DOC_MERCHANDISE_ORDER_DATA = "shop.doc.shop.merchan.order.ename";
//
//    //Marketing tool related data
//    static static final String SHOP_DOC_MARKETING_TOOL_DATA = "shop.doc.shop.marketing.tool.ename";
//
//    //Active behavior data
//    static static final String SHOP_DOC_BEHAVIOR_DATA = "shop.doc.shop.active.behavior.ename";
//
//    //Tag data
//    static static final String SHOP_DOC_TAG_DATA = "shop.doc.shop.tag.ename";


    //shop.doc.shop.basic.ename
    @Value("#{'${shop.doc.shop.basic.ename}'.trim()}")
    public String SHOP_DOC_BASIC_INFO;

    // Merchandise/order data
    @Value("#{'${shop.doc.shop.merchan.order.ename}'.trim()}")
    public String SHOP_DOC_MERCHANDISE_ORDER_DATA;

    //Marketing tool related data
    @Value("#{'${shop.doc.shop.marketing.tool.ename}'.trim()}")
    public String SHOP_DOC_MARKETING_TOOL_DATA;

    //Active behavior data
    @Value("#{'${shop.doc.shop.active.behavior.ename}'.trim()}")
    public String SHOP_DOC_BEHAVIOR_DATA;

    //Tag data
    @Value("#{'${shop.doc.shop.tag.ename}'.trim()}")
    public String SHOP_DOC_TAG_DATA;


    // convert 1 to yes ,0 to no which expressed in Chinese
    @Value("#{'${shop.doc.shop.fields.need.convert}'.trim()}")
    public String SHOP_DOC_FIELD_NEED_CONVERT;



}
