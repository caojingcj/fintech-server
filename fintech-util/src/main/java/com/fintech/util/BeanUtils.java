package com.fintech.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
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
	
	 /**
     * 去掉bean中所有属性为字符串的前后空格
     * @param bean
     * @throws Exception
     */
    public static void beanValueTrim(Object bean) {
        if(bean!=null){
           try {
        	   //获取所有的字段包括public,private,protected,private
               Field[] fields = bean.getClass().getDeclaredFields();
               for (int i = 0; i < fields.length; i++) {
                   Field f = fields[i];
                   if (f.getType().getName().equals("java.lang.String")) {
                       String key = f.getName();//获取字段名
                       Object value = getFieldValue(bean, key);
                       
                       if (value == null)
                           continue;
                       
                       setFieldValue(bean, key, value.toString().trim());
                   }
               }
		} catch (Exception e) {
			
		}
        }
    }

    /**
     * 利用反射通过get方法获取bean中字段fieldName的值
     * @param bean
     * @param fieldName
     * @return
     * @throws Exception
     */
    private static Object getFieldValue(Object bean, String fieldName)
            throws Exception {
        StringBuffer result = new StringBuffer();
        String methodName = result.append("get")
                .append(fieldName.substring(0, 1).toUpperCase())
                .append(fieldName.substring(1)).toString();

        Object rObject = null;
        Method method = null;

        @SuppressWarnings("rawtypes")
        Class[] classArr = new Class[0];
        method = bean.getClass().getMethod(methodName, classArr);
        rObject = method.invoke(bean, new Object[0]);

        return rObject;
    }

    /**
     * 利用发射调用bean.set方法将value设置到字段
     * @param bean
     * @param fieldName
     * @param value
     * @throws Exception
     */
    private static void setFieldValue(Object bean, String fieldName, Object value)
            throws Exception {
        StringBuffer result = new StringBuffer();
        String methodName = result.append("set")
                .append(fieldName.substring(0, 1).toUpperCase())
                .append(fieldName.substring(1)).toString();

        /**
         * 利用发射调用bean.set方法将value设置到字段
         */
        Class[] classArr = new Class[1];
        classArr[0]="java.lang.String".getClass();
        Method method=bean.getClass().getMethod(methodName,classArr);
        method.invoke(bean,value);
    }   
}