package com.fintech.util.vo;

import java.io.InputStream;

/**   
* @Title: InputStreamDataVO.java 
* @Package com.medcfc.commons.utils.vo 
* @author qierkang xyqierkang@163.com   
* @date 2018年1月5日 上午2:05:01  
* @Description: TODO[ 用一句话描述该文件做什么 ]
*/
public class InputStreamDataVO {
	private String filename;
	private InputStream inputStream;
	private String mimeType;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
}
