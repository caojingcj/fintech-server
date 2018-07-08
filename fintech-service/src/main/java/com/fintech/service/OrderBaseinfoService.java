package com.fintech.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fintech.model.CompanyBaseinfo;
import com.fintech.model.CustBaseinfo;
import com.fintech.model.OrderBaseinfo;
import com.fintech.model.vo.CompanyBaseinfoVo;
import com.fintech.model.vo.CustBaseinfoVo;
import com.fintech.model.vo.OrderAttachmentVo;
import com.fintech.model.vo.OrderBaseinfoVo;
import com.fintech.model.vo.OrderDetailinfoVo;
import com.fintech.model.vo.ProjectVo;
import com.fintech.xcpt.FintechException;
import com.github.pagehelper.PageInfo;

@Transactional(rollbackFor = Exception.class)
public interface OrderBaseinfoService {

    void insertSelective(OrderBaseinfo record) throws FintechException;

    void updateByPrimaryKeySelective(OrderBaseinfo record);

    Map<String, Object> scanPiece(String companyId, String mobile) throws Exception;

    void saveProject(ProjectVo projectVo) throws Exception;

    void saveDetailinfo(OrderDetailinfoVo orderDetailinfo);

    Map<String, Object> saveOrderAttachment(String serverId,String token,String attchType,String orderId);

    String remoteSignCaOrder(OrderBaseinfoVo vo) throws Exception;

    Map<String, String> previewCaOrder(String orderId) throws Exception;

    List<OrderBaseinfoVo> orderBaseinfos(String token) throws Exception;

    Map<String, Object> orderBaseinfoDetail(String orderId) throws Exception;

    Map<String, Object> userReturnplans(String token) throws Exception;

    @Transactional(noRollbackFor = { RuntimeException.class, Exception.class })
    CustBaseinfo saveIdentityPositive(String serverId, String token, String orderId) throws Exception;

    @Transactional(noRollbackFor = { RuntimeException.class, Exception.class })
    CustBaseinfo saveIdentitySide(String serverId, String token, String orderId) throws Exception;

    @Transactional(noRollbackFor = { RuntimeException.class, Exception.class })
    void testSaveOrder() throws Exception;
    
    PageInfo<OrderBaseinfoVo> selectByPrimaryKeyList(OrderBaseinfoVo orderBaseinfoVo) throws Exception;
    
    Map<String, Object>selectOrderDetails(String orderId);
    
    void cancelOrder(OrderBaseinfoVo vo);
}
