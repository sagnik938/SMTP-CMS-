package com.springboot.email.api;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;
    
    @PostMapping("/sendmail")
    public ResponseEntity<?> sendEmail(@RequestBody EmailModel emailModel){
        boolean fl = this.emailService.sendEmail(emailModel.getTo(), emailModel.getSubject(), emailModel.getBody());
        if( fl ){
            return(ResponseEntity.ok("Email sent"));
        }
        return(ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Messaage not sent"));
    }
    @PostMapping("/sendtemplatemail")
    public ResponseEntity<?> sendTemplateEmail(@RequestBody EmailModel emailModel){
        boolean fl = this.emailService.sendInvoice(emailModel);
        if( fl ){
            return(ResponseEntity.ok("Email sent"));
        }
        return(ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Messaage not sent"));
    }
}
