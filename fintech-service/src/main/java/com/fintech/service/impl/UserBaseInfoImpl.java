package com.fintech.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.common.properties.AppConfig;
import com.fintech.dao.LogOrderMapper;
import com.fintech.dao.OrderBaseinfoMapper;
import com.fintech.dao.UserBaseinfoMapper;
import com.fintech.dao.UserContractMapper;
import com.fintech.model.LogOrder;
import com.fintech.model.OrderBaseinfo;
import com.fintech.model.UserBaseinfo;
import com.fintech.model.UserReturnplan;
import com.fintech.model.vo.UserBaseinfoVo;
import com.fintech.service.RedisService;
import com.fintech.service.UserBaseinfoService;
import com.fintech.service.WxApiService;
import com.fintech.util.BeanUtils;
import com.fintech.util.CommonUtil;
import com.fintech.util.DateUtils;
import com.fintech.util.FinTechException;
import com.fintech.util.HttpClient;
import com.fintech.util.HttpGetUtil;
import com.fintech.util.MD5Util;
import com.fintech.util.StringUtil;
import com.fintech.util.enumerator.ConstantInterface;
import com.fintech.util.sign.ParamSignUtils;
import com.fintech.util.sign.TokenUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;

import net.sf.json.JSONObject;

@Service
public class UserBaseInfoImpl implements UserBaseinfoService {
	private static final Logger logger = LoggerFactory.getLogger(UserBaseInfoImpl.class);

	@Autowired
	private RedisService redisService;
	@Autowired
	private UserBaseinfoMapper userBaseinfoMapper;

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

	@Override
	public PageInfo<UserBaseinfo> selectByPrimaryKeyList(UserBaseinfoVo vo) throws Exception {
		Map<String, Object> parms = CommonUtil.object2Map(vo);
        PageHelper.startPage(vo.getPageIndex(), vo.getPageSize());
        List<UserBaseinfo> list=userBaseinfoMapper.selectByPrimaryKeyList(parms);
        PageInfo<UserBaseinfo> pageLists=new PageInfo<UserBaseinfo>(list);
        return pageLists;
	}

}
