package com.fintech.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
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
import com.fintech.dao.LogMoxieinfoMapper;
import com.fintech.dao.LogMozhanginfoMapper;
import com.fintech.dao.LogOcridcardMapper;
import com.fintech.dao.LogOrderMapper;
import com.fintech.dao.OrderAttachmentMapper;
import com.fintech.dao.OrderBaseinfoMapper;
import com.fintech.dao.OrderDetailinfoMapper;
import com.fintech.dao.UserContractMapper;
import com.fintech.dao.UserReturnplanMapper;
import com.fintech.dao.procedure.ContractProcedureMapper;
import com.fintech.dao.procedure.OrderProcedureMapper;
import com.fintech.enm.CreditVettingResultEnum;
import com.fintech.model.CompanyAccountinfo;
import com.fintech.model.CompanyBaseinfo;
import com.fintech.model.CompanyChannel;
import com.fintech.model.CompanyPeriodFee;
import com.fintech.model.CustBaseinfo;
import com.fintech.model.LogMoxieinfo;
import com.fintech.model.LogMozhanginfo;
import com.fintech.model.LogOcridcard;
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
import com.fintech.model.vo.faceid.FaceidIDCardPositiveVo;
import com.fintech.model.vo.faceid.FaceidIDCardSideVo;
import com.fintech.model.vo.faceid.Legality;
import com.fintech.service.CreditVettingService;
import com.fintech.service.LogOrderService;
import com.fintech.service.OrderBaseinfoService;
import com.fintech.service.RedisService;
import com.fintech.service.ReturnPlanService;
import com.fintech.service.CreditVettingService;
import com.fintech.util.BeanUtils;
import com.fintech.util.CommonUtil;
import com.fintech.util.DateUtils;
import com.fintech.util.FinTechException;
import com.fintech.util.HttpClient;
import com.fintech.util.StringUtil;
import com.fintech.util.enumerator.ConstantInterface;
import com.fintech.xcpt.FintechException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.sf.json.JSONObject;

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
    @Autowired
    private LogOcridcardMapper logOcridcardMapper;
    @Autowired
    private LogMoxieinfoMapper logMoxieinfoMapper;
    @Autowired
    private LogMozhanginfoMapper logMozhanginfoMapper;
    @Autowired
    private CreditVettingService creditVettingService;
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

    @Override
    public Map<String, Object> scanPiece(String companyId,String mobile) throws Exception {
        CompanyBaseinfo baseinfo = companyBaseinfoMapper.selectByPrimaryKeyInfo(companyId);
        if (baseinfo.getCompanyStatus().equals(ConstantInterface.Enum.ConstantNumber.ZERO.getKey().toString())) {
            throw new FinTechException(ConstantInterface.AppValidateConfig.CompanyValidate.COMPANY_200101.toString());
        }
        Map<String, Object> parms = new HashMap<>();
        parms.put("companyId", companyId);
        List<CompanyChannel> channels = companyChannelMapper.selectByPrimaryKeyList(parms);// 咨询师
        List<CompanyPeriodFee> periodFees = companyPeriodFeeMapper.selectByPrimaryKeyList(parms);// 费率
        List<CompanyItemDo> items = companyItemMapper.selectByPrimaryKeyList(parms);// 项目
        Map<String, Object>mapOrder=new HashMap<>();
        Map<String, Object> reslutMap = new HashMap<>();
        mapOrder.put("custCellphone", mobile);
        mapOrder.put("orderStatus", ConstantInterface.Enum.OrderStatus.ORDER_STATUS00.getKey());
        OrderBaseinfo orderBaseinfo=new OrderBaseinfo();
        OrderBaseinfo info=orderBaseinfoMapper.selectByPrimaryKeySelective(mapOrder);
        if(info==null) {
            orderBaseinfo.setCompanyId(companyId);
            orderBaseinfo.setCustCellphone(mobile);
            orderBaseinfo.setOrderStatus(ConstantInterface.Enum.OrderStatus.ORDER_STATUS00.getKey());
            orderBaseinfo.setCompanyName(baseinfo.getCompanyName());
            orderBaseinfo.setOrderId(orderProcedureMapper.generateOrderId());
            OrderDetailinfo detailinfo=new OrderDetailinfo();
            detailinfo.setOrderId(orderBaseinfo.getOrderId());
            orderBaseinfoMapper.insertSelective(orderBaseinfo);
            orderDetailinfoMapper.insertSelective(detailinfo);
            logOrderMapper.insertSelective(new LogOrder(orderBaseinfo.getOrderId(),ConstantInterface.Enum.OrderLogStatus.ORDER_STATUS00.getKey(), ConstantInterface.Enum.OrderStatus.ORDER_STATUS00.getKey(), null));
        }
        reslutMap.put("channels", channels);
        reslutMap.put("periodFees", periodFees);
        reslutMap.put("items", items);
        reslutMap.put("baseinfo", baseinfo);
        reslutMap.put("order", info==null?orderBaseinfo:info);
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
            throw new FinTechException(ConstantInterface.AppValidateConfig.OrderValidate.ORDER_200001.toString());
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
    public void saveDetailinfo(OrderDetailinfoVo orderDetailinfo) {
        OrderDetailinfo detailinfo=new OrderDetailinfo();
        BeanUtils.copyProperties(orderDetailinfo, detailinfo);
        orderDetailinfoMapper.updateByPrimaryKeySelective(detailinfo);
        logOrderMapper.insertSelective(new LogOrder(orderDetailinfo.getOrderId(),ConstantInterface.Enum.OrderLogStatus.ORDER_STATUS04.getKey(), ConstantInterface.Enum.OrderStatus.ORDER_STATUS00.getKey(), null));
    }
    
    

    /* (非 Javadoc) 
    * <p>Title: saveOrderAttachment</p> 
    * <p>Description: </p> 
    * @param vo
    * @param multipartHttpServletRequest
    * @return 
    * @see com.fintech.service.OrderBaseinfoService#saveOrderAttachment(com.fintech.model.vo.OrderAttachmentVo, org.springframework.web.multipart.MultipartHttpServletRequest) 
    * 订单附件上传
    */
    @Override
    public Map<String, Object> saveOrderAttachment(String serverId,String token,String attchType,String orderId) {
        Map<String, Object> resultMap=new HashMap<>();
        try {
            String accessToken=redisService.get("WEIXIN_ACCESS_TOKEN");
            String requestUrl=appConfig.getWEIXIN_API_MEDIA_URL().replace("{accessToken}", accessToken).replace("{mediaId}", serverId);
            HttpURLConnection conn =HttpClient.netWorkImage(requestUrl);
            InputStream input = conn.getInputStream();
            String fileName,path = "";
            OSSEntity oss=null;
            String folder = CommonUtil.getStringTime(new Date(), "yyyyMMdd") + "/";
            FileUploadSample sample = new FileUploadSample();
            fileName = orderId+"-"+attchType+ "-" + UUID.randomUUID() + ".jpg";
            path = appConfig.getOSS_ORDER_ATTACMENT_PATH() + folder + fileName;
            oss = new OSSEntity();
            oss=sample.uploadFile(input, path);
            OrderAttachment orderAttachment= orderAttachmentMapper.selectByPrimaryKey(orderId);
            OrderAttachment attachment=new OrderAttachment();
            attachment.setOrderId(orderId);
            attachment.setAtthType(attchType);
            attachment.setAtthPath(oss.getUrl());
            if(orderAttachment==null) {
                orderAttachmentMapper.insertSelective(attachment);
            }else {
                orderAttachmentMapper.updateByPrimaryKeySelective(attachment);
            }
            OrderBaseinfo baseinfo=new OrderBaseinfo();
            baseinfo.setOrderId(orderId);
            baseinfo.setOrderStatus(ConstantInterface.Enum.OrderStatus.ORDER_STATUS01.getKey());
            orderBaseinfoMapper.updateByPrimaryKeySelective(baseinfo);
            logOrderMapper.insertSelective(new LogOrder(orderId,ConstantInterface.Enum.OrderLogStatus.ORDER_STATUS05.getKey(), ConstantInterface.Enum.OrderStatus.ORDER_STATUS01.getKey(), null));
            Map<String, Object>parms=new HashMap<>();
            parms.put("orderId", orderId);
            LogMozhanginfo logMozhanginfo= logMozhanginfoMapper.selectByPrimaryKeySelective(parms);
            if(logMozhanginfo!=null) {
                //調用信审服务
                creditVettingService.creditVetting(orderId);
            }
            OrderBaseinfo orderBaseinfo=orderBaseinfoMapper.selectByPrimaryKey(orderId);
            resultMap.put("orderStatus", orderBaseinfo.getOrderStatus());
            resultMap.put("url", oss.getUrl());
            return resultMap;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
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
            throw new FinTechException(ConstantInterface.AppValidateConfig.OrderValidate.ORDER_200002.toString());
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
    public Map<String, String> previewCaOrder(String orderId) {
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
    public Map<String, Object> orderBaseinfoDetail(String orderId){
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
    * faceId身份证正面扫
    */
    @Override
    public CustBaseinfo saveIdentityPositive(String serverId,String token,String orderId) throws Exception {
        Date date=new Date();
        Gson gson=new Gson();
        String accessToken=redisService.get("WEIXIN_ACCESS_TOKEN");
        String requestUrl=appConfig.getWEIXIN_API_MEDIA_URL().replace("{accessToken}", accessToken).replace("{mediaId}", serverId);
        HttpURLConnection conn =HttpClient.netWorkImage(requestUrl);
        InputStream input = conn.getInputStream();
        byte[] buffer = IOUtils.toByteArray(input);
        InputStream faceStream = new ByteArrayInputStream(buffer);
        InputStream ossStream = new ByteArrayInputStream(buffer);
        Map<String, Object>parms=new HashMap<>();
        parms.put("api_key", appConfig.getOCR_API_KEY());
        parms.put("api_secret", appConfig.getOCR_API_SECRET());
        parms.put("legality", ConstantInterface.Enum.ConstantNumber.ONE.getKey());
        parms.put("multi_oriented_detection", ConstantInterface.Enum.ConstantNumber.ONE.getKey());
        JSONObject result=HttpClient.postFileInputStream(appConfig.getOCR_API_URL(), parms,faceStream,"");
        FaceidIDCardPositiveVo faceidVo=gson.fromJson(result.toString(),new TypeToken<FaceidIDCardPositiveVo>() {}.getType());
        String fileName,path = "";
        OSSEntity oss=null;
        String folder = CommonUtil.getStringTime(date, "yyyyMMdd") + "/";
        FileUploadSample sample = new FileUploadSample();
        String mobile=redisService.get(token);
        fileName =mobile+"-" +UUID.randomUUID()+".jpg";
        path = appConfig.getOSS_ORDER_OCR_PATH() + folder + fileName;
        oss = new OSSEntity();
        oss=sample.uploadFile(ossStream, path);
        CustBaseinfo custBaseinfo=new CustBaseinfo();
        custBaseinfo.setCustRealname(faceidVo.getName());
        custBaseinfo.setCustIdCardNo(faceidVo.getId_card_number());
        custBaseinfo.setCustCellphone(mobile);
        custBaseinfo.setIdentityStatus(String.valueOf(1));
        custBaseinfo.setCustNation(faceidVo.getRace());
        custBaseinfo.setCustAddress(faceidVo.getAddress());
        custBaseinfo.setCustIdCardFront(oss.getUrl());
        custBaseinfo.setIdentityTime(date);
        custBaseinfo.setIsEnabled(true);
        custBaseinfo.setCustDeviceCode("wx");
        LogOcridcard ocridcard=new LogOcridcard();
        ocridcard.setOcrIdCard(custBaseinfo.getCustIdCardNo());
        ocridcard.setOcrLegality(gson.toJson(faceidVo.getLegality()));
        ocridcard.setOcrContent(result.toString());
        ocridcard.setOcrMobile(custBaseinfo.getCustCellphone());
        ocridcard.setOcrName(custBaseinfo.getCustRealname());
        ocridcard.setOcrRequestId(faceidVo.getRequest_id());
        ocridcard.setOcrSide(faceidVo.getSide());
        ocridcard.setOrderId(orderId);
        OrderBaseinfo orderBaseinfo=orderBaseinfoMapper.selectByPrimaryKey(orderId);
        orderBaseinfo.setCustRealname(faceidVo.getName());
        orderBaseinfo.setCustCellphone(custBaseinfo.getCustCellphone());
        orderBaseinfo.setCustIdCardNo(faceidVo.getId_card_number());
        logOcridcardMapper.insertSelective(ocridcard);
        custBaseinfoMapper.updateByPrimaryKeySelective(custBaseinfo);
        orderBaseinfoMapper.updateByPrimaryKeySelective(orderBaseinfo);
        LegalityVerification(faceidVo.getLegality());
        return custBaseinfo;
    }
    @Override
    public CustBaseinfo saveIdentitySide(String serverId,String token,String orderId) throws Exception {
        Date date=new Date();
        Gson gson=new Gson();
        String accessToken=redisService.get("WEIXIN_ACCESS_TOKEN");
        String requestUrl=appConfig.getWEIXIN_API_MEDIA_URL().replace("{accessToken}", accessToken).replace("{mediaId}", serverId);
        HttpURLConnection conn =HttpClient.netWorkImage(requestUrl);
        InputStream input = conn.getInputStream();
        byte[] buffer = IOUtils.toByteArray(input);
        InputStream faceStream = new ByteArrayInputStream(buffer);
        InputStream ossStream = new ByteArrayInputStream(buffer);
        Map<String, Object>parms=new HashMap<>();
        parms.put("api_key", appConfig.getOCR_API_KEY());
        parms.put("api_secret", appConfig.getOCR_API_SECRET());
        parms.put("legality", ConstantInterface.Enum.ConstantNumber.ONE.getKey());
        parms.put("multi_oriented_detection", ConstantInterface.Enum.ConstantNumber.ONE.getKey());
        JSONObject result=HttpClient.postFileInputStream(appConfig.getOCR_API_URL(), parms,faceStream,"");
        FaceidIDCardSideVo faceidVo = gson.fromJson(result.toString(),new TypeToken<FaceidIDCardSideVo>() {}.getType());
        String fileName,path = "";
        OSSEntity oss=null;
        String folder = CommonUtil.getStringTime(date, "yyyyMMdd") + "/";
        FileUploadSample sample = new FileUploadSample();
        String mobile=redisService.get(token);
        fileName = mobile+ "-" + UUID.randomUUID() + ".jpg";
        path = appConfig.getOSS_ORDER_OCR_PATH() + folder + fileName;
        oss = new OSSEntity();
        oss=sample.uploadFile(ossStream, path);
        CustBaseinfo custBaseinfo=new CustBaseinfo();
        String [] idTime=faceidVo.getValid_date().split("-");
        custBaseinfo.setCustCellphone(mobile);
        custBaseinfo.setCustIdCardValBegin(DateUtils.parse(idTime[0].replace(".", "-")));
        custBaseinfo.setCustIdCardValEnd(DateUtils.parse(idTime[1].replace(".", "-")));
        custBaseinfo.setCustIdCardBack(oss.getUrl());
        custBaseinfo.setIdentityTime(date);
        OrderBaseinfo orderBaseinfo=orderBaseinfoMapper.selectByPrimaryKey(orderId);
        LogOcridcard ocridcard=new LogOcridcard();
        ocridcard.setOcrIdCard(orderBaseinfo.getCustIdCardNo());
        ocridcard.setOcrLegality(gson.toJson(faceidVo.getLegality()));
        ocridcard.setOcrContent(result.toString());
        ocridcard.setOcrMobile(orderBaseinfo.getCustCellphone());
        ocridcard.setOcrName(orderBaseinfo.getCustRealname());
        ocridcard.setOcrRequestId(faceidVo.getRequest_id());
        ocridcard.setOcrSide(faceidVo.getSide());
        ocridcard.setOrderId(orderBaseinfo.getOrderId());
        logOcridcardMapper.insertSelective(ocridcard);
        custBaseinfoMapper.updateByPrimaryKeySelective(custBaseinfo);
        orderBaseinfoMapper.updateByPrimaryKeySelective(orderBaseinfo);
        LegalityVerification(faceidVo.getLegality());
        logOrderMapper.insertSelective(new LogOrder(orderId, ConstantInterface.Enum.OrderLogStatus.ORDER_STATUS02.getKey(), ConstantInterface.Enum.OrderStatus.ORDER_STATUS00.getKey(), null));
        return custBaseinfo;
    }
    
    /** 
    * @Title: OrderBaseinfoImpl.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年6月30日 下午1:36:29  
    * @param @param legality
    * @param @return    设定文件 
    * @Description: TODO[ ocr 身份证校验结果 ]
    * @throws 
    */
    public void LegalityVerification(Legality legality) {
        if(legality.getPhotocopy().equals(ConstantInterface.Enum.ConstantNumber.ONE.getKey().toString())) {
            throw new FinTechException(ConstantInterface.AppValidateConfig.OrderValidate.ORDER_200007.toString());
        }
        if(legality.getScreen().equals(ConstantInterface.Enum.ConstantNumber.ONE.getKey().toString())) {
            throw new FinTechException(ConstantInterface.AppValidateConfig.OrderValidate.ORDER_200008.toString());
        }
        if(legality.getEdited().equals(ConstantInterface.Enum.ConstantNumber.ONE.getKey().toString())) {
            throw new FinTechException(ConstantInterface.AppValidateConfig.OrderValidate.ORDER_200009.toString());
        }
    }

    @Override
    public void testSaveOrder() throws FinTechException {
            logOrderMapper.insertSelective(new LogOrder("123", "2", "11", "22"));
            logOrderMapper.insertSelective(new LogOrder("123", "2", "11", "22"));
            logOrderMapper.insertSelective(new LogOrder("123", "2", "11", "22"));
            throw new FinTechException("asdasd");
    }

}
