package com.fintech.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.dao.MasterBankMapper;
import com.fintech.model.MasterBank;
import com.fintech.service.MasterBankService;
@Service
public class MasterBankImpl implements MasterBankService {
    @Autowired
    private MasterBankMapper masterBankMapper;

	@Override
	public List<MasterBank> selectByPrimaryKeyList() {
		return masterBankMapper.selectByPrimaryKeyList();
	}
}
