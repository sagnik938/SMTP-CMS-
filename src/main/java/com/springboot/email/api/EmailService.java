package com.springboot.email.api;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;

@Service
public class EmailService {

	@Autowired     
	private JavaMailSender javaMailSender;   

	@Autowired
	Configuration fmConfiguration;

    // jrobikzyrppzxysn
    public boolean sendEmail(String to, String subject, String body){
        //email code
        boolean flag = false;
        String from = "sagnik938@gmail.com";
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(to);
		msg.setFrom(from);
		msg.setSubject(subject);
		msg.setText(body);
		try {
			javaMailSender.send(msg);
			flag = true;
		} catch (Exception e) {
			flag = false;
		}

    return(flag);
    }

	public boolean sendInvoice(EmailModel mail){
		MimeMessage mimeMessage =javaMailSender.createMimeMessage();
		String from = "sagnik938@gmail.com";
		boolean flag = false;

		Map<String,Object> m = new HashMap<>();
		m.put("Fname", "Arindam");
		m.put("lName","Chakrabarty");

        try {
 
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
 
            mimeMessageHelper.setSubject(mail.getSubject());
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(mail.getTo());
            mail.setBody(geContentFromTemplate(m));
            mimeMessageHelper.setText(mail.getBody(), true);
 
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
			flag = true;
        } catch (MessagingException e) {
            e.printStackTrace();
			flag = false;
        }
		return(flag);
    }
 
    public String geContentFromTemplate(Map < String, Object >model)     { 
        StringBuffer content = new StringBuffer();
 
        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("invoice_template.flth"), model));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
