package com.fintech.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fintech.model.OrderBaseinfo;
import com.fintech.model.vo.OrderAttachmentVo;
import com.fintech.model.vo.OrderBaseinfoVo;
import com.fintech.model.vo.OrderDetailinfoVo;
import com.fintech.model.vo.ProjectVo;
import com.fintech.xcpt.FintechException;

public interface OrderBaseinfoService {

    void insertSelective(OrderBaseinfo record) throws FintechException;

    void updateByPrimaryKeySelective(OrderBaseinfo record);

    Map<String, Object> scanPiece(String companyId, String mobile) throws Exception;

    void saveProject(ProjectVo projectVo) throws Exception;

    void saveDetailinfo(OrderDetailinfoVo orderDetailinfo) throws Exception;

    String saveOrderAttachment(OrderAttachmentVo vo, MultipartHttpServletRequest multipartHttpServletRequest);

    String remoteSignCaOrder(OrderBaseinfoVo vo) throws Exception;

    Map<String, String> previewCaOrder(String orderId) throws Exception;

    List<OrderBaseinfo> orderBaseinfos(String token) throws Exception;

    Map<String, Object> orderBaseinfoDetail(String orderId) throws Exception;

    Map<String, Object> userReturnplans(String token) throws Exception;
}
