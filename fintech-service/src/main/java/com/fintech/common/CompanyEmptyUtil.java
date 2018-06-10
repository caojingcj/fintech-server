package com.fintech.common;

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
public class CompanyEmptyUtil  {
    /** 
    * @Title: CompanyEmptyUtil.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年6月10日 下午11:23:19  
    * @param @param baseinfo    设定文件 
    * @Description: TODO[ 这里用一句话描述这个方法的作用 ]
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
    
    public static void main(String[] args) {
        try {
            CompanyEmptyUtil.CompanyEmpty(new CompanyBaseinfo());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
