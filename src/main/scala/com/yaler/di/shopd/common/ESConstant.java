package com.yaler.di.shopd.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 17/5/23.
 */
@Component
public class ESConstant {

    @Value("#{'${es.public.cluster.name}'.trim()}")
    public String ES_PUBLIC_CLUSTER_NAME;
    @Value("#{'${es.public.hosts}'.trim()}")
    public String ES_PUBLIC_HOSTS;

    @Value("#{'${es.pay.cluster.name}'.trim()}")
    public String ES_PAY_CLUSTER_NAME;
    @Value("#{'${es.pay.hosts}'.trim()}")
    public String ES_PAY_HOSTS;

    @Value("#{'${es.test.cluster.name}'.trim()}")
    public String ES_TEST_CLUSTER_NAME;
    @Value("#{'${es.test.hosts}'.trim()}")
    public String ES_TEST_HOSTS;

    @Value("#{'${es.cluster.default.port}'.trim()}")
    public int DEFAULT_PORT;

    @Value("#{'${shop.doc.es.index.name}'.trim()}")
    public String ES_SHOP_DOC_INDEX_NAME;
    @Value("#{'${shop.doc.es.index.type}'.trim()}")
    public String ES_SHOP_DOC_INDEX_TYPE;

    public static final String ES_RECORD_TIME_NAME = "@timestamp";


    @Value("#{'${es.query.env}'.trim()}")
    public String ES_QUERY_ENV;


    public String[] getEsHosts() {
        String host = null;
        switch (ES_QUERY_ENV) {
            case "public":
                host = StringUtils.deleteWhitespace(ES_PUBLIC_HOSTS);
                break;
            case "pay":
                host = StringUtils.deleteWhitespace(ES_PAY_HOSTS);
                break;
            default:
                host = StringUtils.deleteWhitespace(ES_TEST_HOSTS);
                break;
        }

        return host.split(",");
    }

    public String getEsClusterName() {
        String clusterName = null;
        switch (ES_QUERY_ENV) {
            case "public":
                clusterName = ES_PUBLIC_CLUSTER_NAME;
                break;
            case "pay":
                clusterName = ES_PAY_CLUSTER_NAME;
                break;
            default:
                clusterName = ES_TEST_CLUSTER_NAME;
                break;
        }

        return clusterName;
    }


}
