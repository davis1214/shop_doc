package com.yaler.di.shopd.controller

import com.yaler.di.shopd.service.ESService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation._


/**
  * Created by Administrator on 17/6/2.
  */
@Controller
@RequestMapping(Array("/shop/doc"))
class ShopdController @Autowired()(esService: ESService) {


  @RequestMapping(Array("/info/{id}"))
  def info(@PathVariable("id") id: String, model: Model): String = {
    val objectMap = esService.getShopDocInfoBySellerId(id)
    model.addAllAttributes(objectMap)
    "/shop/detail"
  }


  @RequestMapping(Array("/infolast/{id}"))
  def infolast(@PathVariable("id") id: String, model: Model): String = {
    val ifQueryLastest: Boolean = false
    val objectMap = esService.getShopDocInfoBySellerId(id, ifQueryLastest)
    model.addAllAttributes(objectMap)
    "/shop/detail"
  }


}
