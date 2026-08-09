package com.mail_sender.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {
    @NotBlank(message = "Recipient email address is required")
    @Email(message = "Invalid email address")
    private String to;

    @NotBlank(message = "Email subject is required")
    private String subject;

    @NotBlank(message = "Email body is required")
    private String body;


}
