package com.daoReconsitution.util;

import java.util.Collection;

import com.alibaba.fastjson.JSON;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 
 * @author 韩豆豆
 * @description json工具类
 */
public class CommonUtil {
	/**
	 * 转为datagrid json
	 */
	public static JSONObject toGridJson(int totalCount, Object obj) {
	    if(null==obj){
	        JSONObject jsonResult = new JSONObject();
	        jsonResult.put("total",totalCount);
	        jsonResult.put("rows",new JSONArray());
	        return jsonResult;
	    }
	    if(!Collection.class.isAssignableFrom(obj.getClass())) {
	        JSONObject jsonResult = new JSONObject();
	        jsonResult.put("total", totalCount);
	        jsonResult.put("rows", new JSONArray());
	        return jsonResult;
	    }
	    String json = JSON.toJSONString(obj);
	    JSONObject jsonResult = new JSONObject();
	    jsonResult.put("total", totalCount);
	    jsonResult.put("rows", obj);
	    return jsonResult;
	}
}
