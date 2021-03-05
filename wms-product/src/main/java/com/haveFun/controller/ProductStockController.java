package com.haveFun.controller;
import com.haveFun.repository.ProductStockRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("productStock")
public class ProductStockController {
    @Autowired
    ProductStockRespository respository;
    @RequestMapping("queryStock")
    @ResponseBody
    public Object queryStock(int productId){
        int stock = respository.query(productId);
        return stock;
    }
    @RequestMapping("cutStockCnt")
    @ResponseBody
    public Object cutStockCnt(int productId){
        respository.cutStockCnt(productId);
        return "ok";
    }
}
