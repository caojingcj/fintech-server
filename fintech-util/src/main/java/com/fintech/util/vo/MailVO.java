package com.fintech.util.vo;

import java.util.List;
import java.util.Map;

/**   
* @Title: MailVO.java 
* @Package com.medcfc.commons.utils.vo 
* @author qierkang xyqierkang@163.com   
* @date 2018年1月5日 上午2:05:05  
* @Description: TODO[ 用一句话描述该文件做什么 ]
*/
public class MailVO {
    private String to;
    private String subject;
    private String content;
    private Map<String, String> attchFileData;
    private List<InputStreamDataVO> attchInputStreamDataList;

    public List<InputStreamDataVO> getAttchInputStreamDataList() {
        return attchInputStreamDataList;
    }

    public void setAttchInputStreamDataList(List<InputStreamDataVO> attchInputStreamDataList) {
        this.attchInputStreamDataList = attchInputStreamDataList;
    }

    public Map<String, String> getAttchFileData() {
        return attchFileData;
    }

    public void setAttchFileData(Map<String, String> attchFileData) {
        this.attchFileData = attchFileData;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
