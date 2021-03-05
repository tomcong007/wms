package com.haveFun.model;
import javax.persistence.*;
import java.io.Serializable;
@Entity(name = "product_stock")
public class ProductStock  implements Serializable {
    @Id    //主键id
    @GeneratedValue
    private int id;
    private int productId;
    private int stockCnt;
    private String productName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getStockCnt() {
        return stockCnt;
    }

    public void setStockCnt(int stockCnt) {
        this.stockCnt = stockCnt;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
