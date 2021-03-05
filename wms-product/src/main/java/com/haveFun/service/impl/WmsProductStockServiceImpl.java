package com.haveFun.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.haveFun.repository.ProductStockRespository;
import com.haveFun.service.WmsProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Service
@Component
public class WmsProductStockServiceImpl implements WmsProductStockService {
    @Autowired
    ProductStockRespository respository;
    @Override
    public int query(int productId) {

        return respository.query(productId);
    }

    @Override
    public void cutStockCnt(int productId) {
        respository.cutStockCnt(productId);
    }
}
