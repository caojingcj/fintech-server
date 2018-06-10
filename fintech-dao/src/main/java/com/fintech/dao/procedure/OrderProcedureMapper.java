package com.fintech.dao.procedure;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

/**   
* @Title: OrderProcedureMapper.java 
* @Package com.fintech.dao.procedure 
* @author qierkang xyqierkang@163.com   
* @date 2018年6月10日 下午10:24:27  
* @Description: TODO[ spring Boot 订单存储过程 ]
*/
@Mapper
public interface OrderProcedureMapper {
    @Select("{call generateOrderId(@)}")
    @Options(statementType = StatementType.CALLABLE)
    String generateOrderId();
}
