package com.haveFun.repository;
import com.haveFun.model.ProductStock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;
public interface ProductStockRespository extends Repository<ProductStock,Integer> {
    @Query(value = "select stock_cnt from product_stock  where product_id=?1",nativeQuery = true)
    int query(int productId);
    @Modifying
    @Query(value = "update product_stock m set m.stock_cnt=m.stock_cnt -1 where m.product_id=?1",nativeQuery = true)
    @Transactional
    void cutStockCnt(int productId);
}
