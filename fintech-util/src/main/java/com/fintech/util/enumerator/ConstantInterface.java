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
	    * @Description: TODO[ 商户属性验证 100=商户code ]
	    */
	    enum CompanyValidate{
	        COMPANY_NAME_IS_NULL(100001,"商户名称不能为空"),
	        COMPANY_TYPE_IS_NULL(100002,"商户类型不能为空"),
	        COMPANY_STATUS_IS_NULL(100003,"商户状态不能为空"),
	        COMPANY_USCC_IS_NULL(100004,"商户统一社会信用代码不能为空"),
	        COMPANY_FULL_NAME_IS_NULL(100005,"商户营业执照名称不能为空"),
	        COMPANY_100006(100006,"该订单已被后台取消");
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
	    /**   
	    * @Title: ConstantInterface.java 
	    * @Package com.fintech.util.enumerator 
	    * @author qierkang xyqierkang@163.com   
	    * @date 2018年6月19日 下午11:48:39  
	    * @Description: TODO[ 200=订单code ]
	    */
	    enum OrderValidate{
            ORDER_200001(200001,"总在还款额不超过50w或分期还款中的笔数不超过3笔"),
            ORDER_200002(200002,"主code200002");
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
            private OrderValidate(Integer key, String value) {
                this.key = key;
                this.value = value;
            }
        }
	    /**   
	    * @Title: ConstantInterface.java 
	    * @Package com.fintech.util.enumerator 
	    * @author qierkang xyqierkang@163.com   
	    * @date 2018年6月12日 上午1:26:50  
	    * @Description: TODO[ 通用返回值 ]
	    */
	    enum ObjectNullValidate{
	    	OBJECT_NAME_IS_NULL(00010,"该属性不能为空"),
	    	OBJECT_MESSAGE_IS_NULL(00011,"属性传入错误，请检查属性是否正确");
	    	private Integer key;
	    	private String value;
			/**
			 * @return the key
			 */
			public Integer getKey() {
				return key;
			}
			/** 
			* @param key 要设置的 key 
			*/
			public void setKey(Integer key) {
				this.key = key;
			}
			/**
			 * @return the value
			 */
			public String getValue() {
				return value;
			}
			/** 
			* @param value 要设置的 value 
			*/
			public void setValue(String value) {
				this.value = value;
			}
			/** 
			* @Title: ConstantInterface.java 
			* @param @param key
			* @param @param value    设定文件 
			* @Description: TODO[ 这里用一句话描述这个方法的作用 ]
			* @throws 
			*/
			private ObjectNullValidate(Integer key, String value) {
				this.key = key;
				this.value = value;
			}
	    }
	}

}
