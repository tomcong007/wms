package com.haveFun.model;
import java.io.Serializable;
import java.util.Date;
public class WmsUser implements Serializable {
    private Integer id;
    private String username;
    private String phone;
    private String address;
    private Integer point;
    private Date createDate;
    public void setId(Integer  id){
        this.id = id;
    }
    public  Integer getId(){
        return id;
    }
    public void setUsername(String  username){
        this.username = username;
    }
    public  String getUsername(){
        return username;
    }
    public void setPhone(String  phone){
        this.phone = phone;
    }
    public  String getPhone(){
        return phone;
    }
    public void setAddress(String  address){
        this.address = address;
    }
    public  String getAddress(){
        return address;
    }
    public void setPoint(Integer  point){
        this.point = point;
    }
    public  Integer getPoint(){
        return point;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
