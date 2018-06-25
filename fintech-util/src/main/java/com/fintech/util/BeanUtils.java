package com.fintech.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;

/**   
* @Title: BeanUtils.java 
* @Package com.medcfc.commons.utils 
* @author qierkang xyqierkang@163.com   
* @date 2017年9月8日 上午2:39:05  
* @Description: TODO[ 用一句话描述该文件做什么 ]
*/
public abstract class BeanUtils extends org.springframework.beans.BeanUtils {
    private static final Logger     logger = LoggerFactory.getLogger(BeanUtils.class);
	/** 
	* @Title: BeanUtils.java 
	* @author qierkang xyqierkang@163.com   
	* @date 2017年9月8日 上午2:39:16  
	* @param @param source
	* @param @param target
	* @param @throws BeansException    设定文件 
	* @Description: TODO[ 这里用一句话描述这个方法的作用 ]
	* @throws 
	*/
	public static void copyProperties(Object source, Object target) throws BeansException {
		Class<?> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		for (PropertyDescriptor targetPd : targetPds) {
//			System.out.println(targetPd.getName());
			if (targetPd.getWriteMethod() != null) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);
						if (value != null) {
							Method writeMethod = targetPd.getWriteMethod();
							if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							writeMethod.invoke(target, value);
						}
					} catch (Throwable ex) {
					    logger.error("类型转换错误>>>>>>{}",targetPd.getName());
						throw new FatalBeanException("Could not copy properties from source to target", ex);
					}
				}
			}
		}
	}
}