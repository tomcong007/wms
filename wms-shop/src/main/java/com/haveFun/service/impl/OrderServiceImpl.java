package com.haveFun.service.impl;
import com.haveFun.mapper.OrderMapper;
import com.haveFun.mapper.ProductStockMapper;
import com.haveFun.model.ZkOrder;
import com.haveFun.service.OrderService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    CuratorFramework curatorFramework;
    @Autowired
    ProductStockMapper productStockMapper;
    @Autowired
    OrderMapper orderMapper;
    //分布式事务锁的2种实现方式,控制并发场景下产品的库存不低于0
    //基于zookeeper
    @Transactional
    public boolean initOrder(int userId, int productId){
        //声明1个zk文件锁
        String lockNode = String.format("/productId-%d",productId);
        InterProcessMutex lock = new InterProcessMutex(curatorFramework, lockNode);
        try{
            lock.acquire();
            if (lock.acquire(60, TimeUnit.MINUTES)) {
                Stat stat = curatorFramework.checkExists().forPath(lockNode);
                if (null != stat){
                    // Dot the transaction
                    System.out.println("start get data");
                }else {
                    System.out.println("start to create node");
                }
            }
            //处理事务
            productStockMapper.deductStock(productId);
            ZkOrder zkOrder = new ZkOrder();
            zkOrder.setOrderNo(UUID.randomUUID().toString());
            zkOrder.setUserId(userId);
            orderMapper.insertOrder(zkOrder);
            lock.release();
            return true;
        }catch (Exception e){
            e.fillInStackTrace();
            throw  new RuntimeException(e.getMessage());
        }finally {
            if (lock.isAcquiredInThisProcess()) {
                try {
                    lock.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //基于redis读写锁实现的分布式锁,待补充


}
