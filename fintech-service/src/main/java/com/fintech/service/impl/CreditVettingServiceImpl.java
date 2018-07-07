package com.fintech.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.dao.CustBaseinfoMapper;
import com.fintech.dao.LogMoxieinfoMapper;
import com.fintech.dao.LogOrderMapper;
import com.fintech.dao.OrderBaseinfoMapper;
import com.fintech.dao.OrderDetailinfoMapper;
import com.fintech.enm.CreditVettingResultEnum;
import com.fintech.enm.EducationStatusEnum;
import com.fintech.enm.GenderEnum;
import com.fintech.enm.IdentityStatusEnum;
import com.fintech.enm.OrderOperationEnum;
import com.fintech.enm.OrderStatusEnum;
import com.fintech.model.CustBaseinfo;
import com.fintech.model.LogMoxieinfo;
import com.fintech.model.LogOrder;
import com.fintech.model.OrderBaseinfo;
import com.fintech.model.OrderDetailinfo;
import com.fintech.service.CreditVettingService;
import com.fintech.util.IDCardUtil;
import com.fintech.util.JsonTools;
import com.fintech.util.JsonValidator;
import com.fintech.util.MapUtils;
import com.fintech.util.enumerator.ConstantInterface;

@Service
public class CreditVettingServiceImpl implements CreditVettingService {

    @Autowired
    private OrderBaseinfoMapper orderBaseinfoMapper;

    @Autowired
    private OrderDetailinfoMapper orderDetailinfoMapper;

    @Autowired
    private CustBaseinfoMapper custBaseinfoMapper;

    @Autowired
    private LogOrderMapper logOrderMapper;
@Autowired
private LogMoxieinfoMapper logMoxieinfoMapper;
@SuppressWarnings("unchecked")
	@Override
    public CreditVettingResultEnum creditVetting(String orderId) {
    	JsonValidator jv = new JsonValidator();
		JsonTools jsonTools=new JsonTools();
    	// 拒绝 - 年龄18岁以下
        OrderBaseinfo orderBaseInfo = orderBaseinfoMapper.selectByPrimaryKey(orderId);
        String custId = orderBaseInfo.getCustIdCardNo();
        int age = IDCardUtil.getAgeByIdCard(custId);
//        if (age < 18) {
//            logOrder(orderId, CreditVettingResultEnum.拒绝.getValue(), "拒绝 - 年龄18岁以下");
//            return CreditVettingResultEnum.拒绝;
//        }
//        // 拒绝 - 年龄60岁以上
//        if (age > 60) {
//            logOrder(orderId, CreditVettingResultEnum.拒绝.getValue(), "拒绝 - 年龄60岁以上");
//            return CreditVettingResultEnum.拒绝;
//        }
//        // 拒绝 - 身份证有效期失效
//        CustBaseinfo custBaseInfo = custBaseinfoMapper.selectByPrimaryKey(orderBaseInfo.getCustCellphone());
//        if (custBaseInfo.getIdentityStatus().equals(IdentityStatusEnum.未认证.getValue())
//                || custBaseInfo.getIdentityStatus().equals(IdentityStatusEnum.已过期.getValue())) {
//            logOrder(orderId, CreditVettingResultEnum.拒绝.getValue(), "拒绝 - 身份证有效期失效");
//            return CreditVettingResultEnum.拒绝;
//        }
//        // 拒绝 - 年龄>30且性别为男性
//        if (age > 30 && IDCardUtil.getGenderByIdCard(custId).equals(GenderEnum.男.getValue())) {
//            logOrder(orderId, CreditVettingResultEnum.拒绝.getValue(), "拒绝 - 年龄>30且性别为男性");
//            return CreditVettingResultEnum.拒绝;
//        }
//        // 拒绝 - 年龄<22且申请额度>5w
//        if (age < 22 && orderBaseInfo.getOrderAmount().intValue() > 50000) {
//            logOrder(orderId, CreditVettingResultEnum.拒绝.getValue(), "拒绝 - 年龄<22且申请额度>5w");
//            return CreditVettingResultEnum.拒绝;
//        }
//        // 拒绝 - 学生（年龄18-21岁；学历 专科/本科/硕士及以上）
//        OrderDetailinfo orderDetailinfo = orderDetailinfoMapper.selectByPrimaryKey(orderId);
//        String educationStatus = orderDetailinfo.getEducationalStatus();
//        if (age >= 18 && age <= 21
//                && (educationStatus.equals(EducationStatusEnum.专科.getValue())
//                        || educationStatus.equals(EducationStatusEnum.本科.getValue())
//                        || educationStatus.equals(EducationStatusEnum.硕士.getValue())
//                        || educationStatus.equals(EducationStatusEnum.博士.getValue()))) {
//            logOrder(orderId, CreditVettingResultEnum.拒绝.getValue(), "拒绝 - 学生（年龄18-21岁；学历 专科/本科/硕士及以上）");
//            return CreditVettingResultEnum.拒绝;
//        }
//        // 拒绝 - 同一申请人申请超过3笔
//        Map<String, Object> amount = orderBaseinfoMapper.selectByOrderAmountJudge(orderBaseInfo.getCustCellphone());
//        if (Integer.parseInt(amount.get("statusCount").toString()) > 3) {
//        	logOrder(orderId, CreditVettingResultEnum.拒绝.getValue(), "拒绝 - 同一申请人申请超过3笔");
//            return CreditVettingResultEnum.拒绝;
//        }
//        // 拒绝 - 同一申请人借款金额高于50w
//        if(Integer.parseInt(amount.get("amount").toString()) > 1) {
//        	logOrder(orderId, CreditVettingResultEnum.拒绝.getValue(), "拒绝 - 同一申请人借款金额高于50w");
//            return CreditVettingResultEnum.拒绝;
//        }
        Map<String, Object>parms=new HashMap<>();
        LogMoxieinfo logMoxieinfo=logMoxieinfoMapper.selectByPrimaryKeySelective(parms);
        if(logMoxieinfo.getMoxieStatus()==null||logMoxieinfo.getMoxieStatus()!=5) {
        	logOrder(orderId, CreditVettingResultEnum.拒绝.getValue(), "拒绝 - 报告为空！或者获取失败");
			return CreditVettingResultEnum.拒绝;
        }
        // 拒绝 - 魔蝎运营商报告  黑名单信息   黑中介分数   <=40分  用户信息监测（user_info_check）/用户黑名单信息（check_black_info）/phone_gray_score
		if(logMoxieinfo.getMoxieStatus()==5&&jv.validate(logMoxieinfo.getMoxieContent())){
			Object object=jsonTools.getObjectByJson(logMoxieinfo.getMoxieContent(), "user_info_check.check_black_info", ConstantInterface.Enum.TypeEnum.map);
			if(object==null||Integer.parseInt(MapUtils.getMap2String((Map<String, Object>)object, "phone_gray_score"))<=40) {
				logOrder(orderId, CreditVettingResultEnum.拒绝.getValue(), "拒绝 - 魔蝎运营商报告  黑名单信息   黑中介分数   <=40分");
				return CreditVettingResultEnum.拒绝;
			}
		}
        // 拒绝 - 魔蝎运营商报告  1.5风险分析摘要-魔蝎变量  申请人姓名+身份证号码是否出现在法院黑名单   基本信息校验点（basic_check_items）/is_name_and_idcard_in_court_black
		if(logMoxieinfo.getMoxieStatus()==5&&jv.validate(logMoxieinfo.getMoxieContent())){
			Object object=jsonTools.getObjectByJson(logMoxieinfo.getMoxieContent(), "basic_check_items", ConstantInterface.Enum.TypeEnum.arrayList);
				if(object!=null&&!objToArrayList(object,"is_name_and_idcard_in_court_black")) {
				logOrder(orderId, CreditVettingResultEnum.拒绝.getValue(), "拒绝 - 魔蝎运营商报告  1.5风险分析摘要-魔蝎变量  申请人姓名+身份证号码出现在法院黑名单   基本信息校验点");
				return CreditVettingResultEnum.拒绝;
			}
		}
		// 拒绝 - 魔蝎运营商报告  1.5风险分析摘要-魔蝎变量  申请人姓名+身份证号码是否出现在金融机构黑名单 基本信息校验点（basic_check_items）/is_name_and_idcard_in_finance_black
		if(logMoxieinfo.getMoxieStatus()==5&&jv.validate(logMoxieinfo.getMoxieContent())){
			Object object=jsonTools.getObjectByJson(logMoxieinfo.getMoxieContent(), "basic_check_items", ConstantInterface.Enum.TypeEnum.arrayList);
			if(object!=null&&!objToArrayList(object,"is_name_and_idcard_in_finance_black")) {
				logOrder(orderId, CreditVettingResultEnum.拒绝.getValue(), "拒绝 - 魔蝎运营商报告  1.5风险分析摘要-魔蝎变量  申请人姓名+手机号码是现在金融机构黑名单  基本信息校验点");
				return CreditVettingResultEnum.拒绝;
			}
		}
		// 拒绝 - 魔蝎运营商报告  1.5风险分析摘要-魔蝎变量  申请人姓名+手机号码是否出现在金融机构黑名单  基本信息校验点（basic_check_items）/is_name_and_mobile_in_finance_black
		if(logMoxieinfo.getMoxieStatus()==5&&jv.validate(logMoxieinfo.getMoxieContent())){
			Object object=jsonTools.getObjectByJson(logMoxieinfo.getMoxieContent(), "basic_check_items", ConstantInterface.Enum.TypeEnum.arrayList);
			if(object!=null&&!objToArrayList(object,"is_name_and_mobile_in_finance_black")) {
				logOrder(orderId, CreditVettingResultEnum.拒绝.getValue(), "拒绝 - 魔蝎运营商报告  1.5风险分析摘要-魔蝎变量  申请人姓名+手机号码是现在金融机构黑名单  基本信息校验点");
				return CreditVettingResultEnum.拒绝;
			}
		}
		// 拒绝 - 魔蝎运营商报告  1.5风险分析摘要-魔蝎变量  号码类别--催收公司  近3个月通话次数    >=10(call_risk_analysis)/call_cnt_3m
		if(logMoxieinfo.getMoxieStatus()==5&&jv.validate(logMoxieinfo.getMoxieContent())){
			Object object=jsonTools.getObjectByJson(logMoxieinfo.getMoxieContent(), "call_risk_analysis", ConstantInterface.Enum.TypeEnum.arrayList);
			if(object!=null) {
				ArrayList<Map<String, Object>>list=(ArrayList<Map<String, Object>>) object;
				for (Map<String, Object> map : list) {
					for (Map.Entry<String, Object> entry : map.entrySet()) {
						if(entry.getValue()!=null&&entry.getValue().equals("collection")) {
							Map<String, Object>point=(Map<String, Object>) map.get("analysis_point");
							if(Integer.parseInt(point.get("call_cnt_3m").toString())>=10) {
								logOrder(orderId, CreditVettingResultEnum.拒绝.getValue(), "拒绝 - 魔蝎运营商报告  1.6活跃分析摘要   通话活跃天数  近3个月-通话活跃天数 <=30天");
								return CreditVettingResultEnum.拒绝;
							}
						}
					}
				}
			}
		}
		
        // 拒绝 - 魔蝎运营商报告  1.6活跃分析摘要   通话活跃天数  近3个月-通话活跃天数 <=30天 4.1分析点枚举（近1/3/6月）(active_degree)/call_day
		if(logMoxieinfo.getMoxieStatus()==5&&jv.validate(logMoxieinfo.getMoxieContent())){
			Object object=jsonTools.getObjectByJson(logMoxieinfo.getMoxieContent(), "active_degree", ConstantInterface.Enum.TypeEnum.arrayList);
			if(object!=null) {
				ArrayList<Map<String, Object>>list=(ArrayList<Map<String, Object>>) object;
				for (Map<String, Object> map : list) {
					for (Map.Entry<String, Object> entry : map.entrySet()) {
						if(entry.getValue()!=null&&entry.getValue().equals("call_day")) {
							Map<String, Object>m=(Map<String, Object>) map.get("item");
							if(Integer.parseInt(m.get("item_3m").toString())<=30) {
								logOrder(orderId, CreditVettingResultEnum.拒绝.getValue(), "拒绝 - 魔蝎运营商报告  1.6活跃分析摘要   通话活跃天数  近3个月-通话活跃天数 <=30天");
								return CreditVettingResultEnum.拒绝;
							}
						}
					}
				}
			}
		}
		
        // 拒绝 - 联系人联系电话不在手机报告或通讯录中
		
        // 通过(且) - 近三月通话号码>=10
        // 通过(且) - 近三月互通电话>=3 互通定义，主叫和被叫都有记录
        // 通过(且) - 通讯录号码>=10 
		if(logMoxieinfo.getMoxieStatus()==5&&jv.validate(logMoxieinfo.getMoxieContent())){
			Object dial_cnt=jsonTools.getObjectByJson(logMoxieinfo.getMoxieContent(), "call_service_analysis.analysis_point", ConstantInterface.Enum.TypeEnum.map);
			Object cnt=jsonTools.getObjectByJson(logMoxieinfo.getMoxieContent(), "call_service_analysis.analysis_point", ConstantInterface.Enum.TypeEnum.map);
			if(dial_cnt!=null&&cnt!=null) {
//						if(entry.getValue()!=null&&entry.getValue().equals("call_dial_cnt_3m")) {
//							Map<String, Object>m=(Map<String, Object>) map.get("item");
//							if(Integer.parseInt(m.get("item_3m").toString())<=30) {
//								logOrder(orderId, CreditVettingResultEnum.拒绝.getValue(), "拒绝 - 魔蝎运营商报告  1.6活跃分析摘要   通话活跃天数  近3个月-通话活跃天数 <=30天");
//								return CreditVettingResultEnum.拒绝;
//							}
//						}
					}
			}
//        logOrder(orderId, CreditVettingResultEnum.拒绝.getValue(), "拒绝 - 其它原因");
//        return CreditVettingResultEnum.拒绝;
        logOrder(orderId, CreditVettingResultEnum.通过.getValue(), "");
        return CreditVettingResultEnum.通过;
    }

    private void logOrder(String orderId, String result, String note) {
        // 新增订单操作日志
        LogOrder log = new LogOrder();
        // 订单基本信息
        OrderBaseinfo order = new OrderBaseinfo();
        order.setOrderId(orderId);
        log.setOrderId(orderId);
        log.setOrderOperation(OrderOperationEnum.审批.getValue());
        if (result.equals(CreditVettingResultEnum.通过.getValue())) {
            log.setOrderStatus(OrderStatusEnum.待用户签署.getValue());
            order.setOrderStatus(OrderStatusEnum.待用户签署.getValue());
        }
        if (result.equals(CreditVettingResultEnum.拒绝.getValue())) {
            log.setOrderNote(note);
            log.setOrderStatus(OrderStatusEnum.审批拒绝.getValue());
            order.setOrderStatus(OrderStatusEnum.审批拒绝.getValue());
        }
        log.setCreateTime(new Date());
        logOrderMapper.insert(log);
        orderBaseinfoMapper.updateByPrimaryKeySelective(order);
    }
    
    public boolean objToArrayList(Object object,String eqName){
    	@SuppressWarnings("unchecked")
		ArrayList<Map<String, Object>>list=(ArrayList<Map<String, Object>>) object;
		for (Map<String, Object> map : list) {
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				if(entry.getValue()!=null&&entry.getValue().equals(eqName)) {
					if(!map.get("result").equals("否")) {
						return false;
					}
				}
			}
		}
		return true;
    }
}
