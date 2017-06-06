package com.yaler.di.shopd.dao

import com.yaler.di.shopd.common.ESConstant
import org.apache.log4j.Logger
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse
import org.elasticsearch.action.admin.indices.get.GetIndexRequest
import org.elasticsearch.action.admin.indices.get.GetIndexResponse
import org.elasticsearch.action.search.SearchRequestBuilder
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.action.search.SearchType
import org.elasticsearch.client.{IndicesAdminClient, Client}
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.ImmutableSettings
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.elasticsearch.index.query.QueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Repository
import javax.annotation.PostConstruct
import javax.annotation.Resource
import java.util

import scala.collection.JavaConversions._


/**
  * Created by Administrator on 17/5/23.
  */
@Repository class ESDao {
  private var esClient: Client = null
  @Resource private var esConstant: ESConstant = null
  private var searchResultSize: String = "1"
  private final val logger: Logger = Logger.getLogger(classOf[ESDao])
  private var indexName: String = null

  @PostConstruct private def EslasticSearchInit {
    try {
      esClient = null
      //esClient = openClient(esConstant.getEsHosts, esConstant.getEsClusterName)
    }
    catch {
      case e: Exception => {
        e.printStackTrace
        logger.error("***init es client error " + e.getMessage)
      }
    }
  }

  def ifIndexExists(indexName: String, indexType: String): Boolean = {
    val response: IndicesExistsResponse = esClient.admin.indices.exists(new IndicesExistsRequest().indices(Array[String](indexName))).actionGet
    return response.isExists
  }

  @Cacheable(value = Array("indexName"), key = "'IndexName_' + #span")
  def getIndexName(span: Int): String = {
    val indexName: String = esConstant.ES_SHOP_DOC_INDEX_NAME + "*"

    //val actionGet: GetIndexResponse = esClient.admin.indices.getIndex(new GetIndexRequest().indices(Array[String](indexName))).actionGet
    val actionGet = esClient.admin.indices.getIndex(new GetIndexRequest().indices(indexName)).actionGet()

    val indices: Array[String] = actionGet.getIndices
    if (indices == null || indices.length == 0) {
      return "-1"
    }

    indices.sorted.apply(indices.length - 1 - span)
  }

  @Cacheable(value = Array("ShopdInfo"), key = "'ShopdInfo_'+ #sellerId +'_'+ #ifQueryLastest")
  def getShopDocBySellerId(sellerId: String, ifQueryLastest: Boolean): util.Map[String, AnyRef] = {

    val source: util.Map[String, AnyRef] = Map("shop_name" -> "店小二", "seller_id" -> "234324243",
      "c_order_count" -> "1213", "is_wx_lighten" -> "1", "is_wx_union" -> "0", "is_village" -> "1", "session_cn_7" -> "12")

    source

    //      val queryIndexName: String = if (ifQueryLastest) getIndexName(0) else getIndexName(1)
    //      val builder: SearchRequestBuilder = esClient.prepareSearch(queryIndexName).setTypes(esConstant.ES_SHOP_DOC_INDEX_TYPE).setSearchType(SearchType.DEFAULT).setFrom(0).setSize(Integer.valueOf(searchResultSize))
    //      val qb: QueryBuilder = QueryBuilders.boolQuery.must(QueryBuilders.termQuery("seller_id", sellerId))
    //      builder.setQuery(qb)
    //      val responsesearch: SearchResponse = builder.execute.actionGet
    //      val costTime: Long = responsesearch.getTookInMillis
    //      val resNum: Long = responsesearch.getHits.getTotalHits
    //      logger.info("get shop_doc detail info by seller id " + sellerId + " caost " + costTime + " , resNum " + resNum)
    //      if (resNum <= 0) {
    //        return null
    //      }
    //
    //      responsesearch.getHits.getAt(0).getSource
  }

  /**
    * 获取Client对象
    *
    * @return
    */
  def getPublicClient: Client = {
    if (esClient == null) {
      EslasticSearchInit
    }
    return esClient
  }

  /**
    * 获取Client对象
    *
    * @return
    */
  def resetPublicClient(client: Client): Client = {
    if (client != esClient && esClient != null) {
      return esClient
    }
    if (esClient != null) {
      esClient.close
    }
    esClient = openClient(esConstant.getEsHosts, esConstant.getEsClusterName)
    logger.info("***reset static client")
    return esClient
  }

  /**
    * 初始化ES client
    *
    * @param hostNames   逗号分隔的hosts
    * @param clusterName ES集群名称
    */
  private def openClient(hostNames: Array[String], clusterName: String): TransportClient = {


    val serverAddresses: Array[InetSocketTransportAddress] = new Array[InetSocketTransportAddress](hostNames.length)


    var i: Int = 0
    hostNames.foreach(hostName => {
      val hostPort: Array[String] = hostNames(i).trim.split(":")
      val host: String = hostPort(0).trim
      val port: Int = if (hostPort.length == 2) hostPort(1).trim.toInt else esConstant.DEFAULT_PORT
      serverAddresses(i) = new InetSocketTransportAddress(host, port)
      i += 1
    })



    //      while (i < hostNames.length) {
    //        {
    //          val hostPort: Array[String] = hostNames(i).trim.split(":")
    //          val host: String = hostPort(0).trim
    //          val port: Int = if (hostPort.length == 2) hostPort(1).trim.toInt else esConstant.DEFAULT_PORT
    //          serverAddresses(i) = new InetSocketTransportAddress(host, port)
    //        }
    //        ({
    //          i += 1;
    //          i - 1
    //        })
    //      }

    logger.info("Using ElasticSearch hostnames:  " + serverAddresses.toString)
    val settings: Settings = ImmutableSettings.settingsBuilder.put("cluster.name", clusterName).put("client.transport.sniff", false).build
    val transportClient: TransportClient = new TransportClient(settings)
    for (host <- serverAddresses) {
      transportClient.addTransportAddress(host)
    }
    return transportClient
  }
}