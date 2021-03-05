package com.haveFun.mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
@Component(value = "productStockMapper")
public interface ProductStockMapper {
    @Update("update product_stock set stock_cnt = stock_cnt-1  where id = #{id}")
    int deductStock(@Param("id")  Integer id);
}
