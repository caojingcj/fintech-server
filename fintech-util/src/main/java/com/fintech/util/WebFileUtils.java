package com.fintech.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;


/**   
* @Title: WebFileUtils.java 
* @Package com.mybatis.util 
* @author qierkang xyqierkang@163.com   
* @date 2017年11月30日 下午1:33:56  
* @Description: TODO[ 文件转换工具类 ]
*/
public class WebFileUtils {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
        .getLogger(WebFileUtils.class);

    /**
     * 根据url拿取file
     * 
     * @param url
     * @param suffix
     *            文件后缀名
     * */
    public static File createFileByUrl(String url, String suffix) {
        byte[] byteFile = getImageFromNetByUrl(url);
        if (byteFile != null) {
            File file = getFileFromBytes(byteFile,suffix);
            return file;
        } else {
            logger.info("生成文件失败！");
            return null;
        }
    }

    /**
     * 根据地址获得数据的字节流
     * 
     * @param strUrl
     *            网络连接地址
     * @return
     */
    private static byte[] getImageFromNetByUrl(String strUrl) {
        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            conn.setUseCaches(false); 
            conn.setRequestProperty("Connection", "Keep-Alive");  
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  
            InputStream inStream = conn.getInputStream();// 通过输入流获取图片数据
            byte[] btImg = readInputStream(inStream);// 得到图片的二进制数据
            return btImg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从输入流中获取数据
     * 
     * @param inStream
     *            输入流
     * @return
     * @throws Exception
     */
    private static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    // 创建临时文件
    private static File getFileFromBytes(byte[] b, String suffix) {
        BufferedOutputStream stream = null;
        File file = null;
        try {
            file = File.createTempFile("qek_pattern", "." + suffix);
//            System.out.println("临时文件位置：" + file.getCanonicalPath());
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }
    
    /** 
    * @Title: WebFileUtils.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2017年11月30日 下午1:26:02  
    * @param @param url
    * @param @return    设定文件 
    * @Description: TODO[ 返回MultipartFile ]
    * @throws 
    */
    public static MultipartFile createImg(String url){
        try {
            // File转换成MutipartFile
            String prefix=url.substring(url.lastIndexOf(".")+1);
            File file = WebFileUtils.createFileByUrl(url, prefix);
            FileInputStream inputStream = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile(file.getName(), inputStream);
            //注意这里面填啥，MultipartFile里面对应的参数就有啥，比如我只填了name，则
            //MultipartFile.getName()只能拿到name参数，但是originalFilename是空。
            return multipartFile;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
    public static File createFileImg(String url){
        try {
            // File转换成MutipartFile
            String prefix=url.substring(url.lastIndexOf(".")+1);
            File file = WebFileUtils.createFileByUrl(url, prefix);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    public static void writeImageToDisk(byte[] img, String fileName,File file){    
        try {    
            FileOutputStream fops = new FileOutputStream(file); 
            fops.write(img);    
            fops.flush();    
            fops.close(); 
        } catch (Exception e) {    
            e.printStackTrace();    
        }    
    }
    
    
    public static boolean createFile(String destFileName) {  
        File file = new File(destFileName);  
        if(file.exists()) {  
//            System.out.println("创建单个文件" + destFileName + "失败，目标文件已存在！");  
            return false;  
        }  
        if (destFileName.endsWith(File.separator)) {  
            System.out.println("创建单个文件" + destFileName + "失败，目标文件不能为目录！");  
            return false;  
        }  
        //判断目标文件所在的目录是否存在  
        if(!file.getParentFile().exists()) {  
            //如果目标文件所在的目录不存在，则创建父目录  
            System.out.println("目标文件所在目录不存在，准备创建它！");  
            if(!file.getParentFile().mkdirs()) {  
                System.out.println("创建目标文件所在目录失败！");  
                return false;  
            }  
        }  
        //创建目标文件  
        try {  
            if (file.createNewFile()) {  
                System.out.println("创建单个文件" + destFileName + "成功！");  
                return true;  
            } else {  
                System.out.println("创建单个文件" + destFileName + "失败！");  
                return false;  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
            System.out.println("创建单个文件" + destFileName + "失败！" + e.getMessage());  
            return false;  
        }  
    }  
     
     
    public static boolean createDir(String destDirName) {  
        File dir = new File(destDirName);  
        if (dir.exists()) {  
//            System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");  
            return false;  
        }  
        if (!destDirName.endsWith(File.separator)) {  
            destDirName = destDirName + File.separator;  
        }  
        //创建目录  
        if (dir.mkdirs()) {  
            System.out.println("创建目录" + destDirName + "成功！");  
            return true;  
        } else {  
            System.out.println("创建目录" + destDirName + "失败！");  
            return false;  
        }  
    }  
     
     
    public static String createTempFile(String prefix, String suffix, String dirName) {  
        File tempFile = null;  
        if (dirName == null) {  
            try{  
                //在默认文件夹下创建临时文件  
                tempFile = File.createTempFile(prefix, suffix);  
                //返回临时文件的路径  
                return tempFile.getCanonicalPath();  
            } catch (IOException e) {  
                e.printStackTrace();  
                System.out.println("创建临时文件失败！" + e.getMessage());  
                return null;  
            }  
        } else {  
            File dir = new File(dirName);  
            //如果临时文件所在目录不存在，首先创建  
            if (!dir.exists()) {  
                if (!WebFileUtils.createDir(dirName)) {  
                    System.out.println("创建临时文件失败，不能创建临时文件所在的目录！");  
                    return null;  
                }  
            }  
            try {  
                //在指定目录下创建临时文件  
                tempFile = File.createTempFile(prefix, suffix, dir);  
                return tempFile.getCanonicalPath();  
            } catch (IOException e) {  
                e.printStackTrace();  
                System.out.println("创建临时文件失败！" + e.getMessage());  
                return null;  
            }  
        }  
    }  
    
    public static void main(String[] args) {
//        String path="http://image.medcfc.com/hos/contract/1479351091351_100002协议.pdf";
//        byte[] btImg = getImageFromNetByUrl(path);
//        String fileName = path.substring(path.indexOf("_")+1, path.length());   
//        String str="http://image.medcfc.com/hos/contract/1479351091351_100002协议.pdf";
//        System.out.println(str.substring(str.indexOf("_")+1, str.length()));
        //创建目录  
        String dirName = "D:/manageImg";
        WebFileUtils.createDir(dirName);
        //创建文件  
        String fileName = dirName + "/100002/tempFile.txt";
        WebFileUtils.createFile(fileName);
        //创建临时文件  
//        String prefix = "temp";  
//        String suffix = ".txt";  
//        for (int i = 0; i < 10; i++) {  
//            System.out.println("创建了临时文件："  
//                    + WebFileUtils.createTempFile(prefix, suffix, dirName));  
//        }  
//        //在默认目录下创建临时文件  
//        for (int i = 0; i < 10; i++) {  
//            System.out.println("在默认目录下创建了临时文件："  
//                    + WebFileUtils.createTempFile(prefix, suffix, null));  
//        } 
    }
}
