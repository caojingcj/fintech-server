package com.fintech.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fintech.util.vo.InputStreamDataVO;
import com.fintech.util.vo.MailVO;


/**   
* @Title: MailUtil.java 
* @Package com.medcfc.commons.utils 
* @author qierkang xyqierkang@163.com   
* @date 2018年1月5日 上午1:58:35  
* @Description: TODO[ 用一句话描述该文件做什么 ]
*/
public class MailUtil {
	
    Date date = DateUtils.zeroDateSSS(new Date());
    
    private static final Logger logger = LoggerFactory.getLogger(MailUtil.class);
    
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.setProperty("mail.smtp.auth", "true");// 必须 普通客户端
        props.setProperty("mail.transport.protocol", "smtp");// 必须选择协议
        props.put("mail.smtp.ssl.enable", "true");
        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);// 设置debug模式 在控制台看到交互信息
        Message msg = new MimeMessage(session); // 建立一个要发送的信息
        msg.setText("li72  welcome ");// 设置简单的发送内容
        Transport transport = session.getTransport();// 发送信息的工具
        transport.connect("smtp.16fenqi.com", 25, "qierkang@16fenqi.com", "@320796356388O0");// 发件人邮箱号
        msg.setFrom(new InternetAddress("qierkang@16fenqi.com"));// 发件人邮箱号
        msg.setSubject("test");
        transport.sendMessage(msg, new Address[] {new InternetAddress("xyqierkang@163.com")});// 对方的地址
        transport.close();
    }
	
	/**
	 * 寄信 for javamail
	 * 
	 * @throws Exception
	 */
	public static void sendMail(MailVO mailVO, Session mailSession) throws Exception {
		MimeMessage msg = new MimeMessage(mailSession);
		msg.setSubject(mailVO.getSubject(), "UTF-8");
//		msg.setFrom(new InternetAddress(ResourceUtil.getMailUser()));
		
		InternetAddress[] addressArray = null;
		InternetAddress address = null;
		String to = mailVO.getTo();
		if(to.indexOf(";") > 0){
			List<InternetAddress> addressList = new ArrayList<InternetAddress>();
			for(String mailAddress : to.split(";")){
				address = new InternetAddress(mailAddress);
				addressList.add(address);
			}
			addressArray = (InternetAddress[]) addressList.toArray(new InternetAddress[addressList.size()]);
		}else{
			addressArray = new InternetAddress[1];
			addressArray[0] = new InternetAddress(to);
		}
		msg.setRecipients(RecipientType.TO, addressArray);
		
		//信件內容
		Multipart multipart = new MimeMultipart("mixed");
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText(mailVO.getContent());
		multipart.addBodyPart(messageBodyPart);
		
		//夾帶檔案
		if(mailVO.getAttchInputStreamDataList() != null){
			BodyPart fileBodyPart = null;
			DataSource source = null;
			for(InputStreamDataVO vo : mailVO.getAttchInputStreamDataList()){
				fileBodyPart = new MimeBodyPart();
				try {
					source = new ByteArrayDataSource(vo.getInputStream(), vo.getMimeType());
					if(source != null && source.getInputStream().available() > 0){
						fileBodyPart.setDataHandler(new DataHandler(source));
						fileBodyPart.setFileName(MimeUtility.encodeText(vo.getFilename(), "UTF-8", "B"));
						multipart.addBodyPart(fileBodyPart);
					}
				} catch (Exception e) {
				    logger.error(e.getMessage());
					continue;
				}
			}
		}
		
		if(mailVO.getAttchFileData() != null){
			BodyPart fileBodyPart = null;
			DataSource source = null;
			for(String filePath : mailVO.getAttchFileData().keySet()){
				fileBodyPart = new MimeBodyPart();
				try {
					source = new FileDataSource(filePath);
					if(source != null && source.getInputStream().available() > 0){
						fileBodyPart.setDataHandler(new DataHandler(source));
						fileBodyPart.setFileName(MimeUtility.encodeText(mailVO.getAttchFileData().get(filePath), "UTF-8", "B"));
						multipart.addBodyPart(fileBodyPart);
					}
				} catch (Exception e) {
				    logger.error(e.getMessage());
					continue;
				}
			}
		}
		msg.setContent(multipart);
		
		Transport.send(msg);
	}
}
