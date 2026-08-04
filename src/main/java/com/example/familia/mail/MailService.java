package com.example.familia.mail;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${sendgrid.api-key:}")
    private String sendgridApiKey;

    @Value("${sendgrid.from-email:}")
    private String sendgridFromEmail;

    private final RestClient restClient = RestClient.create();

    public void send(String to, String subject, String text) {
        if (isConfigured(sendgridApiKey) && isConfigured(sendgridFromEmail)) {
            sendWithSendgrid(to, subject, text);
        } else {
            sendWithSmtp(to, subject, text);
        }
    }

    private void sendWithSmtp(String to, String subject, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        if (isConfigured(sendgridFromEmail)) {
            msg.setFrom(sendgridFromEmail);
        }
        msg.setSubject(subject);
        msg.setText(text);
        mailSender.send(msg);
    }

    private void sendWithSendgrid(String to, String subject, String text) {
        Map<String, Object> body = Map.of(
                "personalizations", List.of(
                        Map.of("to", List.of(Map.of("email", to)))
                ),
                "from", Map.of("email", sendgridFromEmail),
                "subject", subject,
                "content", List.of(
                        Map.of("type", "text/plain", "value", text)
                )
        );

        restClient.post()
                .uri("https://api.sendgrid.com/v3/mail/send")
                .header("Authorization", "Bearer " + sendgridApiKey)
                .header("Content-Type", "application/json")
                .body(body)
                .retrieve()
                .toBodilessEntity();

        log.debug("Email envoyé via SendGrid à {}", to);
    }

    private boolean isConfigured(String value) {
        return value != null && !value.isBlank();
    }
}

