package com.fintech.service;

import java.util.List;

import com.fintech.model.MasterBank;

public interface MasterBankService {
	
    List<MasterBank> selectByPrimaryKeyList();
}
