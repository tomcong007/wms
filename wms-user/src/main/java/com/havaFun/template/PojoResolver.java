package com.havaFun.template;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.db.model.DataRow;
import com.db.model.JdbcTemplate;
import com.db.service.BaseService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;

public class PojoResolver {
    private final static JdbcTemplate t = new BaseService().getJdbcTemplate("web");
    public final static String WORKSPACE= com.db.config.Configuration.getString("model.tpl");
    public final static String TEMPLATE_PATH  = com.db.config.Configuration.getString("model.path");
    public static  Template getTemplate(String name) {
        try {
            // 通过Freemaker的Configuration读取相应的ftl
            Configuration cfg = new Configuration();
            // 设定去哪里读取相应的ftl模板文件
            cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_PATH));
            // 在模板文件目录中找到名称为name的文件
            Template temp = cfg.getTemplate(name);
            return temp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 生成pojo层
     * @param tpl(生成的java文件名,必须为.java,属于本地Workspace非web容器里com.service里的相对路径)
     */
    public static void createPojo(PojoTpl tpl){
        String fileName=String.format("%s\\model\\%s.java",WORKSPACE,tpl.getName());
        File f = new File(fileName);
        if(f.exists())f.delete();
        FileWriter out = null;
        Map<String,Object> root = new HashMap<String,Object>(2);
        root.put("pojoName", tpl.getName());
        root.put("propertyList",tpl.getPropertyList());
        try {
            // 通过一个文件输出流，就可以写到相应的文件中，此处用的是绝对路径
            validateParentPath(fileName);
            out = new FileWriter(new File(fileName));
            Template temp = getTemplate("pojo.ftl");
            temp.process(root, out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 生成数据库结果集hashmap到pojo层的转化映射covert层
     * @param tpl(生成的java文件名,必须为.java,属于本地Workspace非web容器里com.service里的相对路径)
     */
    public static void createCovert(PojoTpl tpl){
        String fileName=String.format("%s\\covert\\%sCovert.java",WORKSPACE,tpl.getName());
        File f = new File(fileName);
        if(f.exists())f.delete();
        FileWriter out = null;
        Map<String,Object> root = new HashMap<String,Object>(2);
        root.put("pojoName", tpl.getName());
        root.put("propertyList",tpl.getPropertyList());
        try {
            // 通过一个文件输出流，就可以写到相应的文件中，此处用的是绝对路径
            validateParentPath(fileName);
            out = new FileWriter(new File(fileName));
            Template temp = getTemplate("covert.ftl");
            temp.process(root, out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void createService(String table,PojoTpl tpl) {
        String fileName=String.format("%s\\service\\%sService.java",WORKSPACE,tpl.getName());
        File f = new File(fileName);
        if(f.exists())f.delete();
        FileWriter out = null;
        Map<String,Object> root = new HashMap<String,Object>(4);
        root.put("pojoName", tpl.getName());
        root.put("tableName", table);
        root.put("propertyList",tpl.getPropertyList());
        root.put("ali",0);
        try {
            // 通过一个文件输出流，就可以写到相应的文件中，此处用的是绝对路径
            validateParentPath(fileName);
            out = new FileWriter(new File(fileName));
            Template temp = getTemplate("service.ftl");
            temp.process(root, out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createAliService(String table,PojoTpl tpl) {
        String fileName=String.format("%s\\service\\%sService.java",WORKSPACE,tpl.getName());
        File f = new File(fileName);
        if(f.exists())f.delete();
        FileWriter out = null;
        Map<String,Object> root = new HashMap<String,Object>(4);
        root.put("pojoName", tpl.getName());
        root.put("tableName", table);
        root.put("propertyList",tpl.getPropertyList());
        root.put("ali",1);
        try {
            // 通过一个文件输出流，就可以写到相应的文件中，此处用的是绝对路径
            validateParentPath(fileName);
            out = new FileWriter(new File(fileName));
            Template temp = getTemplate("service.ftl");
            temp.process(root, out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据表名和字段映射来获取模版变量的值
     * @param tableName(数据库的表名)
     * @param name 为实体类的名称,比如 User.java name就是 User
     * @return 模板
     */
    public static PojoTpl formatTplFromTable(String tableName,String name){
        PojoTpl tpl = new PojoTpl();
        tpl.setName(name);
        tpl.setTable(tableName);
        tpl.setPropertyList(formatListFromTableName(tableName));
        return tpl;
    }

    /**
     * 如果文件的父路径不存在则自动创建文件夹
     * @param fileName
     */
    private static void validateParentPath(String fileName){
        // TODO Auto-generated method stub
        if(fileName==null||fileName.indexOf("/")==-1)return;
        String parentPath = fileName.substring(0, fileName.lastIndexOf("/"));
        File f = new File(parentPath);
        if(!f.exists()) f.mkdir();
    }


    public static List<Property> formatListFromTableName(String tableName){
        String sql = "select column_name,data_type  from information_schema.columns where TABLE_NAME =?";
        List<DataRow> list = t.query(sql, new Object[]{tableName.toUpperCase()});
        List<Property> properList = new ArrayList<Property>();
        if(list==null||list.size()==0) {
            throw new RuntimeException(String.format("%s表名不存在!",tableName));
        }
        Set<String> keys = new HashSet<String>();
        for(DataRow data:list){
            String name = data.getString("column_name");
            String type = data.getString("data_type");
            Property property = new Property();
            property.setKey(name);
            if(keys.contains(name))continue;
            if(type.equals("varchar")){
                property.setPrefix("String");
            }else if(type.equals("bigint")){
                property.setPrefix("Long");
            }else if(type.equals("int")||type.equals("tinyint")){
                property.setPrefix("Integer");
            }else if(type.equals("datetime")){
                property.setPrefix("Date");
            }else if(type.equals("longtext")){
                property.setPrefix("String");
            }else{
                property.setPrefix("Double");
            }
            properList.add(property);
            keys.add(name);
        }
        return properList;
    }


    public static List<Property> formatListFromTableNameWithCols(String table,List<String> cols){
        String sql = "select column_name,data_type  from information_schema.columns where TABLE_NAME =?";
        List<DataRow> list = t.query(sql, new Object[]{table.toUpperCase()});
        List<Property> properList = new ArrayList<Property>();
        if(list==null||list.size()==0) {
            throw new RuntimeException(String.format("%s表名不存在!",table));
        }
        Set<String> keys = new HashSet<String>();
        for(DataRow data:list){
            String name = data.getString("column_name");
            if(!cols.contains(name))continue;
            String type = data.getString("data_type");
            Property property = new Property();
            property.setKey(name);
            if(keys.contains(name))continue;
            if(type.equals("varchar")){
                property.setPrefix("String");
            }else if(type.equals("bigint")){
                property.setPrefix("Long");
            }else if(type.equals("int")||type.equals("tinyint")){
                property.setPrefix("Integer");
            }else if(type.equals("datetime")){
                property.setPrefix("ErpDate");
            }else{
                property.setPrefix("Double");
            }
            properList.add(property);
            keys.add(name);
        }
        System.out.println(keys);
        return properList;
    }

    public static  void createTableByProps(String table,List<Property> list){
        int c  = t.queryInt("select count(*)  from information_schema.columns where TABLE_NAME =?",new Object[]{table});
        if(c>0)throw new RuntimeException(String.format("表%s已经存在",table));
        StringBuffer sql = new StringBuffer("CREATE TABLE ");
        sql.append(table);
        sql.append(" (");
        for(int i=0;i<list.size();i++){
            if(i!=0)sql.append(",");
            Property property = list.get(i);
            String prefix = property.getPrefix();
            String key = property.getKey();
            sql.append(key);
            if(prefix.equals("String")){
                sql.append(" varchar(20)");
            }else if(prefix.equals("long")){
                sql.append(" bigint(20)");
            }else if(prefix.equals("int")){
                sql.append(" int(11)");
            }else if(prefix.equals("date")){
                sql.append(" datetime ");
            }
        }
        sql.append(")");
        System.out.println(sql.toString());
        t.update(sql.toString());
    }

    public static  void createTableByProps(String table,JSONObject root){
        createTableByProps(table,parsePropertyFromJson(root));
    }
    public static List<Property> parsePropertyFromJson(JSONObject root){
        if(root==null||root.size()==0)return null;
        List<Property> list = new ArrayList<Property>();
        for(Iterator<String> it = root.keySet().iterator();it.hasNext();){
            String key = it.next();
            Object value = root.get(key);
            Property property = new Property();
            property.setValue(key);
            if(value  instanceof String){
                property.setPrefix("String");
            }else if(value instanceof Integer){
                int v = (Integer) value;
                if(v>100000){
                    property.setPrefix("long");
                }else{
                    property.setPrefix("int");
                }
            }else if(value instanceof Long){
                property.setPrefix("long");
            }else if(value instanceof Double){
                property.setPrefix("double");
            }else if(value instanceof Float){
                property.setPrefix("float");
            }else if(value==null||StringUtils.isEmpty(value.toString())){
                property.setPrefix("String");
            }else{
                continue;
            }
            if(property.getPrefix()!=null)list.add(property);
        }
        return list;
    }



    public static void createJava(PojoTpl tpl){
        createPojo(tpl);
        createCovert(tpl);
        //createService(tpl);
    }


    public static  void createPojo(String name ,JSONObject root){
        PojoTpl pojoTpl = new PojoTpl();
        pojoTpl.setName(name);
        pojoTpl.setPropertyList(parsePropertyFromJson(root));
        createPojo(pojoTpl);
    }
    public static  void createCovert(String name ,JSONObject root){
        PojoTpl pojoTpl = new PojoTpl();
        pojoTpl.setName(name);
        pojoTpl.setPropertyList(parsePropertyFromJson(root));
        createCovert(pojoTpl);
    }
    public static  void createService(String table,String name ,JSONObject root){
        PojoTpl pojoTpl = new PojoTpl();
        pojoTpl.setName(name);
        pojoTpl.setPropertyList(parsePropertyFromJson(root));
        createService(table,pojoTpl);
    }

    public static void createORM(String table,String name,List<String> cols){
        if(StringUtils.isEmpty(name)||name.length()<2)return;
        name = String.format("%s%s",name.substring(0, 1).toUpperCase(), name.substring(1));
        List<Property> props =formatListFromTableNameWithCols(table,cols);
        System.out.println(props.size());
        PojoTpl pojoTpl = new PojoTpl();
        pojoTpl.setName(name);
        pojoTpl.setPropertyList(props);
        createPojo(pojoTpl);
        createCovert(pojoTpl);
        createService(table,pojoTpl);
    }
    public static void createORM(String table,String name){
        if(StringUtils.isEmpty(name)||name.length()<2)return;
        name = String.format("%s%s",name.substring(0, 1).toUpperCase(), name.substring(1));
        List<Property> props =formatListFromTableName(table);
        PojoTpl pojoTpl = new PojoTpl();
        pojoTpl.setName(name);
        pojoTpl.setPropertyList(props);
        createPojo(pojoTpl);
        createCovert(pojoTpl);
        createService(table,pojoTpl);
    }

    public static  void auto(String table){

    }



    public static void main(String[] args) {
        createORM("wms_user","WmsUser");

    }


}
