package com.haveFun.mapper;
import com.haveFun.model.ZkOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Component;
@Component(value = "orderMapper")
public interface OrderMapper {
    @Options(useGeneratedKeys=true,keyColumn = "id",keyProperty = "id")
    @Insert("insert into zk_order(product_id,order_no,user_id) values (#{productId},#{orderNo},#{userId})")
    int insertOrder(ZkOrder order);
}
