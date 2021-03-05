package com.haveFun.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.haveFun.service.WmsProductStockService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("productStock")
public class ProductStockController {
    @Reference
    WmsProductStockService wmsProductStockService;
    @RequestMapping("query")
    @ResponseBody
    public String query(int productId){
        int stock = wmsProductStockService.query(productId);
        if(stock<=0)return "商品已经售罄";
        return "stock:"+stock;

    }
}
