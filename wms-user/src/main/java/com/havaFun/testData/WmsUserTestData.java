package com.havaFun.testData;
import com.db.model.ErpDate;
import com.havaFun.service.WmsUserService;
import com.haveFun.model.WmsUser;
import java.util.Random;
public class WmsUserTestData {
    static WmsUserService service = WmsUserService.getInstance();
    public static void insertSomeUsers(){
        Random r = new Random();
        for(int i=0;i<100;i++){
            WmsUser wmsUser = new WmsUser();
            wmsUser.setAddress("address"+i);
            wmsUser.setCreateDate(new ErpDate());
            wmsUser.setPhone(String.valueOf(13419680000l+i));
            wmsUser.setPoint(r.nextInt(10000));
            wmsUser.setUsername("username"+i);
            service.saveItem(wmsUser);
        }
    }
    public static void main(String[] args) {
        //尝试插入若干用户信息
        insertSomeUsers();
    }
}
