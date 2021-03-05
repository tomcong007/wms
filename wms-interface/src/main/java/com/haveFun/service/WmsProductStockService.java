package com.haveFun.service;

public interface WmsProductStockService {
    int query(int productId);
    void cutStockCnt(int productId);
}
