package com.fintech.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.dao.MasterCompanyItemMapper;
import com.fintech.model.MasterCompanyItem;
import com.fintech.service.MasterCompanyItemService;
@Service
public class MasterCompanyItemImpl implements MasterCompanyItemService {
    @Autowired
    private MasterCompanyItemMapper masterCompanyItemMapper;

	@Override
	public List<MasterCompanyItem> selectByPrimaryKeyList() {
		return masterCompanyItemMapper.selectByPrimaryKeyList();
	}

}
