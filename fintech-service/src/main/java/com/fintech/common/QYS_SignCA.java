package com.fintech.common;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fintech.common.oss.FileUploadSample;
import com.fintech.common.oss.OSSEntity;
import com.fintech.common.properties.AppConfig;
import com.fintech.util.DateUtils;
import com.qiyuesuo.sdk.SDKClient;
import com.qiyuesuo.sdk.api.LocalSignService;
import com.qiyuesuo.sdk.api.RemoteSignService;
import com.qiyuesuo.sdk.api.SealService;
import com.qiyuesuo.sdk.api.StandardSignService;
import com.qiyuesuo.sdk.api.TemplateService;
import com.qiyuesuo.sdk.exception.BaseSdkException;
import com.qiyuesuo.sdk.impl.LocalSignServiceImpl;
import com.qiyuesuo.sdk.impl.RemoteSignServiceImpl;
import com.qiyuesuo.sdk.impl.SealServiceImpl;
import com.qiyuesuo.sdk.impl.StandardSignServiceImpl;
import com.qiyuesuo.sdk.impl.TemplateServiceImpl;
import com.qiyuesuo.sdk.sign.SignType;
import com.qiyuesuo.sdk.sign.SignUrlResponse;
import com.qiyuesuo.sdk.sign.Stamper;
import com.qiyuesuo.sdk.sign.ViewUrlResponse;
import com.qiyuesuo.sdk.signer.PaperType;
import com.qiyuesuo.sdk.signer.Person;
import com.qiyuesuo.sdk.template.Template;

/**
 * @author Jerry
 * @Title QYS_SignCA
 * @Version 1.0
 * @Company jufan
 * @Date 2017年6月23日 下午12:13:38
 * @Description 契约锁合同签署API<br>暂时只用到了RemoteSignService，Te
 */
public class QYS_SignCA {
    private static final Logger logger = LoggerFactory.getLogger(QYS_SignCA.class);
    private static SDKClient sdkClient = null;
    private static ThreadLocal<ArrayList<String>> log;
    private static RemoteSignService remoteSignService;//远程签
    private static TemplateService templateService;//模板
    private static SealService sealService;//印章
    private static LocalSignService localSignService;//本地签
    private static StandardSignService standardSignService;//标准签
    private static AppConfig config;
    
    private static String serverUrl="https://openapi.qiyuesuo.me/";
    private static String accessKey="hCDzvig7TF";
    private static String accessSecret="txyriflJVg7xpjEo1LNPiAF1wen9C3";
//    private static String serverUrl="https://openapi.qiyuesuo.com/";
//    private static String accessKey="yxAIiAwH3L";
//    private static String accessSecret="Bbvgf57uLlOJqzhz2Jiea4g7J5YEMs";
    
    static {
        logger.info("初始化CA,{}===》{}==>{}",serverUrl, accessKey, accessSecret);
        sdkClient =new SDKClient(serverUrl, accessKey, accessSecret);
        log =new ThreadLocal<ArrayList<String>>();
    }

    private QYS_SignCA() {
        
    };//私有化构造器，防呆

    /**
     * @author Jerry
     * @Method getService
     * @Company jufan
     * @Description 懒加载<br>Warn:Only SealService.class,TemplateService.class,LocalSignService.class,RemoteSignService.class,StandardSignService.class is permitted</p>
     * @param T 要获得类型的interface类型
     * @return T 要获得类型的interface类型
     */
    public static <T> T getService(Class T) {
        if (RemoteSignService.class.equals(T)) {
            if (remoteSignService == null) {
                synchronized (T) {
                    if (remoteSignService == null) {
                        remoteSignService = new RemoteSignServiceImpl(sdkClient);
                    }
                }
            }
            return (T) remoteSignService;
        } else if (TemplateService.class.equals(T)) {
            if (templateService == null) {
                synchronized (T) {
                    if (templateService == null) {
                        templateService = new TemplateServiceImpl(sdkClient);
                    }
                }
            }
            return (T) templateService;
        } else if (SealService.class.equals(T)) {
            if (sealService == null) {
                synchronized (T) {
                    if (sealService == null) {
                        sealService = new SealServiceImpl(sdkClient);
                    }
                }
            }
            return (T) sealService;
        } else if (LocalSignService.class.equals(T)) {
            if (localSignService == null) {
                synchronized (T) {
                    if (localSignService == null) {
                        localSignService = new LocalSignServiceImpl(sdkClient);
                    }
                }
            }
            return (T) localSignService;
        } else if (StandardSignService.class.equals(T)) {
            if (standardSignService == null) {
                synchronized (T) {
                    if (standardSignService == null) {
                        standardSignService = new StandardSignServiceImpl(sdkClient);
                    }
                }
            }
            return (T) standardSignService;
        } else {
            throw new IllegalArgumentException(
                "Only SealService.class,TemplateService.class,LocalSignService.class,RemoteSignService.class,StandardSignService.class is permitted");
        }
    }

    /**
     * @author Jerry
     * @Method createContractWithPDF
     * @Company jufan
     * @Description 用本地pdf文件在契约锁平台创建可签署合同</p>
     * @param path pdf文件路径
     * @param contractDescription 契约锁平台文件描述，本地使用合同表ID
     * @throws FileNotFoundException
     * @return long 返回契约锁平台合同ID
     */
    public static long createContractWithPDF(String path, String contractDescription) throws FileNotFoundException {
        getService(RemoteSignService.class);
        long contractDocId = remoteSignService.create(new FileInputStream(new File(path)),
            contractDescription);
        toLog("用pdf上传创建合同完成>contractDocId:" + contractDocId);
        return contractDocId;
    }

    /**
     * @author Jerry
     * @Method createContractWithHtml
     * @Company jufan
     * @Description 用html在契约锁平台创建可签署合同</p>
     * @param html html格式字符串
     * @param contractDescription 契约锁平台文件描述，本地使用合同表ID
     * @return long 返回契约锁平台合同ID
     */
    public static long createContractWithHtml(String html, String contractDescription) {
        getService(RemoteSignService.class);
        long contractDocId = remoteSignService.create(html, contractDescription);
        toLog("用html创建合同完成>contractDocId:" + contractDocId);
        return contractDocId;
    }

    /**
     * @author Jerry
     * @Method createContractWithTemplate
     * @Company jufan
     * @Description 用契约锁平台模板文件在契约锁平台创建可签署合同<br>Warn:需要在契约锁平台创建模板合同</p>
     * @param templateDocId 在契约锁平台创建的模板文件ID
     * @param params 模板中需要替换的参数
     * @param contractDescription 契约锁平台文件描述，本地使用合同表ID
     * @return long 返回契约锁平台合同ID
     */
    public static long createContractWithTemplate(long templateDocId, Map<String, String> params, String contractDescription) {
        Template template = new Template(templateDocId);
        for (String name : params.keySet()) {
            template.addParameter(name, params.get(name));
        }
        getService(RemoteSignService.class);
        long contractDocId = remoteSignService.create(template, contractDescription);
        toLog("用契约锁平台模板创建合同完成>contractDocId:" + contractDocId);
        return contractDocId;
    }

    public static String getCaTemplate(long templateDocId, Map<String, String> params, String contractDescription) {
        Template template = new Template(templateDocId);
        for (String name : params.keySet()) {
            template.addParameter(name, params.get(name));
        }
        getService(RemoteSignService.class);
        long contractDocId;
        try {
            contractDocId = remoteSignService.create(template, contractDescription);
            ViewUrlResponse response = remoteSignService.viewUrl(contractDocId);
            toLog("返回合同模版PDF>contractDocId:" + response);
            return response.getViewUrl();
        } catch (BaseSdkException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @author Jerry
     * @Method platformSignWithStamp
     * @Company jufan
     * @Description 平台方签署合同带水印<br>Warn:需要在契约锁平台创建印章</p>
     * @param contractDocId 契约锁平台合同ID
     * @param platformSealId 契约锁平台创建的印章ID
     * @param page 水印页面，页码从1开始
     * @param x 水印水平方向坐标，原点为页面左下角
     * @param y 水印垂直方向坐标，原点为页面左下角
     */
    public static void platformSignWithStamp(long documentId, long sealId, int page, float x, float y) {
        Stamper stamper = new Stamper(page, x, y);
        getService(RemoteSignService.class);
        remoteSignService.sign(documentId, sealId, stamper);
        toLog("平台印章签署完成>documentId:" + documentId);
    }

    /**
     * @author Jerry
     * @Method personSignWithStamp
     * @Company jufan
     * @Description 个人用户签署合同带水印静默签署</p>
     * @param contractDocId 契约锁平台合同ID
     * @param name 个人用户姓名
     * @param idNo 个人用户身份证
     * @param mobile 个人用户手机号
     * @param page 水印页面，页码从1开始
     * @param x 水印水平方向坐标，原点为页面左下角
     * @param y 水印垂直方向坐标，原点为页面左下角
     */
    public static void personSignWithStamp(long contractDocId, String name, String idNo, String mobile, int page, float x, float y) {
        Person signer = new Person(name);
        signer.setIdcard(idNo);
        signer.setPaperType(PaperType.IDCARD);
        signer.setMobile(mobile);
        getService(SealService.class);
        String personSealData = sealService.generateSeal(signer);// 生成个人印章数据，用户可自定义签名图片
        Stamper personStamper = new Stamper(page, x, y);
        getService(RemoteSignService.class);
        remoteSignService.sign(contractDocId, signer, personSealData, personStamper);
        toLog("个人签署完成>contractDocId:" + contractDocId);
    }

    /**
     * @author Jerry
     * @Method personSignWithStampSMS
     * @Company jufan
     * @Description 生成个人用户待签署合同带水印短信签署</p>
     * @param contractDocId 契约锁平台合同ID
     * @param name 个人用户姓名
     * @param idNo 个人用户身份证
     * @param mobile 个人用户手机号
     * @param page 水印页面，页码从1开始
     * @param x 水印水平方向坐标，原点为页面左下角
     * @param y 水印垂直方向坐标，原点为页面左下角
     * @param callBackUrl 指定合同个人用户签署完毕后回调地址
     * @return String 待签署合同签署页面
     */
    public static String personSignWithStampSMS(long contractDocId, String name, String idNo, String mobile, int page, float x, float y, String callBackUrl) {
        Person signer = new Person(name);
        signer.setIdcard(idNo);
        signer.setPaperType(PaperType.IDCARD);
        signer.setMobile(mobile);
        getService(SealService.class);
        String personSealData = sealService.generateSeal(signer);// 生成个人印章数据，用户可自定义签名图片
        Stamper personStamper = new Stamper(page, x, y);
        getService(RemoteSignService.class);
        SignUrlResponse personSignVisibleResponse = remoteSignService.signUrl(contractDocId,
            SignType.SIGNWITHPIN,
            signer,
            personSealData,
            personStamper,
            callBackUrl);
        toLog("个人签署合同准备完成,可以进行短信验证>contractDocId:" + contractDocId);
        return personSignVisibleResponse.getSignUrl();
    }

    /**
     * @author Jerry
     * @Method closeContract
     * @Company jufan
     * @Description 结束合同签署流程</p>
     * @param contractDocId 契约锁平台合同ID
     */
    public static void closeContract(long contractDocId) {
        getService(RemoteSignService.class);
        remoteSignService.complete(contractDocId);
        toLog("签署完成>contractDocId:" + contractDocId);
    }

    /**
     * @author Jerry
     * @Method download
     * @Company jufan
     * @Description 下载签署完毕的合同到本地路径</p>
     * @param contractDocId 契约锁平台合同ID
     * @param filepath 指定文件下载到的本地路径
     */
    public static void download(long contractDocId, String filepath) {
        File downToLocalFile = new File(filepath);
        if (!downToLocalFile.getParentFile().exists()) {
            downToLocalFile.getParentFile().mkdirs();
        }
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(downToLocalFile);
            getService(RemoteSignService.class);
            remoteSignService.download(contractDocId, outputStream);
            toLog("下载完成>contractDocId:" + contractDocId);
        } catch (FileNotFoundException e) {
            toLog("下载失败>contractDocId:" + contractDocId);
            e.printStackTrace();
        } catch (Exception e) {
            toLog("下载失败>contractDocId:" + contractDocId);
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                safeClose(outputStream);
            }
        }
    }

    /**
     * @author Jerry
     * @Method download
     * @Company jufan
     * @Description 下载签署完毕的合同到本地路径</p>
     * @param contractDocId 契约锁平台合同ID
     */
    public static OSSEntity download(String companyId, long contractDocId) {
        OutputStream outputStream = null;
        try {
            Date nowDate = new Date();
            SimpleDateFormat year = new SimpleDateFormat("yyyyMM");
            SimpleDateFormat day = new SimpleDateFormat("dd");
            String nowDateTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(nowDate);
            String fileName = companyId + "_QYS" + nowDateTime;
            String path = "manage/qys/" + year.format(nowDate) + "/" + day.format(nowDate);
            String fileUrl = path + "/" + fileName;
            File downToLocalFile = File.createTempFile(fileUrl, ".pdf");
            outputStream = new FileOutputStream(downToLocalFile);
            getService(RemoteSignService.class);
            remoteSignService.download(contractDocId, outputStream);
            return new FileUploadSample().uploadFile(downToLocalFile, fileUrl + ".pdf");
        } catch (Exception e) {
            toLog("下载失败>contractDocId:" + contractDocId);
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                safeClose(outputStream);
            }
        }
        return null;
    }

    /**
     * @author Jerry
     * @Method toLog
     * @Company jufan
     * @Description 线程独享的合同签署日志，打包输出，防止异步输出</p>
     * @param logStr 需要输出的日志内容，输入null则不输出如何内容，用来在合同流程外提取log内容
     * @return ArrayList<String> 日志输出的arrayList对象
     */
    public static ArrayList<String> toLog(String logStr) {
        ArrayList localArr = log.get();
        if (localArr == null) {
            log.set(new ArrayList<String>());
        }
        ArrayList arr = log.get();
        if (logStr != null) {
            arr.add(logStr);
            log.set(arr);
        }
        return arr;
    }

    private static void safeClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**签署合同**/
    public static long signCommit(long templateDocId, long platformSealId, Map<String, String> params, String contractFullPath, String stampPostion, String personSignPosition) {
        //用模板创建合同
        logger.info("EK>before---用模板创建合同>方法名[{}]操作时间[{}]",
            Thread.currentThread().getStackTrace()[1].getMethodName(),
            DateUtils.getDateTime());
        long contractDocId = QYS_SignCA.createContractWithTemplate(templateDocId, params, "合同号");
        logger.info("EK>Done--用模板创建合同>方法名[{}]操作时间[{}]",
            Thread.currentThread().getStackTrace()[1].getMethodName(),
            DateUtils.getDateTime());

        String arrStamp[] = stampPostion.split(",");
        String arrPerson[] = personSignPosition.split(",");

        int pageS = Integer.valueOf(arrStamp[0]);
        float xS = Float.valueOf(arrStamp[1]);
        float vS = Float.valueOf(arrStamp[2]);

        int pageP = Integer.valueOf(arrPerson[0]);
        float xP = Float.valueOf(arrPerson[1]);
        float vP = Float.valueOf(arrPerson[2]);

        //平台签名
        QYS_SignCA.platformSignWithStamp(contractDocId, platformSealId, pageS, xS, vS);
        logger.info("EK>Done---平台签名>方法名[{}]操作时间[{}]",
            Thread.currentThread().getStackTrace()[1].getMethodName(),
            DateUtils.getDateTime());

        //客户签名
        QYS_SignCA.personSignWithStamp(contractDocId,
            params.get("APPLY_NAME"),
            params.get("APPLY_IDNO"),
            params.get("APPLY_TEL"),
            pageP,
            xP,
            vP);
        logger.info("EK>Done---客户签名>方法名[{}]操作时间[{}]",
            Thread.currentThread().getStackTrace()[1].getMethodName(),
            DateUtils.getDateTime());
        //合同完成
        QYS_SignCA.closeContract(contractDocId);
        logger.info("EK>Done---合同完成>方法名[{}]操作时间[{}]",
            Thread.currentThread().getStackTrace()[1].getMethodName(),
            DateUtils.getDateTime());

        //下载合同
        QYS_SignCA.download(contractDocId, contractFullPath);
        logger.info("EK>Done---下载合同完成>方法名[{}]操作时间[{}]",
            Thread.currentThread().getStackTrace()[1].getMethodName(),
            DateUtils.getDateTime());
        return contractDocId;
    }

    /**签署合同**/
    public static OSSEntity signCommit(String companyId, long templateDocId, long platformSealId, Map<String, String> params, String stampPostion, String personSignPosition) {
        //用模板创建合同
        logger.info("EK>before---用模板创建合同>方法名[{}]操作时间[{}]",
            Thread.currentThread().getStackTrace()[1].getMethodName(),
            DateUtils.getDateTime());
        long contractDocId = QYS_SignCA
            .createContractWithTemplate(templateDocId, params, companyId);
        logger.info("EK>Done--用模板创建合同>Success, contractDocId[{}]方法名[{}]操作时间[{}]",
            contractDocId,
            Thread.currentThread().getStackTrace()[1].getMethodName(),
            DateUtils.getDateTime());
        String arrStamp[] = stampPostion.split(",");
        //      String arrPerson[]= personSignPosition.split(",");
        int pageS = Integer.valueOf(arrStamp[0]);
        float xS = Float.valueOf(arrStamp[1]);
        float vS = Float.valueOf(arrStamp[2]);
        //      int pageP= Integer.valueOf(arrPerson[0]);
        //      float xP=  Float.valueOf(arrPerson[1]);
        //      float vP=  Float.valueOf(arrPerson[2]);
        //平台签名
        QYS_SignCA.platformSignWithStamp(contractDocId, platformSealId, pageS, xS, vS);
        logger.info("EK>Done---平台签名>方法名[{}]操作时间[{}]",
            Thread.currentThread().getStackTrace()[1].getMethodName(),
            DateUtils.getDateTime());
        //      //客户签名
        //      QYS_SignCA.personSignWithStamp(contractDocId, params.get("BUSINESS_NAME"), "", "", pageP, xP, vP);
        //      logger.info("EK>Done---客户签名>方法名[{}]操作时间[{}]",Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
        //合同完成
        QYS_SignCA.closeContract(contractDocId);
        logger.info("EK>Done---下载合同完成>方法名[{}]操作时间[{}]",
            Thread.currentThread().getStackTrace()[1].getMethodName(),
            DateUtils.getDateTime());
        //下载合同
        OSSEntity ossEntity = QYS_SignCA.download(companyId, contractDocId);
        return ossEntity;
    }

    public static void main(String[] args) {
        try {
            long templateDocId = 2314707370428187404l;
            long platformSealId = 2314706482469843360l;
            Map<String, String> params = new HashMap<String, String>(2);

            params.put("APPLY_NAME", "张三");
            params.put("APPLY_IDNO", "123256123658942354");
            //
            //用模板创建合同
            long contractDocId = QYS_SignCA
                .createContractWithTemplate(templateDocId, params, "测试签署合同");
            //平台签名
            QYS_SignCA.platformSignWithStamp(contractDocId, platformSealId, 1, 0.1f, 0.1f);
            //客户签名
            QYS_SignCA.personSignWithStamp(contractDocId,
                "张三",
                "123256123658942354",
                "18068801721",
                1,
                0.2f,
                0.1f);
            //合同完成
            QYS_SignCA.closeContract(contractDocId);
            //下载合同
            QYS_SignCA.download(contractDocId, "/home/daxiang/test/remote-download.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ArrayList<String> arr = QYS_SignCA.toLog(null);
            for (String logLine : arr) {
                System.out.println(logLine);
            }
        }

        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            es.execute(new Runnable() {
                public void run() {
                    QYS_SignCA.toLog("wew");
                    QYS_SignCA.toLog("---");
                }
            });
        }
        es.shutdown();
    }
}

/**
 * Revision History
 * -------------------------------------------------------------------------
 * Version       Date             Author          Note
 * -------------------------------------------------------------------------
 * 1.0           2017年5月16日         Jerry
 *
 */