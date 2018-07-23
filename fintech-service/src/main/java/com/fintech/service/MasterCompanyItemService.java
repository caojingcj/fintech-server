package com.fintech.service;

import java.util.List;

import com.fintech.model.MasterCompanyItem;

public interface MasterCompanyItemService {
	
    List<MasterCompanyItem> selectByPrimaryKeyList();
}
