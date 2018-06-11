package com.fintech.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.dao.CompanyChannelMapper;
import com.fintech.model.CompanyChannel;
import com.fintech.service.CompanyChannelService;

@Service
public class CompanyChannelImpl implements CompanyChannelService {

    @Autowired
    private CompanyChannelMapper companyChannelMapper;
	@Override
	public List<CompanyChannel> selectByPrimaryKeyList(Map<String, Object> parms) {
		return companyChannelMapper.selectByPrimaryKeyList(parms);
	}

}
