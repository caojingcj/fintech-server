package com.fintech.service;

import java.util.List;
import java.util.Map;

import com.fintech.model.MasterArea;

public interface MasterAreaService {
    List<MasterArea> queryMasterAreaByKey(Map<String, Object>params);
}
