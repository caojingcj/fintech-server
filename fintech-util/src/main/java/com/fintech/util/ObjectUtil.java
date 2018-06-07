package com.fintech.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * byte数组与对象相互转化，以及通过序列化进行深克隆的工具类<br>
 * 已合并至com.medcfc.util.javabean.JavaBeanUtil
 * 
 * @see com.medcfc.util.javabean.JavaBeanUtil
 * @author yuqih
 * 
 */
@Deprecated
public class ObjectUtil {
	private static Logger log = LoggerFactory.getLogger(ObjectUtil.class);

	/**
	 * 通过序列化的方式深克隆
	 * 
	 * @param <T>
	 * @param source
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object deepClone(Object source) {
		try {
			// 将对象写到流里
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(source);
			// 从流里读出来
			ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
			ObjectInputStream oi = new ObjectInputStream(bi);
			return oi.readObject();

		} catch (Exception e) {
			log.error("复制对象失败" + e.getMessage());
		}
		return source;
	}

	/**
	 * 通过序列化的方式深克隆
	 * 
	 * @param <T>
	 * @param source
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T deepClone(T source, Class<T> clazz) {
		return (T) deepClone(source);
	}

	/**
	 * 将对象转化为byte数组
	 * 
	 * @param object
	 * @return
	 * @throws IOException
	 */
	public static byte[] objectToByteArray(Object object) throws IOException {

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				byteArrayOutputStream);

		objectOutputStream.writeObject(object);
		objectOutputStream.flush();

		return byteArrayOutputStream.toByteArray();
	}

	/**
	 * 将byte数组转化为对象
	 * 
	 * @param b
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object byteArrayToObject(byte[] b) throws IOException,
			ClassNotFoundException {

		InputStream is = new ByteArrayInputStream(b);
		ObjectInputStream ois = null;
		ois = new ObjectInputStream(is);

		return ois.readObject();

	}


	/**
	 * Object 转换为MAP
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> object2Map(Object obj) throws Exception {
		if(obj == null){
			return null;
		}
		//获取关联的所有类，本类以及所有父类
		boolean ret = true;
		Class oo = obj.getClass();
		List<Class> clazzs = new ArrayList<Class>();
		while(ret){
			clazzs.add(oo);
			oo = oo.getSuperclass();
			if(oo == null || oo == Object.class)break;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		for(int i=0;i<clazzs.size();i++){
			Field[] declaredFields = clazzs.get(i).getDeclaredFields();
			for (Field field : declaredFields) {
				int mod = field.getModifiers();
				//过滤 static 和 final 类型
				if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
					continue;
				}
				field.setAccessible(true);
				map.put(field.getName(), field.get(obj));
			}
		}

		return map;
	}

}
