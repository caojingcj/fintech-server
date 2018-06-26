package com.fintech.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fintech.common.QysRemoteSignHandler;
import com.fintech.common.oss.FileUploadSample;
import com.fintech.common.oss.OSSEntity;
import com.fintech.common.properties.AppConfig;
import com.fintech.dao.CompanyAccountinfoMapper;
import com.fintech.dao.CompanyBaseinfoMapper;
import com.fintech.dao.CompanyChannelMapper;
import com.fintech.dao.CompanyItemMapper;
import com.fintech.dao.CompanyPeriodFeeMapper;
import com.fintech.dao.CustBaseinfoMapper;
import com.fintech.dao.LogOrderMapper;
import com.fintech.dao.OrderAttachmentMapper;
import com.fintech.dao.OrderBaseinfoMapper;
import com.fintech.dao.OrderDetailinfoMapper;
import com.fintech.dao.UserContractMapper;
import com.fintech.dao.UserReturnplanMapper;
import com.fintech.dao.procedure.ContractProcedureMapper;
import com.fintech.dao.procedure.OrderProcedureMapper;
import com.fintech.model.CompanyAccountinfo;
import com.fintech.model.CompanyBaseinfo;
import com.fintech.model.CompanyChannel;
import com.fintech.model.CompanyPeriodFee;
import com.fintech.model.CustBaseinfo;
import com.fintech.model.LogOrder;
import com.fintech.model.OrderAttachment;
import com.fintech.model.OrderBaseinfo;
import com.fintech.model.OrderDetailinfo;
import com.fintech.model.UserContract;
import com.fintech.model.domain.CompanyItemDo;
import com.fintech.model.vo.CustBaseinfoVo;
import com.fintech.model.vo.OrderAttachmentVo;
import com.fintech.model.vo.OrderBaseinfoVo;
import com.fintech.model.vo.OrderDetailinfoVo;
import com.fintech.model.vo.ProjectVo;
import com.fintech.service.OrderBaseinfoService;
import com.fintech.service.RedisService;
import com.fintech.service.ReturnPlanService;
import com.fintech.util.BeanUtils;
import com.fintech.util.CommonUtil;
import com.fintech.util.DateUtils;
import com.fintech.util.FinTechException;
import com.fintech.util.enumerator.ConstantInterface;
import com.fintech.xcpt.FintechException;

@Service
public class OrderBaseinfoImpl implements OrderBaseinfoService {

    @Autowired
    private OrderBaseinfoMapper orderBaseinfoMapper;
    @Autowired
    private OrderDetailinfoMapper orderDetailinfoMapper;
    @Autowired
    private CompanyBaseinfoMapper companyBaseinfoMapper;
    @Autowired
    private LogOrderMapper logOrderMapper;
    @Autowired
    private OrderProcedureMapper orderProcedureMapper;
    @Autowired
    private CompanyChannelMapper companyChannelMapper;
    @Autowired
    private CompanyPeriodFeeMapper companyPeriodFeeMapper;
    @Autowired
    private CompanyItemMapper companyItemMapper;
    @Autowired
    private CustBaseinfoMapper custBaseinfoMapper;
    @Autowired
    private OrderAttachmentMapper orderAttachmentMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private AppConfig appConfig;
    @Autowired
    private QysRemoteSignHandler qysRemoteSignHandler;
    @Autowired
    private UserContractMapper userContractMapper;
    @Autowired
    private ContractProcedureMapper contractProcedureMapper;
    @Autowired
    private ReturnPlanService returnPlanService;
    @Autowired
    private UserReturnplanMapper userReturnplanMapper;
    @Autowired
    private CompanyAccountinfoMapper companyAccountinfoMapper;
    /*
     * (非 Javadoc) <p>Title: insertSelective</p> <p>Description: </p>
     * 
     * @param record
     * 
     * @throws FintechException
     * 
     * @see com.fintech.service.OrderBaseinfoService#insertSelective(com.fintech.model.OrderBaseinfo)
     */
    @Override
    public void insertSelective(OrderBaseinfo record) throws FintechException {
//        // 总在还款额不超过50w，分期还款中的笔数不超过3笔；
//        Map<String, Object> amount = orderBaseinfoMapper.selectByOrderAmountJudge(record.getCustCellphone());
//        if (Integer.parseInt(amount.get("amount").toString()) > 1
//                || Integer.parseInt(amount.get("statusCount").toString()) > 3) {
//            throw new FinTechException(ConstantInterface.AppValidateConfig.OrderValidate.ORDER_200001.getKey(),
//                    ConstantInterface.AppValidateConfig.OrderValidate.ORDER_200001.getValue());
//        }
//        orderBaseinfoMapper.insertSelective(record);
//        // OrderDetailinfo orderDetailinfo=new OrderDetailinfo();
//        // orderDetailinfoMapper.insertSelective(orderDetailinfo);
        record.setOrderId(orderProcedureMapper.generateOrderId());
        orderBaseinfoMapper.insertSelective(record);
        logOrderMapper.insertSelective(new LogOrder(record.getOrderId(),ConstantInterface.Enum.ConstantNumber.ONE.getKey().toString(), "00", null));
        
    }

    /*
     * (非 Javadoc) <p>Title: updateByPrimaryKeySelective</p> <p>Description: </p>
     * 
     * @param record
     * 
     * @see com.fintech.service.OrderBaseinfoService#updateByPrimaryKeySelective(com.fintech.model.OrderBaseinfo) 项目信息填写
     */
    @Override
    public void updateByPrimaryKeySelective(OrderBaseinfo record) {
        CompanyBaseinfo baseinfo = companyBaseinfoMapper.selectByPrimaryKeyInfo(record.getCompanyId());
        if (baseinfo.getCompanyStatus().equals(String.valueOf(ConstantInterface.Enum.ConstantNumber.ZERO.getKey()))) {
            throw new FinTechException(ConstantInterface.ValidateConfig.CompanyValidate.COMPANY_100006.getKey(),
                    ConstantInterface.ValidateConfig.CompanyValidate.COMPANY_100006.getValue());
        }
        orderBaseinfoMapper.updateByPrimaryKeySelective(record);
        logOrderMapper.insertSelective(new LogOrder(record.getOrderId(),
                ConstantInterface.Enum.ConstantNumber.ONE.getKey().toString(), ConstantInterface.Enum.OrderStatus.ORDER_STATUS00.getKey(), null));
    }

    public static void main(String[] args) {
        String ss = "1";
        System.out.println(ss.equals(String.valueOf(ConstantInterface.Enum.ConstantNumber.ZERO.getKey())));
    }

    @Override
    public Map<String, Object> scanPiece(String companyId,String mobile) throws Exception {
        CompanyBaseinfo baseinfo = companyBaseinfoMapper.selectByPrimaryKeyInfo(companyId);
        if (baseinfo.getCompanyStatus().equals(ConstantInterface.Enum.ConstantNumber.ZERO.getKey().toString())) {
            throw new Exception(ConstantInterface.AppValidateConfig.CompanyValidate.COMPANY_200101.toString());
        }
        Map<String, Object> parms = new HashMap<>();
        parms.put("companyId", companyId);
        List<CompanyChannel> channels = companyChannelMapper.selectByPrimaryKeyList(parms);// 咨询师
        List<CompanyPeriodFee> periodFees = companyPeriodFeeMapper.selectByPrimaryKeyList(parms);// 费率
        List<CompanyItemDo> items = companyItemMapper.selectByPrimaryKeyList(parms);// 项目
        OrderBaseinfo orderBaseinfo=new OrderBaseinfo();
        orderBaseinfo.setCompanyId(companyId);
        orderBaseinfo.setCustCellphone(mobile);
        orderBaseinfo.setOrderStatus(ConstantInterface.Enum.OrderStatus.ORDER_STATUS00.getKey());
        orderBaseinfo.setCompanyName(baseinfo.getCompanyName());
        orderBaseinfo.setOrderId(orderProcedureMapper.generateOrderId());
        OrderDetailinfo detailinfo=new OrderDetailinfo();
        detailinfo.setOrderId(orderBaseinfo.getOrderId());
        orderBaseinfoMapper.insertSelective(orderBaseinfo);
        orderDetailinfoMapper.insertSelective(detailinfo);
        Map<String, Object> reslutMap = new HashMap<>();
        reslutMap.put("channels", channels);
        reslutMap.put("periodFees", periodFees);
        reslutMap.put("items", items);
        reslutMap.put("baseinfo", baseinfo);
        reslutMap.put("order", orderBaseinfo);
        return reslutMap;
    }
    
    /* (非 Javadoc) 
    * <p>Title: saveProject</p> 
    * <p>Description: </p> 
    * @param projectVo
    * @throws Exception 
    * @see com.fintech.service.OrderBaseinfoService#saveProject(com.fintech.model.vo.ProjectVo) 
    * 项目信息填写
    */
    @Override
    public void saveProject(ProjectVo projectVo) throws Exception {
        // 总在还款额不超过50w，分期还款中的笔数不超过3笔；
        Map<String, Object> amount = orderBaseinfoMapper.selectByOrderAmountJudge(redisService.get(projectVo.getToken()));
        if (Integer.parseInt(amount.get("amount").toString()) > 1 || Integer.parseInt(amount.get("statusCount").toString()) > 3) {
            throw new Exception(ConstantInterface.AppValidateConfig.OrderValidate.ORDER_200001.toString());
        }
        OrderBaseinfo record=new OrderBaseinfo();
        record.setItemCode(projectVo.getItemCode());
        record.setItemName(projectVo.getItemName());
        record.setOrderAmount(new BigDecimal(projectVo.getOrderAmount()));
        record.setTotalPeriod(projectVo.getTotalPeriod());
        record.setOrderId(projectVo.getOrderId());
        OrderDetailinfo detailinfo=new OrderDetailinfo();
        detailinfo.setCompanyChannelId(projectVo.getCompanyChannelId());
        detailinfo.setCompanyChannelName(projectVo.getCompanyChannelName());
        detailinfo.setCompanyChannelPhone(projectVo.getCompanyChannelPhone());
        detailinfo.setOrderId(projectVo.getOrderId());
        orderBaseinfoMapper.updateByPrimaryKeySelective(record);
        orderDetailinfoMapper.updateByPrimaryKeySelective(detailinfo);
        logOrderMapper.insertSelective(new LogOrder(projectVo.getOrderId(),ConstantInterface.Enum.OrderLogStatus.ORDER_STATUS01.getKey(), ConstantInterface.Enum.OrderStatus.ORDER_STATUS00.getKey(), null));
    }

    /* (非 Javadoc) 
    * <p>Title: saveDetailinfo</p> 
    * <p>Description: </p> 
    * @param orderDetailinfo
    * @throws Exception 
    * @see com.fintech.service.OrderBaseinfoService#saveDetailinfo(com.fintech.model.vo.OrderDetailinfoVo) 
    * 个人信息填写
    */
    @Override
    public void saveDetailinfo(OrderDetailinfoVo orderDetailinfo) throws Exception {
        OrderDetailinfo detailinfo=new OrderDetailinfo();
        BeanUtils.copyProperties(orderDetailinfo, detailinfo);
        orderDetailinfoMapper.updateByPrimaryKeySelective(detailinfo);
        logOrderMapper.insertSelective(new LogOrder(orderDetailinfo.getOrderId(),ConstantInterface.Enum.OrderLogStatus.ORDER_STATUS04.getKey(), ConstantInterface.Enum.OrderStatus.ORDER_STATUS00.getKey(), null));
    }

    @Override
    public String saveOrderAttachment(OrderAttachmentVo vo, MultipartHttpServletRequest multipartHttpServletRequest) {
        try {
            MultipartFile multipartFile=multipartHttpServletRequest.getFile("file");
            InputStream is= multipartFile.getInputStream();
            if(is.available()==0){
                throw new Exception(ConstantInterface.AppValidateConfig.OrderValidate.ORDER_200002.toString());
            }
            File convFile=null;
            String fileName,path = "";
            OSSEntity oss=null;
            String folder = CommonUtil.getStringTime(new Date(), "yyyyMMdd") + "/";
            FileUploadSample sample = new FileUploadSample();
            convFile = new File(multipartFile.getOriginalFilename());
            multipartFile.transferTo(convFile);
            String suffix = convFile.getName().substring(convFile.getName().lastIndexOf("."));
            fileName = vo.getOrderId()+"-"+vo.getAtthType() + "-" + UUID.randomUUID() + suffix;
            path = appConfig.getOSS_ORDER_ATTACMENT_PATH() + folder + fileName;
            oss = new OSSEntity();
            oss=sample.uploadFile(multipartFile.getInputStream(), path);
            OrderAttachment attachment=new OrderAttachment();
            BeanUtils.copyProperties(vo, attachment);
            attachment.setAtthPath(oss.getUrl());
            orderAttachmentMapper.insertSelective(attachment);
            logOrderMapper.insertSelective(new LogOrder(vo.getOrderId(),ConstantInterface.Enum.OrderLogStatus.ORDER_STATUS05.getKey(), ConstantInterface.Enum.OrderStatus.ORDER_STATUS01.getKey(), null));
            return oss.getUrl();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    /* (非 Javadoc) 
    * <p>Title: remoteSignCaOrder</p> 
    * <p>Description: </p> 
    * @param vo
    * @return
    * @throws IOException 
    * @see com.fintech.service.OrderBaseinfoService#remoteSignCaOrder(com.fintech.model.vo.OrderBaseinfoVo) 
    * 用户签署
    */
    @Override
    public String remoteSignCaOrder(OrderBaseinfoVo vo) throws Exception {
        OrderBaseinfo orderBaseinfo=orderBaseinfoMapper.selectByPrimaryKey(vo.getOrderId());
        if(orderBaseinfo.getOrderStatus().equals(ConstantInterface.Enum.OrderStatus.ORDER_STATUS05.getKey())) {
            throw new Exception(ConstantInterface.AppValidateConfig.OrderValidate.ORDER_200002.toString());
        }
        OSSEntity oss=null;
        Map<String, String>qysParams=resultCaMap(orderBaseinfo);
        orderBaseinfo.setContractId(qysParams.get("CONTRACT_ID"));
        orderBaseinfo.setOrderStatus(ConstantInterface.Enum.OrderStatus.ORDER_STATUS05.getKey());
        oss = qysRemoteSignHandler.signOrderCA(orderBaseinfo.getContractId(), qysParams);
        orderBaseinfoMapper.updateByPrimaryKeySelective(orderBaseinfo);
        returnPlanService.generateReturnPlan(vo.getOrderId());//生成还款计划
        logOrderMapper.insertSelective(new LogOrder(vo.getOrderId(),ConstantInterface.Enum.OrderLogStatus.ORDER_STATUS06.getKey(), ConstantInterface.Enum.OrderStatus.ORDER_STATUS05.getKey(), null));
        return oss.getUrl();
    }
    
    /* (非 Javadoc) 
    * <p>Title: previewCaOrder</p> 
    * <p>Description: </p> 
    * @param orderId
    * @return
    * @throws Exception 
    * @see com.fintech.service.OrderBaseinfoService#previewCaOrder(java.lang.String) 
    * 用户签署协议预览
    */
    @Override
    public Map<String, String> previewCaOrder(String orderId) throws Exception {
        OrderBaseinfo orderBaseinfo=orderBaseinfoMapper.selectByPrimaryKey(orderId);
        return  resultCaMap(orderBaseinfo);
    }

    /** 
    * @Title: OrderBaseinfoImpl.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年6月26日 下午11:54:47  
    * @param @param vo
    * @param @return    设定文件 
    * @Description: TODO[ 签署 封装返回 map ]
    * @throws 
    */
    public Map<String, String> resultCaMap(OrderBaseinfo vo){
        UserContract contract=userContractMapper.selectByOrderId(vo.getOrderId());
        UserContract userContract=new UserContract();
        if(contract==null) {
            String contractId=contractProcedureMapper.generateContractId();
            userContract.setOrderId(vo.getOrderId());
            userContract.setCustIdCardNo(vo.getCustIdCardNo());
            userContract.setCustCellphone(vo.getCustCellphone());
            userContract.setCustRealname(vo.getCustRealname());
            userContract.setContractId(contractId);
            userContractMapper.insertSelective(userContract);
        }
        CompanyBaseinfo companyBaseinfo=companyBaseinfoMapper.selectByPrimaryKeyInfo(vo.getCompanyId());
        CompanyAccountinfo accountinfo= companyAccountinfoMapper.selectByPrimaryKey(vo.getCompanyId());
        Map<String, String> qysParams = new HashMap<>();
        qysParams.put("PartyA_Name", ConstantInterface.Enum.PartyAInfo.PartyA_Name.getValue());
        qysParams.put("PartyA_CORPORATE_NAME",ConstantInterface.Enum.PartyAInfo.PartyA_CORPORATE_NAME.getValue());
        qysParams.put("PartyA_COMPANY_ADDR", ConstantInterface.Enum.PartyAInfo.PartyA_COMPANY_ADDR.getValue());
        qysParams.put("PartyA_COMPANY_ACCOUNT_NAME", ConstantInterface.Enum.PartyAInfo.PartyA_COMPANY_ACCOUNT_NAME.getValue());
        qysParams.put("PartyA_COMPANY_ACCOUNT_BRANCH", ConstantInterface.Enum.PartyAInfo.PartyA_COMPANY_ACCOUNT_BRANCH.getValue());
        qysParams.put("PartyA_COMPANY_ACCOUNT_NO", ConstantInterface.Enum.PartyAInfo.PartyA_COMPANY_ACCOUNT_NO.getValue());
        qysParams.put("CONTRACT_ID", contract!=null?contract.getContractId():userContract.getContractId());
        qysParams.put("SIGNING_TIME", DateUtils.getDateStr("yyyy年MM月dd日"));
        qysParams.put("COMPANY_NAME", companyBaseinfo.getCompanyName());
        qysParams.put("CORPORATE_NAME", companyBaseinfo.getCorporateName());
        qysParams.put("COMPANY_ADDR", companyBaseinfo.getCompanyAddr());
        qysParams.put("COMPANY_ACCOUNT_NAME", accountinfo.getCompanyAccountName());
        qysParams.put("COMPANY_ACCOUNT_BRANCH", accountinfo.getCompanyAccountBranch());
        qysParams.put("COMPANY_ACCOUNT_NO", accountinfo.getCompanyAccountNo());
        qysParams.put("ITEM_NAME", vo.getItemName());
        qysParams.put("ORDER_AMOUNT",String.valueOf(vo.getOrderAmount()));
        qysParams.put("CUST_REALNAME", vo.getCustRealname());
        return qysParams;
    }
    /* (非 Javadoc) 
    * <p>Title: orderBaseinfos</p> 
    * <p>Description: </p> 
    * @param token
    * @return
    * @throws Exception 
    * @see com.fintech.service.OrderBaseinfoService#orderBaseinfos(java.lang.String) 
    * 查询订单列表
    */
    @Override
    public List<OrderBaseinfo> orderBaseinfos(String token) throws Exception{
        Map<String, Object>parms=new HashMap<>();
        parms.put("custCellphone", redisService.get(token));
        return orderBaseinfoMapper.selectByPrimaryKeyList(parms);
    }
    /* (非 Javadoc) 
    * <p>Title: orderBaseinfoDetail</p> 
    * <p>Description: </p> 
    * @param orderId
    * @return
    * @throws Exception 
    * @see com.fintech.service.OrderBaseinfoService#orderBaseinfoDetail(java.lang.String) 
    * 订单详情
    */
    @Override
    public Map<String, Object> orderBaseinfoDetail(String orderId) throws Exception{
        Map<String, Object>parms=new HashMap<>();
        parms.put("orderId", orderId);
        Map<String, Object>map=new HashMap<>();
        map.put("orderBaseInfo", orderBaseinfoMapper.selectByPrimaryKey(orderId));
        map.put("userReturnplan", userReturnplanMapper.selectByPrimaryKeyList(parms));
        return map;
    }
    
    /* (非 Javadoc) 
    * <p>Title: orderBaseinfoDetail</p> 
    * <p>Description: </p> 
    * @param orderId
    * @return
    * @throws Exception 
    * @see com.fintech.service.OrderBaseinfoService#orderBaseinfoDetail(java.lang.String) 
    */
    @Override
    public Map<String, Object> userReturnplans(String token) throws Exception{
        Map<String, Object>parms=new HashMap<>();
        Map<String, Object>map=new HashMap<>();
        parms.put("custCellphone", redisService.get(token));
        map.put("userReturnplan", userReturnplanMapper.selectByPrimaryKeyList(parms));
        return map;
    }

    /* (非 Javadoc) 
    * <p>Title: saveIdentity</p> 
    * <p>Description: </p> 
    * @param custBaseinfoVo
    * @throws Exception 
    * @see com.fintech.service.OrderBaseinfoService#saveIdentity(com.fintech.model.vo.CustBaseinfoVo) 
    * 身份认证 oci还未接入
    */
    @Override
    public void saveIdentity(CustBaseinfoVo custBaseinfoVo) throws Exception {
        String mobile=redisService.get(custBaseinfoVo.getToken());
        CustBaseinfo custBaseinfo=new CustBaseinfo();
        if(custBaseinfoMapper.selectByPrimaryKey(mobile)!=null) {
            throw new Exception(ConstantInterface.AppValidateConfig.OrderValidate.ORDER_200003.toString());
        }
        BeanUtils.copyProperties(custBaseinfoVo, custBaseinfo);
        custBaseinfo.setCustRealname("戚尔康");
        custBaseinfo.setCustIdCardNo("320830199207180035");
        custBaseinfo.setCustCellphone(mobile);
        custBaseinfo.setIdentityStatus(String.valueOf(1));
        custBaseinfo.setCustNation("汉族");
        custBaseinfo.setCustAddress("东兰路288");
        custBaseinfo.setCustIdCardValBegin(new Date());
        custBaseinfo.setCustIdCardValEnd(new Date());
        custBaseinfo.setCustIdCardFront("oci正在对接，请等待回调");
        custBaseinfo.setCustIdCardBack("oci正在对接，请等待回调");
        custBaseinfo.setIdentityTime(new Date());
        custBaseinfo.setIsEnabled(true);
        custBaseinfo.setCustDeviceCode("wx");
        custBaseinfoMapper.insertSelective(custBaseinfo);
        OrderBaseinfo orderBaseinfo=orderBaseinfoMapper.selectByPrimaryKey(custBaseinfoVo.getOrderId());
        orderBaseinfo.setCustRealname(custBaseinfo.getCustRealname());
        orderBaseinfo.setCustCellphone(custBaseinfo.getCustCellphone());
        orderBaseinfo.setCustIdCardNo(custBaseinfo.getCustIdCardNo());
        orderBaseinfoMapper.updateByPrimaryKeySelective(orderBaseinfo);
    }
    
}
