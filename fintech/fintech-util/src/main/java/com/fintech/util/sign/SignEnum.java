package com.fintech.util.sign;

import java.util.HashMap;
import java.util.Map;

public interface SignEnum {
	
	enum  Code {
		CODE_70000(70000, "处理成功!"),
		CODE_70001(70001, "处理失败!"),
		CODE_70002(70002, "token缺失!"),
		CODE_70003(70003, "该token已经被验证过!"),
		CODE_70004(70004, "验证通过!"),
		CODE_70005(70005, "token无效!"),
		CODE_70006(70006, "token已过期!"),
		CODE_70008(70008, "token时间戳无效"),
		CODE_70007(70007, "token数据格式错误"),
		CODE_70009(70009, "解密失败,token可能遭到篡改"),
		CODE_70010(70010, "sign签名错误!");
		private final Integer key;
		private final String value;

		private Code(Integer key, String value) {
			this.key = key;
			this.value = value;
		}

		public Map<Integer, String> tomap() {
			Map<Integer, String> map = new HashMap<Integer, String>();
			map.put(this.key, this.value);
			return map;
		}
		/**
		 * @Title: getKey
		 * @Description: ( )
		 */
		public Integer getKey() {
			return key;
		}

		/**
		 * @Title: getValue
		 * @Description: ( )
		 */
		public String getValue() {
			return value;
		}
	}
}
