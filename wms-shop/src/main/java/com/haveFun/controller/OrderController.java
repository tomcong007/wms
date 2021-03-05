package com.haveFun.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.haveFun.model.WmsUser;
import com.haveFun.service.OrderService;
import com.haveFun.service.WmsProductStockService;
import com.haveFun.service.WmsUserProducerService;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("order")
public class OrderController {
    @Autowired
    CuratorFramework curatorFramework;
    @Reference
    WmsUserProducerService wmsUserProducerService;
    @Reference
    WmsProductStockService wmsProductStockService;
    @Autowired
    OrderService orderService;
    @RequestMapping("initOrder")
    @ResponseBody
    public String initOrder(int userId, int productId){
        WmsUser wmsUser = wmsUserProducerService.findById(userId);
        if(wmsUser==null)return "用户不存在";
        int stockCnt = wmsProductStockService.query(productId);
        if(stockCnt<=0)return "error:库存不足";
        if(orderService.initOrder(userId,productId)){
            doNotifyUser(userId);
            return "success";
        }else {
            return "error";
        }
    }
    //notify用户,订单创建成功,跳过支付系统,直接为用户添加100积分
    //将用户信息用rabbitMQ通知用户端,给用户的积分+100,假设不同商品的积分都是100
    private void doNotifyUser(int userId) {

    }
}
