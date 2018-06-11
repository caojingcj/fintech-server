package com.fintech.service;

import java.util.List;
import java.util.Map;

import com.fintech.model.CompanyChannel;

public interface CompanyChannelService {
    List<CompanyChannel> selectByPrimaryKeyList(Map<String, Object> parms);
}
