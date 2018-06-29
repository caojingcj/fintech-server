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
		enum OrderStatus {
		    ORDER_STATUS00("00","录入中"),
		    ORDER_STATUS01("01","待审批"),
		    ORDER_STATUS02("02","审批中"),
		    ORDER_STATUS03("03","审批拒绝"),
		    ORDER_STATUS04("04","待用户签署"),
		    ORDER_STATUS05("05","分期还款中"),
		    ORDER_STATUS11("11","已取消"),
		    ORDER_STATUS12("12","已退款"),
		    ORDER_STATUS13("13","已提前结清"),
		    ORDER_STATUS99("99","已结清"),
		    ORDER_STATUS09("09","取消确认中");
            private final String key;
            private final String value;
            private OrderStatus(String key, String value) {
                this.key = key;
                this.value = value;
            }
            
            public String getKey() {
                return key;
            }

            public String getValue() {
                return value;
            }

            @Override
            public String toString() {
                return "[" + this.key + "]" + this.value;
            }
        }
		
		enum OrderLogStatus {
            ORDER_STATUS00("00","扫码"),
            ORDER_STATUS01("01","项目信息填写"),
            ORDER_STATUS02("02","身份信息认证"),
            ORDER_STATUS03("03","运营商认证"),
            ORDER_STATUS04("04","个人信息填写"),
            ORDER_STATUS05("05","附件上传"),
            ORDER_STATUS06("06","签署合同"),
            ORDER_STATUS91("91","审批"),
            ORDER_STATUS11("11","取消"),
            ORDER_STATUS12("12","退款"),
            ORDER_STATUS13("13","提前结清"),
            ORDER_STATUS99("99","结清"),
            ORDER_STATUS09("09","取消确认"),
            ORDER_STATUS81("81","取消驳回");
            private final String key;
            private final String value;
            private OrderLogStatus(String key, String value) {
                this.key = key;
                this.value = value;
            }
            
            public String getKey() {
                return key;
            }

            public String getValue() {
                return value;
            }

            @Override
            public String toString() {
                return "[" + this.key + "]" + this.value;
            }
        }
		
		enum PartyAInfo {
	        PartyA_Name("上海舵定信息科技有限公司"),
	        PartyA_CORPORATE_NAME("华妍"),
	        PartyA_COMPANY_ADDR("上海市奉贤区城乡路333号1幢一层1433室"),
	        PartyA_COMPANY_ACCOUNT_NAME("上海舵定信息科技有限公司"),
	        PartyA_COMPANY_ACCOUNT_BRANCH("宁波银行股份有限公司上海闵行支行"),
	        PartyA_COMPANY_ACCOUNT_NO("70050122000274069"),
	        PartyA_COMPANY_IDCARD("31011219921014522X");
		    private final String value;
		    private PartyAInfo(String value) {
		        this.value = value;
		    }
		    public String getValue() {
		        return value;
		    }
		    
		    @Override
		    public String toString() {
		        return "[" + this.value + "]" ;
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
	* @Description: TODO[ 验证统一枚举 100开头都是后台异常码 200app\微信错误码 ]
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
	
	/**   
	* @Title: ConstantInterface.java 
	* @Package com.fintech.util.enumerator 
	* @author qierkang xyqierkang@163.com   
	* @date 2018年6月25日 下午6:05:08  
	* @Description: TODO[ 200app\微信错误码、200001商户模块错误码、200100订单模块错误码  阈值范围1-100 ]
	*/
	public interface AppValidateConfig {
	    
	    enum CompanyValidate{
            COMPANY_200101(200101,"该商户已被禁用"),
            COMPANY_200102(200102,"该订单已被后台取消");
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
            @Override
            public String toString() {
                return "[" + this.key + "]" + this.value;
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
	            ORDER_200001(200201,"总在还款额不超过50w或分期还款中的笔数不超过3笔"),
	            ORDER_200002(200202,"该客户已经签署！"),
	            ORDER_200003(200203,"该客户已经认证！"),
	            ORDER_200004(200204,"认证异常，图片格式或清晰度有问题"),
	            ORDER_200005(200205,"null异常请求，请在公众号操作"),
	            ORDER_200006(200206,"异常请求，无法获取微信认证");
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
	            @Override
	            public String toString() {
	                return "[" + this.key + "]" + this.value;
	            }
	        }
	        
	        enum LoginValidate{
	            LOGIN_200300(200300,"登录失效，请从新登录"),
	            LOGIN_200301(200301,"验证码错误或失效！"),
	            LOGIN_200302(200302,"该订单已被后台取消");
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
	            private LoginValidate(Integer key, String value) {
	                this.key = key;
	                this.value = value;
	            }
	            @Override
	            public String toString() {
	                return "[" + this.key + "]" + this.value;
	            }
	        }
	        
	        enum ObjectRedisValidate{
	            OBJECT_REDIS_TOKEN("token"),
	            OBJECT_REDIS_CODE("code");
	            private String value;
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
	            private ObjectRedisValidate(String value) {
	                this.value = value;
	            }
	        }
	}
	
	

}
