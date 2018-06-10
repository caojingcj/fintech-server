package com.fintech.util.enumerator;

import java.util.HashMap;
import java.util.Map;

/**   
* @Title: ConstantInterface.java 
* @Package com.fintech.util.enumerator 
* @author qierkang xyqierkang@163.com   
* @date 2018年6月10日 上午4:04:49  
* @Description: TODO[ 公共枚举 ]
*/
public interface ConstantInterface {

	/**
	 * @author qierkang
	 * @ClassName: Enum
	 * @date 2016年5月31日
	 * @Description: (枚举)
	 */
	public interface Enum {

		/**
		 * @author qierkang
		 * @ClassName: ConstantNumber
		 * @date 2016年5月19日
		 * @Description: (整数枚举)
		 */
		enum ConstantNumber {
			/**
			 * @Fields NEGATIVE : ( -1 )
			 */
			NEGATIVE(-1),
			/**
			 * @Fields ZERO : ( 0 )
			 */
			ZERO(0),
			/**
			 * @Fields ONE : ( 1 )
			 */
			ONE(1),
			/**
			 * @Fields TOW : ( 2 )
			 */
			TOW(2),
			/**
			 * @Fields THREE : ( 3 )
			 */
			THREE(3),
			/**
			 * @Fields FOUR : ( 4 )
			 */
			FOUR(4),
			/**
			 * @Fields FIVE : ( 5 )
			 */
			FIVE(5),
			/**
			 * @Fields FIVE : ( 6 )
			 */
			SIX(6),
			/**
			 * @Fields FIVE : ( 7 )
			 */
			Seven(7),
			/**
			 * @Fields FIVE : ( 8 )
			 */
			Eight(8),
			/**
			 * @Fields FIVE : ( 9 )
			 */
			Nine(9),
			/**
			 * @Fields FIVE : ( 10 )
			 */
			Ten(10);

			private final Integer key;

			private ConstantNumber(Integer key) {
				this.key = key;
			}

			/**
			 * @Title: getKey
			 * @Description: ()
			 */
			public Integer getKey() {
				return key;
			}
		}
	}
	public interface DruidDataConfig {
	    /**
	     * @author qierkang
	     * @ClassName: ConstantNumber
	     * @date 2016年5月19日
	     * @Description: (数据库信息 多数据源需要指定Map如 mapper/fintech/*.xml  mapper/xxxx/*.xml)
	     * 精确到 mapper 和他数据源隔离
	     */
	   enum DRUIDDATA_CONFIG {
	         PROCEDURE_MAPPER("classpath*:mapper/fintech/procedure/*.xml"),
	        FINTECH_MAPPER("classpath*:mapper/fintech/*.xml");
	        private String value;
            public String getValue() {
                return value;
            }
            private DRUIDDATA_CONFIG(String value) {
                this.value = value;
            }
            
	    }
	}
	
	/**   
	* @Title: ConstantInterface.java 
	* @Package com.fintech.util.enumerator 
	* @author qierkang xyqierkang@163.com   
	* @date 2018年6月10日 下午11:37:23  
	* @Description: TODO[ 验证统一枚举 ]
	*/
	public interface ValidateConfig {
	    /**   
	    * @Title: ConstantInterface.java 
	    * @Package com.fintech.util.enumerator 
	    * @author qierkang xyqierkang@163.com   
	    * @date 2018年6月10日 下午11:37:22  
	    * @Description: TODO[ 商户属性验证 ]
	    */
	    enum CompanyValidate{
	        COMPANY_NAME_IS_NULL(10001,"商户名称不能为空"),
	        COMPANY_TYPE_IS_NULL(10002,"商户类型不能为空"),
	        COMPANY_STATUS_IS_NULL(10003,"商户状态不能为空"),
	        COMPANY_USCC_IS_NULL(10004,"商户统一社会信用代码不能为空"),
	        COMPANY_FULL_NAME_IS_NULL(10005,"商户营业执照名称不能为空");
	        private Integer key;
	        private String value;
            public Integer getKey() {
                return key;
            }
            public void setKey(Integer key) {
                this.key = key;
            }
            public String getValue() {
                return value;
            }
            public void setValue(String value) {
                this.value = value;
            }
            private CompanyValidate(Integer key, String value) {
                this.key = key;
                this.value = value;
            }
	    }
	}

}
