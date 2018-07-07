package com.fintech.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fintech.util.enumerator.ConstantInterface;

/** 
 *  通用多层json递归解析。主要是在没有Object对象，或者是极度复杂的多级嵌套json，情况下可以以类的方式，直接获取想要的结果。
 *  支持String、Map、ArrayList、ArrayMap四种返回对象的数据获取
 *  使用方式：根据json层级关系直接使用: 基节点.子节点.孙节点
 */
public class JsonTools {
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL) //类头部注解，空不生成json节点
	private static String jsonStr = "{\"api\":\"2.1\",\"message\":[\"产品\",\"tokken\"],\"request\":{\"ptype\":\"\",\"tokken\":\"A#daDSFkiwi239sdls#dsd\"},\"response\":{\"status\":{\"statusCode\":\"500\",\"statusMessage\":[\"产品类型错误\",\"tokken失效\"]},\"page\":{\"pageSize\":\"100\",\"pageIndex\":\"1\"},\"data\":{\"ptitle\":\"all product lists\",\"sDate\":\"2014-12-01\",\"eDate\":\"2016-12-01\",\"productList\":[{\"pid\":\"RA001\",\"pname\":\"产品1\"},{\"pid\":\"RA002\",\"pname\":\"产品2\"}]}},\"args\":[{\"tit\":\"RA001\",\"val\":\"产品1\"},{\"tit\":\"RA002\",\"val\":\"产品2\"}]}";
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static void main(String[] args) throws Exception {
//		System.out.println(json);
		JsonValidator jv = new JsonValidator();
		if(jv.validate(jsonStr)){
			JsonTools jsonTools=new JsonTools();
			//测试通过json获取Object对象
//			Object obj = jsonTools.getObjectByJson(jsonStr,"request.tokken",ConstantInterface.Enum.TypeEnum.string); //层级递归String
//			System.out.println("API:"+obj.toString());
			
			Object obj = jsonTools.getObjectByJson(jsonStr,"response.page",ConstantInterface.Enum.TypeEnum.map);  //层级递归Map
			System.out.println("API:"+obj.toString()+((Map<String, Object>)obj).get("pageSize"));
			
//			Object obj = jsonTools.getObjectByJson(jsonStr,"response.status.statusMessage",ConstantInterface.Enum.TypeEnum.arrayList); //层级递归ArrayList
//			System.out.println("API:"+obj.toString()+((List)obj).get(0));
			
//			Object obj = jsonTools.getObjectByJson(jsonStr,"response.data.productList",ConstantInterface.Enum.TypeEnum.arrayMap);   //层级递归ArrayMap 
//			System.out.println("API:"+obj.toString()+((List<Map>)obj).get(1).get("pid"));
			
			
			//测试Objectz转json
			/*
			Map mapPars = new HashMap();
			mapPars.put("agentCode", "SH0001");
			mapPars.put("date", "2014-01-10");
			mapPars.put("url", "http://www.map.com/maps.jsp?tag=2");
			
			Map mapArgs = new HashMap();
			mapArgs.put("query", mapPars);

			Map maps = new HashMap();
			maps.put("request", mapArgs);
			maps.put("date", "2014-10-10");
			
			System.out.println(getObjectByJson(maps));
			*/
			
			//测试json层级递归显示效果
			//Map maps = mapper.readValue(jsonStr, Map.class);
			//viewJsonTree(maps);
		}else{
			System.out.println("JSON数据格式错误，请核查。");
		}
	}

	/** 复杂嵌套Map转Json  */
	public static String getObjectByJson(Object obj){
		String str = "";
		try {
			str = mapper.writeValueAsString(obj);
		} catch (Exception e) {
			System.out.println("###[Error] getObjectToJson() "+e.getMessage());
		}
		return str;
	}
	/** 复杂嵌套Json层级展示  */
	public static Object viewJsonTree(Object m){
		if(m == null){ System.out.println("over...");return false;}

		try {
			Map mp = null;
			List ls = null;
			if(m instanceof Map || m instanceof LinkedHashMap){
				mp = (LinkedHashMap)m;
				for(Iterator ite = mp.entrySet().iterator(); ite.hasNext();){  
					Map.Entry e = (Map.Entry) ite.next();  

					if(e.getValue() instanceof String){
						System.out.println("[String]"+e.getKey()+" | " + e.getValue());
					}else if(e.getValue() instanceof LinkedHashMap){
						System.out.println("{Map}"+ e.getKey()+" | "+e.getValue());
						viewJsonTree((LinkedHashMap)e.getValue());
					}else if(e.getValue() instanceof ArrayList){
						System.out.println("[Array]"+ e.getKey()+" | "+e.getValue());
						viewJsonTree((ArrayList)e.getValue());
					}
				}  	
			}
			if(m instanceof List || m instanceof ArrayList){
				ls = (ArrayList)m;
				for(int i=0;i<ls.size();i++){
					if(ls.get(i) instanceof LinkedHashMap){
						viewJsonTree((LinkedHashMap)ls.get(i));
					}else if(ls.get(i) instanceof ArrayList){
						viewJsonTree((ArrayList)ls.get(i));
					}	
				}
			}	
			System.out.println();
		} catch (Exception e) {
			System.out.println("###[Error] viewJsonTree() "+e.getMessage());
		}
		return null;
	}	
	
	
	private int i = 0;
	/** 复杂嵌套Json获取Object数据  */
	public Object getObjectByJson(String jsonStr,String argsPath,ConstantInterface.Enum.TypeEnum argsType){
		if(argsPath == null || argsPath.equals("") || argsType == null){ 
			System.out.println("over...");return null;
		}
		
		Object obj = null;
		try {
			Map maps = mapper.readValue(jsonStr, Map.class);
			//多层获取
			if(argsPath.indexOf(".") >= 0){
				//类型自适应
				obj = getObject(maps,argsPath,argsType);
			}else{ //第一层获取
				if(argsType == ConstantInterface.Enum.TypeEnum.string){
					obj = maps.get(argsPath).toString();
				}else if(argsType == ConstantInterface.Enum.TypeEnum.map){
					obj = (Map)maps.get(argsPath);
				}else if(argsType == ConstantInterface.Enum.TypeEnum.arrayList){
					obj = (List)maps.get(argsPath);
				}else if(argsType == ConstantInterface.Enum.TypeEnum.arrayMap){
					obj = (List<Map>)maps.get(argsPath);
				}
			}
		} catch (Exception e) {
			System.out.println("###[Error] getObjectByJson() "+e.getMessage());
		}
		return obj;
	}
	//递归获取object
	private Object getObject(Object m,String key,ConstantInterface.Enum.TypeEnum type){
		if(m == null){ System.out.println("over...");return null;}
		Object o = null; //用于返回的对象
		
		Map mp = null;
		List ls = null;
		try {
			//{}对象层级递归遍历解析
			if(m instanceof Map || m instanceof LinkedHashMap){
				mp = (LinkedHashMap)m;
				for(Iterator ite = mp.entrySet().iterator(); ite.hasNext();){  
					Map.Entry e = (Map.Entry) ite.next();  
					
					if(i<key.split("\\.").length && e.getKey().equals(key.split("\\.")[i])){
						System.out.println("["+key.split("\\.").length+"]["+key+"]["+(i+1)+"][OK]["+key.split("\\.")[i]+"]"); //Val [" + e.toString()+"]
						i++;
						if(e.getValue() instanceof String){
							//递归最后一次
							if(i== key.split("\\.").length){
								o = e.getValue();
								return o;
							}
						}else if(e.getValue() instanceof LinkedHashMap){
							//递归最后一次
							if(i== key.split("\\.").length){
								if(type == ConstantInterface.Enum.TypeEnum.map){
									o = (LinkedHashMap)e.getValue();
									return o;
								}
							}else{
								o = getObject((LinkedHashMap)e.getValue(),key,type);
							}
							return o;
						}else if(e.getValue() instanceof ArrayList){
							//递归最后一次
							if(i== key.split("\\.").length){
								if(type == ConstantInterface.Enum.TypeEnum.arrayList){
									o = (ArrayList)e.getValue();
									return o;
								}
								if(type == ConstantInterface.Enum.TypeEnum.arrayMap){
									o = (ArrayList<Map>)e.getValue();
									return o;
								}
							}else{
								o = getObject((ArrayList)e.getValue(),key,type);
							}
							return o;
						}
					}else{
						System.out.println("["+key.split("\\.").length+"]["+key+"]["+(i+1)+"][NO]["+e.getKey()+"]");
					}
				}  	
			}
			//[]数组层级递归遍历解析
			if(m instanceof List || m instanceof ArrayList){
				ls = (ArrayList)m;
				for(int i=0;i<ls.size();i++){
					if(ls.get(i) instanceof LinkedHashMap){
						//递归最后一次
						if(i== key.split("\\.").length){
							if(type == ConstantInterface.Enum.TypeEnum.map){
								o = (LinkedHashMap)ls.get(i);
								return o;
							}
						}else{
							o = getObject((LinkedHashMap)ls.get(i),key,type);
						}
						return o;
					}else if(ls.get(i) instanceof ArrayList){
						//递归最后一次
						if(i== key.split("\\.").length){
							if(type == ConstantInterface.Enum.TypeEnum.arrayList){
								o = (ArrayList)ls.get(i);
								return o;
							}
							if(type == ConstantInterface.Enum.TypeEnum.arrayMap){
								o = (ArrayList<Map>)ls.get(i);
								return o;
							}
						}else{
							o = getObject((ArrayList)ls.get(i),key,type);
						}
						return o;
					}	
				}
			}	
			System.out.println();
		} catch (Exception e) {
			System.out.println("###[Error] getObject() "+e.getMessage());
		}
		
		return o;
	}
}