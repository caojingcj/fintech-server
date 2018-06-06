package com.fintech.util.image;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**   
* @Title: FileTypeUtil.java 
* @Package com.medcfc.commons.utils.image 
* @author qierkang xyqierkang@163.com   
* @date 2017年8月8日 下午12:56:15  
* @Description: TODO[ 用一句话描述该文件做什么 ]
*/
public class FileTypeUtil {
    /** 
    * @Title: FileTypeUtil.java 
    * @param     设定文件 
    * @Description: TODO[ Constructor ]
    * @throws 
    */
    private FileTypeUtil() {
    }

    /** 
    * @Title: FileTypeUtil.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2017年8月8日 下午12:56:26  
    * @param @param src
    * @param @return    设定文件 
    * @Description: TODO[ 将文件头转换成16进制字符串 ]
    * @return 16进制字符串 
    * @throws 
    */
    private static String bytesToHexString(byte[] src) {
	StringBuilder stringBuilder = new StringBuilder();
	if (src == null || src.length <= 0) {
	    return null;
	}
	for (int i = 0; i < src.length; i++) {
	    int v = src[i] & 0xFF;
	    String hv = Integer.toHexString(v);
	    if (hv.length() < 2) {
		stringBuilder.append(0);
	    }
	    stringBuilder.append(hv);
	}
	return stringBuilder.toString();
    }

    /** 
    * @Title: FileTypeUtil.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2017年8月8日 下午12:56:48  
    * @param @param filePath 文件路径 
    * @param @param stream
    * @param @return 文件头
    * @param @throws IOException    设定文件 
    * @Description: TODO[ 得到文件头  ]
    * @throws 
    */
    private static String getFileContent(String filePath,InputStream stream) throws IOException {
	byte[] b = new byte[28];
	InputStream inputStream = null;
	try {
	    if(stream==null){
		inputStream = new FileInputStream(filePath);
	    }else{
		inputStream=stream;
	    }
	    inputStream.read(b, 0, 28);
	} catch (IOException e) {
	    e.printStackTrace();
	    throw e;
	} finally {
	    if (inputStream != null) {
		try {
		    inputStream.close();
		} catch (IOException e) {
		    e.printStackTrace();
		    throw e;
		}
	    }
	}
	return bytesToHexString(b);
    }

    /** 
    * @Title: FileTypeUtil.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2017年8月8日 下午12:57:09  
    * @param @param inputStream 文件路径
    * @param @return 文件类型 
    * @param @throws IOException    设定文件 
    * @Description: TODO[ 判断文件类型 ]
    * @throws 
    */
    public static FileType getInputStreamType(InputStream inputStream) throws IOException {
	String fileHead = getFileContent("",inputStream);
	if (fileHead == null || fileHead.length() == 0) {
	    return null;
	}
	fileHead = fileHead.toUpperCase();
	FileType[] fileTypes = FileType.values();
	for (FileType type : fileTypes) {
	    if (fileHead.startsWith(type.getValue())) {
		return type;
	    }
	}
	return null;
    }
    
    /** * 判断文件类型 * * @param filePath * 文件路径 * @return 文件类型 */
    public static FileType getType(String filePath) throws IOException {
	String fileHead = getFileContent(filePath,null);
	if (fileHead == null || fileHead.length() == 0) {
	    return null;
	}
	fileHead = fileHead.toUpperCase();
	FileType[] fileTypes = FileType.values();
	for (FileType type : fileTypes) {
	    if (fileHead.startsWith(type.getValue())) {
		return type;
	    }
	}
	return null;
    }

    public static void main(String args[]) throws Exception {
	System.out.println(FileTypeUtil.getType("d:\\aa.jpg"));
    }
}
