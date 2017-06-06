package com.yaler.di.shopd.service

import java.util

import com.yaler.di.shopd.common.{StaticConstants, Constants}
import com.yaler.di.shopd.dao.ESDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import scala.collection.JavaConversions._


/**
  * Created by Administrator on 17/6/2.
  */
@Service
class ESService @Autowired()(esDao: ESDao, constants: Constants
                            ) {

  def getShopDocInfoBySellerId(sellerId: String, ifQueryLastest: Boolean = true): util.Map[String, AnyRef] = {
    val objectMap: util.Map[String, AnyRef] = esDao.getShopDocBySellerId(sellerId, ifQueryLastest)
    val shopdocMap: util.Map[String, AnyRef] = new util.HashMap[String, AnyRef]

    val shopBasicMap: util.Map[String, AnyRef] = constructKVMapData(constants.SHOP_DOC_BASIC_INFO, objectMap)
    shopdocMap.put("shopBasicData", shopBasicMap)

    val merchandiseOrderMap: util.Map[String, AnyRef] = constructKVMapData(constants.SHOP_DOC_MERCHANDISE_ORDER_DATA, objectMap)
    shopdocMap.put("merchandiseOrderData", merchandiseOrderMap)
    val marketingToolMap: util.Map[String, AnyRef] = constructKVMapData(constants.SHOP_DOC_MARKETING_TOOL_DATA, objectMap)
    shopdocMap.put("marketingToolData", marketingToolMap)

    val activeBehaviorMap: util.Map[String, AnyRef] = constructKVMapData(constants.SHOP_DOC_BEHAVIOR_DATA, objectMap)
    shopdocMap.put("activeBehaviorData", activeBehaviorMap)

    val tagMap: util.Map[String, AnyRef] = constructKVMapData(constants.SHOP_DOC_TAG_DATA, objectMap)
    shopdocMap.put("tagData", tagMap)
    return shopdocMap
  }

  private def constructKVMapData(shopDocData: String, objectMap: util.Map[String, AnyRef]): util.Map[String, AnyRef] = {

    val kvMap: util.Map[String, AnyRef] = new util.LinkedHashMap[String, AnyRef]

    if (objectMap == null) return kvMap

    val splits: Array[String] = shopDocData.split(",")

    splits.foreach(split => {
      val kv: Array[String] = split.split(":")
      kvMap.put(kv(1), getValue(objectMap, kv(0)))
    })

    kvMap
  }

  private def getValue(objectMap: util.Map[String, AnyRef], key: String): AnyRef = {

    val `object`: Object = objectMap.get(key)

    if(`object` ==  null) return null

    if (constants.SHOP_DOC_FIELD_NEED_CONVERT.contains("," + key + ",")) {
      if (StaticConstants._1 == `object`.toString) {
        return StaticConstants._YES_IN_CHINESE
      }
      return StaticConstants._NO_IN_CHINESE
    }


    if ((StaticConstants.LAST_LOGIN_DAY == key) || (StaticConstants.LAST_UPDATE_DIARY_DAY == key) || (StaticConstants.LAST_UPLOAD_ITEM_DAY == key) || (StaticConstants.LAST_UPDATE_ITEM_DAY == key)) {
      if (StaticConstants.LAST_TAG_VALUE == `object`.toString) {
        return ""
      }
      return `object`
    }


    return `object`
  }


}
