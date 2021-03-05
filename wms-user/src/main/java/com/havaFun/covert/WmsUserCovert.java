package com.havaFun.covert;
import com.db.covert.base.BaseCovert;
import com.db.model.DataRow;
import com.haveFun.model.WmsUser;
public class WmsUserCovert  extends BaseCovert<WmsUser> {
public  WmsUser format(DataRow form){
if(form==null)return null;
WmsUser item = new WmsUser();
        item.setId(this.parseIntegerItem(form,"id"));
        item.setUsername(this.parseStringItem(form, "username"));
        item.setPhone(this.parseStringItem(form, "phone"));
        item.setAddress(this.parseStringItem(form, "address"));
        item.setPoint(this.parseIntegerItem(form,"point"));
        item.setCreateDate(this.parseErpDateItem(form,"create_date"));
return item;
}
public  DataRow parse(WmsUser item){
if(item==null)return null;
DataRow form = new DataRow();
        this.formatIntegerForm(form, "id", item.getId());
        this.formatStringForm(form, "username", item.getUsername());
        this.formatStringForm(form, "phone", item.getPhone());
        this.formatStringForm(form, "address", item.getAddress());
        this.formatIntegerForm(form, "point", item.getPoint());
        this.formatDateForm(form,"create_date",item.getCreateDate());
return form;
}
	

}
