package com.havaFun.service;
import com.db.service.PojoBaseService;
import com.havaFun.covert.WmsUserCovert;
import com.haveFun.model.WmsUser;

public class WmsUserService extends PojoBaseService<WmsUser> {
    private static WmsUserService service;
    private static WmsUserCovert covert = new WmsUserCovert();
	private final static String table = "wms_user";
	private WmsUserService(){
         super(table, covert);
    }
	public static WmsUserService getInstance(){
	   if(service==null)service = new WmsUserService();
	   return service;
	}

	

}
