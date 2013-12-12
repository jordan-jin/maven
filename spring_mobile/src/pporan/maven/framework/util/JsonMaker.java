/**
 * @author jinbos
 * @version 00.00.01
 * @date 2012.11.09
 * @category kr.co.etribe.framework.util
 * @description JSON형식에 맞추어 Json Data 생성하는 클래스
 */
package pporan.maven.framework.util;

import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonMaker {

	
	public static JSONObject getJsonObjectMake(String keys, HashMap dataMap){
		JSONObject returnObj = new JSONObject();
		
		String[] item_keys = keys.split(",");
		
		for(int i=0; i<item_keys.length; i++){
			returnObj.put(item_keys[i].toLowerCase(), nvl(dataMap.get(item_keys[i])));
		}
		
		return returnObj;
	}
	
	
	public static JSONObject getJsonObjectMake(String keys, String regex, HashMap dataMap){
		JSONObject returnObj = new JSONObject();
		
		String[] item_keys = keys.split(regex);
		
		for(int i=0; i<item_keys.length; i++){
			returnObj.put(item_keys[i].toLowerCase(), nvl(dataMap.get(item_keys[i])));
		}
		
		return returnObj;
	}
	
	
	public static JSONArray getJsonArrayMake(String keys, String regex, List dataList){
		JSONArray returnArr = new JSONArray();
		JSONObject obj = new JSONObject();
		
		String[] item_keys = keys.split(regex);
		
		for(int i=0; i<dataList.size(); i++){
			HashMap dataMap = (HashMap)dataList.get(i);
			for(int j=0; j<item_keys.length; j++){
				obj.put(item_keys[j].toLowerCase(), nvl(dataMap.get(item_keys[j])));
			}
			returnArr.add(obj);
		}
		
		return returnArr;
	}
	
	private static String nvl(Object obj){
		String str="";
		
		if (obj == null)
			str = "";
		else{
			try{				
				str = (String)obj;	
			}catch(Exception e){
				try{
					str = obj.toString();				
				}catch(Exception ex){
					str = "";
				}
			}
		}
		
		if(str.equals("null") || (str.length() == 0))
			str = "";
		
		return str;
	}
}
