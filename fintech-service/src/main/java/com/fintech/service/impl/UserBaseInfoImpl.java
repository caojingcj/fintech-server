package com.fintech.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.dao.UserBaseinfoMapper;
import com.fintech.model.UserBaseinfo;
import com.fintech.model.vo.UserBaseinfoVo;
import com.fintech.service.RedisService;
import com.fintech.service.UserBaseinfoService;
import com.fintech.util.BeanUtils;
import com.fintech.util.CommonUtil;
import com.fintech.util.FinTechException;
import com.fintech.util.MD5Util;
import com.fintech.util.StringUtil;
import com.fintech.util.enumerator.ConstantInterface;
import com.fintech.util.sign.TokenUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;

/**   
* @Title: UserBaseInfoImpl.java 
* @Package com.fintech.service.impl 
* @author qierkang xyqierkang@163.com   
* @date 2018年7月19日 下午8:52:33  
* @Description: TODO[ 用一句话描述该文件做什么 ]
*/
@Service
public class UserBaseInfoImpl implements UserBaseinfoService {

	@Autowired
	private RedisService redisService;
	@Autowired
	private UserBaseinfoMapper userBaseinfoMapper;

	/* (非 Javadoc) 
	* <p>Title: manageLogin</p> 
	* <p>Description: </p> 
	* @param name
	* @param pwd
	* @return
	* @throws Exception 
	* @see com.fintech.service.UserBaseinfoService#manageLogin(java.lang.String, java.lang.String) 
	*/
	@Override
	public UserBaseinfoVo manageLogin(String name, String pwd) throws Exception {
		UserBaseinfo userBaseinfo=userBaseinfoMapper.selectByPrimaryKey(name);
		Gson gson=new Gson();
		if(userBaseinfo==null) {
			throw new FinTechException(ConstantInterface.Enum.ObjectNullValidate.OBJECT_REDIS_KEY_99914.toString());
		}
		if(!userBaseinfo.getUserLoginPassword().equals(MD5Util.md5(pwd))) {
			throw new FinTechException(ConstantInterface.Enum.ObjectNullValidate.OBJECT_REDIS_KEY_99913.toString());
		}
		UserBaseinfoVo baseinfoVo=new UserBaseinfoVo();
		String token=TokenUtil.getToken();
		BeanUtils.copyProperties(userBaseinfo, baseinfoVo);
		baseinfoVo.setToken(token);
		redisService.set(token,gson.toJson(baseinfoVo));
		return baseinfoVo;
	}

	/* (非 Javadoc) 
	* <p>Title: selectByPrimaryKeyList</p> 
	* <p>Description: </p> 
	* @param vo
	* @return
	* @throws Exception 
	* @see com.fintech.service.UserBaseinfoService#selectByPrimaryKeyList(com.fintech.model.vo.UserBaseinfoVo) 
	*/
	@Override
	public PageInfo<UserBaseinfo> selectByPrimaryKeyList(UserBaseinfoVo vo) throws Exception {
		Map<String, Object> parms = CommonUtil.object2Map(vo);
        PageHelper.startPage(vo.getPageIndex(), vo.getPageSize());
        List<UserBaseinfo> list=userBaseinfoMapper.selectByPrimaryKeyList(parms);
        PageInfo<UserBaseinfo> pageLists=new PageInfo<UserBaseinfo>(list);
        return pageLists;
	}

	/* (非 Javadoc) 
	* <p>Title: insertUserBaseInfo</p> 
	* <p>Description: </p> 
	* @param vo 
	* @see com.fintech.service.UserBaseinfoService#insertUserBaseInfo(com.fintech.model.vo.UserBaseinfoVo) 
	*/
	@Override
	public void insertUserBaseInfo(UserBaseinfoVo vo) {
		UserBaseinfo userBaseinfo=new UserBaseinfo();
		BeanUtils.copyProperties(vo, userBaseinfo);
		userBaseinfo.setUserLoginPassword(MD5Util.md5(userBaseinfo.getUserLoginPassword()));
		userBaseinfo.setIsEnabled(true);
		userBaseinfoMapper.insertSelective(userBaseinfo);
	}

	/* (非 Javadoc) 
	* <p>Title: updateUserBaseInfo</p> 
	* <p>Description: </p> 
	* @param vo 
	* @see com.fintech.service.UserBaseinfoService#updateUserBaseInfo(com.fintech.model.vo.UserBaseinfoVo) 
	*/
	@Override
	public void updateUserBaseInfo(UserBaseinfoVo vo) {
		UserBaseinfo userBaseinfo=new UserBaseinfo();
		BeanUtils.copyProperties(vo, userBaseinfo);
		if(!StringUtil.isEmpty(vo.getUserLoginPassword())) {
			userBaseinfo.setUserLoginPassword(MD5Util.md5(userBaseinfo.getUserLoginPassword()));
		}
		userBaseinfoMapper.updateByPrimaryKeySelective(userBaseinfo);
	}

}
