package com.fintech.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.dao.MasterAreaMapper;
import com.fintech.model.MasterArea;
import com.fintech.service.MasterAreaService;
import com.fintech.util.CommonUtil;
/**   
* @Title: MasterAreaImpl.java 
* @Package com.fintech.service.impl 
* @author qierkang xyqierkang@163.com   
* @date 2018年6月12日 上午3:26:08  
* @Description: TODO[ 通用省市区服务类 ]
*/
@Service
public class MasterAreaImpl implements MasterAreaService {
    @Autowired
    private MasterAreaMapper masterAreaMapper;
    /* (非 Javadoc) 
    * <p>Title: queryMasterAreaByKey</p> 
    * <p>Description: </p> 
    * @param params
    * @return 查询所有通用省市区
    * @see com.fintech.service.MasterAreaService#queryMasterAreaByKey(java.util.Map) 
    */
    @Override
    public List<MasterArea> queryMasterAreaByKey(Map<String, Object>params){
        return masterAreaMapper.selectByPrimaryKey(params);
    }
}
