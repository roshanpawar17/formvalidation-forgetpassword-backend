package com.reactspringboot.service;

import java.util.Properties;

import org.springframework.stereotype.Service;

import com.reactspringboot.entities.EmailRequest;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



@Service
public class SendEmail {
	public boolean sendMail(String emailTo,String msg ) {
		
		boolean s=false;
		
		String from="rjcroshan12sci72@gmail.com";
		String password="ydapeeospqrpvkib";
		
		Properties properties=System.getProperties();
		
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");
		
		
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(from,password);
            }
        });
		
		MimeMessage m=new MimeMessage(session);
		try {
			m.setFrom(from);
			m.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
			m.setSubject("OTP FROM ROSHAN");
			m.setText(msg);

			Transport.send(m);
			s=true;
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return s;
	}
	
}
