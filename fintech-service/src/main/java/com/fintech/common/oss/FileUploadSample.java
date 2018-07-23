package com.fintech.common.oss;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.fintech.common.properties.AppConfig;
import com.fintech.util.CommonUtil;
import com.fintech.util.DateUtils;
import com.fintech.util.enumerator.ConstantInterface;
import com.fintech.util.image.FileTypeUtil;

import sun.misc.BASE64Decoder;
@Component
public class FileUploadSample {
    
    @Autowired
    private AppConfig appConfig;
    private static String endpoint        = "http://oss-cn-shanghai.aliyuncs.com";
    private static String accessKeyId     = "LTAIFJarWhCyP9fu";
    private static String accessKeySecret = "3KIqdmIjb5AwkAIwQYwgymTInCn3P1";
    private static String bucketName      = "fintech-attachment";
     static String ENDPOINT;
     static String ACCESS_KEY_ID;
     static String ACCESS_KEY_SECRET;
     static String BUCKET_NAME;
    @PostConstruct
    public void init() {
        ENDPOINT=appConfig.getENDPOINT();
        ACCESS_KEY_ID=appConfig.getACCESS_KEY_ID();
        ACCESS_KEY_SECRET=appConfig.getACCESS_KEY_SECRET();
        BUCKET_NAME=appConfig.getBUCKET_NAME();
    }
    
     Logger logger = LoggerFactory.getLogger(FileUploadSample.class);
    /**
     * 上传字符串
     *
     * @param content
     * @param fileName
     * @return
     */
    public OSSEntity uploadStr(String content, String fileName) {
        OSSClient ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        ossClient.putObject(BUCKET_NAME, fileName, new ByteArrayInputStream(content.getBytes()));
        if (CommonUtil.isNullStr(fileName)) {
            fileName = CommonUtil.getUUIDString();
        }
        // 关闭client
        OSSObject o = ossClient.getObject(BUCKET_NAME, fileName);
        ossClient.shutdown();
        return getOSSEntity(o);
    }

    public static void main(String[] args) throws IOException {
        List<String> keys=new ArrayList<>();
        FileUploadSample s = new FileUploadSample();
//        keys.add("manage/merchant/20170831/EK000-17");
//        keys.add("manage/merchant/20170831/EK000-18.jpg");
//        keys.add("manage/merchant/20170831/EK000-19.jpg");
//        System.out.println(s.deleteFile(keys));
        
        File f = new File("C:/Users/erkang/Desktop/营业执照/test.jpg");
        FileInputStream fs = new FileInputStream(f);
        String fileName = f.getName();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String[] type = { "JPEG", "png","ZIP" };
        boolean image = Arrays.asList(type)
            .contains(FileTypeUtil.getInputStreamType(fs).toString());
        System.out.println(image);
        String folder = CommonUtil.getStringTime(new Date(), "yyyyMMdd") + "/";
        String path = "fintech/order/" + folder
                      + "attchType" + "-"
                      + new Random().nextInt(40) + suffix;
////        图片类型1：标准合同2：利率合同3：打款账户4：营业执照5：法人身份证6：商户授权书7：卫生许可证8：开户许可证9：委托授权书10：商户门头11:医院前台12：医疗器械13其他14商户交接表
//        String path = "manage/merchant/attach/20170901/103716/"+ ConstantInterface.ManageAttachmentEnum.EK001.getValue() + "-"
//                + new Random().nextInt(40)+ suffix;
//        System.out.println(path);
        s.uploadFile(f, path);
     // 构造ListObjectsRequest请求
//        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(BUCKET_NAME);
//        listObjectsRequest.setPrefix("manage/merchant/attach/20170924/105378");
//        // 递归列出fun目录下的所有文件
//        OSSClient ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
//        ObjectListing listing = ossClient.listObjects(listObjectsRequest);
//        // 遍历所有Object
//        System.out.println("Objects:");
//        for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
//            System.out.println(objectSummary.getKey());
//        }
//        // 遍历所有CommonPrefix
//        System.out.println("\nCommonPrefixs:");
//        for (String commonPrefix : listing.getCommonPrefixes()) {
//            System.out.println(commonPrefix);
//        }
    }

    public OSSEntity uploadFile(File file, String fileName) {
        OSSClient ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        checkFile(file);
        // 上传文件
        ossClient.putObject(BUCKET_NAME, fileName, file);
        OSSObject o = ossClient.getObject(BUCKET_NAME, fileName);
        logger.info("EK 感谢您使用阿里云对象存储服务>>>已上传文件：[{}]方法名[{}]操作时间[{}]",o.getResponse().getUri(),Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
        ossClient.shutdown();
        return getOSSEntity(o);
    }

    /**
     * 上传二级制
     *
     * @param bytes
     * @param fileName
     * @return
     */
    public OSSEntity uploadFile(byte[] bytes, String fileName) {
        OSSClient ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        // 创建OSSClient实例
        if (CommonUtil.isNullStr(fileName)) {
            fileName = CommonUtil.getUUIDString();
        }
        // 上传文件
        ossClient.putObject(BUCKET_NAME, fileName, new ByteArrayInputStream(bytes));
        OSSObject o = ossClient.getObject(BUCKET_NAME, fileName);
        ossClient.shutdown();
        return getOSSEntity(o);
    }

    /**
     * 上传base64
     *
     * @param base64
     * @param fileName
     * @return
     */
    public OSSEntity uploadFileBase64(String base64, String fileName) {
        if (CommonUtil.isStrBlank(base64)) {
            return null;
        }
        if (CommonUtil.isNullStr(fileName)) {
            fileName = CommonUtil.getUUIDString();
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(base64);
            return uploadFile(b, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 上传URL
     *
     * @param url
     * @param fileName
     * @return
     * @throws IOException
     */
    public OSSEntity uploadFile(String url, String fileName) throws IOException {
        OSSClient ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        // 创建OSSClient实例
        if (CommonUtil.isNullStr(fileName)) {
            fileName = CommonUtil.getUUIDString();
        }
        try {
            // 上传文件
            InputStream inputStream = new URL(url).openStream();
            ossClient.putObject(BUCKET_NAME, fileName, inputStream);
            OSSObject o = ossClient.getObject(BUCKET_NAME, fileName);
            ossClient.shutdown();
            return getOSSEntity(o);

        } catch (IOException e) {
            return null;
        }

    }

    /**
     * 通过字节流上传
     *
     * @param inputStream
     * @param fileName
     * @return
     */
    public OSSEntity uploadFile(InputStream inputStream, String fileName) {
        OSSClient ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        // 创建OSSClient实例
        if (CommonUtil.isNullStr(fileName)) {
            fileName = CommonUtil.getUUIDString();
        }
        ossClient.putObject(BUCKET_NAME, fileName, inputStream);
        OSSObject o = ossClient.getObject(BUCKET_NAME, fileName);
        logger.info("EK 感谢您使用阿里云对象存储服务>>>已上传文件：[{}]方法名[{}]操作时间[{}]",o.getResponse().getUri(),Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
        ossClient.shutdown();
        return getOSSEntity(o);
    }

    /** 
    * @Title: FileUploadSample.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2017年9月1日 上午1:55:04  
    * @param @param fileUrl
    * @param @return    设定文件 
    * @Description: TODO[ 删除单个文件或者删除多个文件 ]
    * @throws 
    * 
    */
    public boolean deleteFile(List<String> keys) {
        if (BUCKET_NAME == null || keys == null)
            return false;
        OSSClient ossClient = null;
        try {
            ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
            DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(new DeleteObjectsRequest(BUCKET_NAME).withKeys(keys));
            List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
            for (String object : deletedObjects) {
                logger.info("EK 感谢您使用阿里云对象存储服务>>>已删除文件：[{}]方法名[{}]操作时间[{}]",object,Thread.currentThread().getStackTrace()[1].getMethodName(),DateUtils.getDateTime());
          }
        } catch (Exception oe) {
            oe.printStackTrace();
            return false;
        } finally {
            ossClient.shutdown();
        }
        return true;
    }
    
    /** 
    * @Title: FileUploadSample.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2017年9月24日 上午12:28:18  
    * @param @param companyId
    * @param @return    设定文件 
    * @Description: TODO[ 根据  日期+companyd删除指定文件夹下所有文件 ]
    * @throws 
    */
    public boolean deleteFolder(String companyId) {
        if (BUCKET_NAME == null || companyId == null)
            return false;
        try {
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest(BUCKET_NAME);
            listObjectsRequest.setPrefix("manage/merchant/attach/"+companyId);
            // 递归列出 所有文件
            OSSClient ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
            ObjectListing listing = ossClient.listObjects(listObjectsRequest);
            // 遍历所有Object
            List<String> str=new ArrayList<>();
            for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
                str.add(objectSummary.getKey());
            }
            if(str.size()>0){
                deleteFile(str);
            }
        } catch (Exception oe) {
            oe.printStackTrace();
            return false;
        }
        return true;
    }


    public OSSEntity getOSSEntity(OSSObject o) {
        if (null == o) {
            return null;
        }
        OSSEntity entity = new OSSEntity();
        entity.setKey(o.getKey());
        entity.setUrl(o.getResponse().getUri());
        entity.setBucketName(o.getBucketName());
        System.out.println(JSON.toJSONString(entity));
        return entity;
    }
    
    public static boolean checkFile(File file) {
        if (file == null) {
            return false;
        }
        boolean exists = false;
        boolean isFile = false;
        boolean canRead = false;
        try {
            exists = file.exists();
            isFile = file.isFile();
            canRead = file.canRead();
        } catch (SecurityException se) {
            // Swallow the exception and return false directly.
            se.printStackTrace();
            return false;
        }
        System.out.println(exists+""+isFile+""+canRead);
        return (exists && isFile && canRead);
    }
}
