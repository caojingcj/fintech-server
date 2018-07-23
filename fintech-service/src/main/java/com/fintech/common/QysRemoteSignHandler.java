package com.fintech.common;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.common.oss.FileUploadSample;
import com.fintech.common.oss.OSSEntity;
import com.fintech.common.properties.AppConfig;
import com.fintech.service.RedisService;
import com.fintech.util.CommonUtil;
import com.fintech.util.DateUtils;
import com.qiyuesuo.sdk.SDKClient;
import com.qiyuesuo.sdk.api.LocalSignService;
import com.qiyuesuo.sdk.api.RemoteSignService;
import com.qiyuesuo.sdk.api.SealService;
import com.qiyuesuo.sdk.impl.LocalSignServiceImpl;
import com.qiyuesuo.sdk.impl.RemoteSignServiceImpl;
import com.qiyuesuo.sdk.impl.SealServiceImpl;
import com.qiyuesuo.sdk.sign.Stamper;
import com.qiyuesuo.sdk.signer.PaperType;
import com.qiyuesuo.sdk.signer.Person;
import com.qiyuesuo.sdk.template.Template;

/**
 * @Title: QysRemoteSignHandler.java
 * @Package com.medcfc.biz.company.encontract
 * @author qierkang xyqierkang@163.com
 * @date 2018年5月19日 下午5:54:38
 * @Description: TODO[ 契约锁》商户线上签约 ]
 */
@Service("qysRemoteSignHandler")
public class QysRemoteSignHandler {

    private static final Logger logger = LoggerFactory.getLogger(QysRemoteSignHandler.class);
    /** 
    * @Fields sdkClient : TODO[ 契约锁SDK客户端封装类 ] 
    */
     static SDKClient sdkClient;
    /**
     * 本地签接口
     */
     static LocalSignService localSignService;
    /**
     * 远程签服务接口 远程签需要将合同文件保存在契约锁服务器
     */
     static RemoteSignService remoteSignService;
    /**
     * 印章服务接口
     */
     static SealService sealService;
     
     private static AppConfig appConfig;
     
     /** 
	* @param appConfig 要设置的 appConfig 
	*/
     @Autowired(required = true)
 	public void setAppConfig(AppConfig appConfig) {
    	 QysRemoteSignHandler.appConfig = appConfig;
 	}

	@PostConstruct
     private void init() {
         try {
             logger.info("EK>初始化QysRemoteSignHandler契约锁方法名[{}，{}，{}]操作时间[{}]",appConfig.getQYS_SERVER_URL(),appConfig.getQYS_ACCESS_KEY(), appConfig.getQYES_ACCESS_SECRET(),Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
             sdkClient = new SDKClient(appConfig.getQYS_SERVER_URL(),appConfig.getQYS_ACCESS_KEY(), appConfig.getQYES_ACCESS_SECRET());
             localSignService = new LocalSignServiceImpl(sdkClient);
             remoteSignService = new RemoteSignServiceImpl(sdkClient);
             sealService = new SealServiceImpl(sdkClient);
         } catch (Exception e) {
             e.printStackTrace();
         }
     }

    /** 
    * @Title: QysRemoteSignHandler.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年5月19日 下午6:11:03  
    * @param @param closeable    设定文件 
    * @Description: TODO[ 方法用作于关闭文件IO流 ]
    * @throws 
    */
    private static void safeClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
    
     private OSSEntity signRemoteCa(long templateId, String applyId, Map<String, String> params, String stampPostion, String personSignPosition, String name, String idcard, String mobile, Integer fileType) {
            return signRemoteCa(templateId, applyId, params, stampPostion, personSignPosition, name, idcard, mobile, fileType,"1");
        }

        private OSSEntity signRemoteCa(long templateId, String applyId, Map<String, String> params, String stampPostion, String personSignPosition, String name, String idcard, String mobile, Integer fileType, String caSingType) {
            Long documentId = createDocumentId(templateId, applyId, params);
            if (null == caSingType || "1" == caSingType || "3" == caSingType) {
                stampSign(stampPostion, documentId);
            }
            personSign(personSignPosition, documentId, name, idcard, mobile);
            logger.info("个人签署完成。");
            return uploadSignFile(documentId, fileType, applyId);
        }
        
        /**
         * 下载签约文件
         *
         * @param documentId
         * @param fileType
         * @param applyId
         * @return
         */
        private OSSEntity uploadSignFile(Long documentId, Integer fileType, String applyId) {
            // 下载合同文件
            try {
                File personFile = File.createTempFile(fileType + "ca.pdf", "pdf");
                OutputStream outputStream = new FileOutputStream(personFile);
                remoteSignService.download(documentId, outputStream);
                remoteSignService.complete(documentId);
                OSSEntity oss = new FileUploadSample().uploadFile(personFile, applyId + "_" + fileType + ".pdf");
                safeClose(outputStream);
                oss.setKey(documentId.toString());
                return oss;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        
        /**
         * 个人签章
         *
         * @param position
         * @param documentId
         */
        private void personSign(String position, Long documentId, String userName, String idCard, String mobile) {
            if (CommonUtil.isNullStr(position)) {
                throw new RuntimeException("个人签约位置信息为空");
            }
            String[] positionArr = position.split(",");
            if (positionArr.length != 3) {
                throw new RuntimeException("个人签约位置信息错误");
            }

            int page = Integer.valueOf(positionArr[0]);
            float x = Float.valueOf(positionArr[1]);
            float y = Float.valueOf(positionArr[2]);

            Person person = new Person(userName);
            person.setIdcard(idCard);
            person.setPaperType(PaperType.IDCARD);
            person.setMobile(mobile);
            String sealData = sealService.generateSeal(person);// 生成个人印章数据
            // 个人签署
            Stamper personStamper = new Stamper(page, x, y);
            // 个人签署接口，有签名外观
            remoteSignService.sign(documentId, person, sealData, personStamper);
        }
        
        /**
         * 平台签章 （乔融）
         *
         * @param stampPostion
         * @param documentId
         */
        private void stampSign(String stampPostion, Long documentId) {
            long platformSealId=0;
            if (CommonUtil.isNullStr(stampPostion)) {
                throw new RuntimeException("平台签约位置信息为空");
            }
            String[] positionArr = stampPostion.split(",");
            if (positionArr.length != 3) {
                throw new RuntimeException("平台签约位置信息错误");
            }
            String arrStamp[] = stampPostion.split(",");
            int pageS = Integer.valueOf(arrStamp[0]);
            float xS = Float.valueOf(arrStamp[1]);
            float vS = Float.valueOf(arrStamp[2]);
            Stamper stamper = new Stamper(pageS, xS, vS);// 签名位置
            remoteSignService.sign(documentId, platformSealId, stamper);
        }
        
        /** 
        * @Title: QysRemoteSignHandler.java 
        * @author qierkang xyqierkang@163.com   
        * @date 2018年5月19日 下午8:44:20  
        * @param @param templateId
        * @param @param applyId
        * @param @param params
        * @param @return    设定文件 
        * @Description: TODO[创建合同 返回文件id ]
        * @throws 
        */
        private Long createDocumentId(long templateId, String applyId, Map<String, String> params) {
            // 根据模板创建合同
            Template template = new Template(templateId);// 创建模板对象
            // 设置模板参数
            Set<String> set = params.keySet();
            for (String s : set) {
                template.addParameter(s, params.get(s));
            }
            Long documentId = remoteSignService.create(template, applyId);
            return documentId;
        }
    
    public OSSEntity signOrderCA(String contractId,Map<String, String> qysParams) throws IOException {
          logger.info("EK>用户签约授权书>商户编号：[{}]方法名[{}]操作时间[{}]",contractId,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
            try {
                /**** qys用户签署 ***/
                OSSEntity oss = QYS_SignCA.signCommit(contractId, Long.parseLong(appConfig.getQYES_CA_DOCID()), Long.parseLong(appConfig.getQYES_CA_SEALID()), qysParams, appConfig.getQYES_CA_STAMP(),"签约合同");
                return oss;
            } catch (Exception e) {
                logger.info("EK>QYS ERROR>商户编号：[{}]方法名[{}]操作时间[{}]error:[{}]",contractId,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime(),e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
      
      public String getCaTemplate(long templateDocId,Map<String, String> qysParams) {
          String viewUrl = QYS_SignCA.getCaTemplate(templateDocId, qysParams,"合同号");
          return viewUrl;
      }
}
