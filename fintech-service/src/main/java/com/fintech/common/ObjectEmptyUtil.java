package com.fintech.common;

import java.lang.reflect.Method;

import com.fintech.model.CompanyBaseinfo;
import com.fintech.util.FinTechException;
import com.fintech.util.StringUtil;
import com.fintech.util.enumerator.ConstantInterface;

/**   
* @Title: CompanyEmptyUtil.java 
* @Package com.fintech.common 
* @author qierkang xyqierkang@163.com   
* @date 2018年6月10日 下午11:23:17  
* @Description: TODO[ 商户类属性验证 ]
*/
public class ObjectEmptyUtil  {
    /** 
    * @Title: CompanyEmptyUtil.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年6月10日 下午11:23:19  
    * @param @param baseinfo    设定文件 
    * @Description: TODO[ 商户通用属性判断 ]
    * @throws 
    */
    public static void CompanyEmpty(CompanyBaseinfo companyBaseinfo) {
    		if (StringUtil.isEmpty(companyBaseinfo.getCompanyName())) {
                throw new FinTechException(ConstantInterface.ValidateConfig.CompanyValidate.COMPANY_NAME_IS_NULL.getKey(),ConstantInterface.ValidateConfig.CompanyValidate.COMPANY_NAME_IS_NULL.getValue());
            }
            if (StringUtil.isEmpty(companyBaseinfo.getCompanyType())) {
                throw new FinTechException(ConstantInterface.ValidateConfig.CompanyValidate.COMPANY_TYPE_IS_NULL.getKey(),ConstantInterface.ValidateConfig.CompanyValidate.COMPANY_TYPE_IS_NULL.getValue());
            }
            if (StringUtil.isEmpty(companyBaseinfo.getCompanyStatus())) {
                throw new FinTechException(ConstantInterface.ValidateConfig.CompanyValidate.COMPANY_STATUS_IS_NULL.getKey(),ConstantInterface.ValidateConfig.CompanyValidate.COMPANY_STATUS_IS_NULL.getValue());
            }
            if (StringUtil.isEmpty(companyBaseinfo.getCompanyUscc())) {
                throw new FinTechException(ConstantInterface.ValidateConfig.CompanyValidate.COMPANY_USCC_IS_NULL.getKey(),ConstantInterface.ValidateConfig.CompanyValidate.COMPANY_USCC_IS_NULL.getValue());
            }
            if (StringUtil.isEmpty(companyBaseinfo.getCompanyFullName())) {
                throw new FinTechException(ConstantInterface.ValidateConfig.CompanyValidate.COMPANY_FULL_NAME_IS_NULL.getKey(),ConstantInterface.ValidateConfig.CompanyValidate.COMPANY_FULL_NAME_IS_NULL.getValue());
            }
    }
    
    /** 
    * @Title: CompanyEmptyUtil.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年6月12日 上午1:27:12  
    * @param @param fieldName
    * @param @param o
    * @param @return    设定文件 
    * @Description: TODO[ 判断Object对象 获取里面属性是否有值 ]
    * @throws 
    */
    public static  Object isObjectEmptyByName(Object[] obj, Object o) {
    	String fieldName="";
        	Object value=null;
        		for (Object object : obj) {
        			fieldName=object.toString();
        			String firstLetter = ((String) object).substring(0, 1).toUpperCase();
        			String getter = "get" + firstLetter + ((String) object).substring(1);
        			 try {
						Method method = o.getClass().getMethod(getter, new Class[] {});
						 value = method.invoke(o, new Object[] {});
					} catch (Exception e) {
			            throw new FinTechException(ConstantInterface.ValidateConfig.ObjectNullValidate.OBJECT_MESSAGE_IS_NULL.getKey(),ConstantInterface.ValidateConfig.ObjectNullValidate.OBJECT_MESSAGE_IS_NULL.getValue()+"属性名："+fieldName);
			        } 
        			 if(null==value) {
        		            throw new FinTechException(ConstantInterface.ValidateConfig.ObjectNullValidate.OBJECT_NAME_IS_NULL.getKey(),ConstantInterface.ValidateConfig.ObjectNullValidate.OBJECT_NAME_IS_NULL.getValue()+"属性名："+fieldName);
        			 }
				}
        	return value;
    }
    
    public static  void isEmptyByName(Object o) {
    	if(StringUtil.isEmpty(o.toString())) {
			throw new FinTechException(ConstantInterface.ValidateConfig.ObjectNullValidate.OBJECT_NAME_IS_NULL.getKey(),ConstantInterface.ValidateConfig.ObjectNullValidate.OBJECT_NAME_IS_NULL.getValue());
    	}
    }
    
    public static void main(String[] args) {
        try {
        	String aa="12321";
        	isEmptyByName(aa);        	
//        	Object[] objs={"companyId1"};
//        	CompanyBaseinfo baseinfo=new CompanyBaseinfo();
//        	System.out.println(isObjectEmptyByName(objs, baseinfo));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
