package com.mail_sender.controller;

import com.mail_sender.dto.EmailRequest;
import com.mail_sender.services.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/email")
@CrossOrigin(origins="*")
public class EmailController {


    @Autowired
    private EmailService emailService;
    @PostMapping("/send")
    public ResponseEntity<?> sendEmail(@Valid @RequestBody EmailRequest emailRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            Map<String ,String > errors = bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(
                            fieldError -> fieldError.getField(),
                            fieldError -> fieldError.getDefaultMessage(),
                            (existing, replacement) -> existing
                    ));
            return ResponseEntity.badRequest().body(errors);
        }
        try{
            emailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody());
            Map<String,String> map = new HashMap<>();
            map.put("message", "Email sent successfully.");
            return ResponseEntity.ok(map);
        }catch (Exception e){
            Map<String,String> map = new HashMap<>();
            map.put("message",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
        }
    }
}
