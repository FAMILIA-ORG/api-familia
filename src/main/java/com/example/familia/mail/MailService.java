package com.example.familia.mail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
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
            sendWithSendgrid(to, subject, text, null);
        } else {
            sendWithSmtp(to, subject, text);
        }
    }

    public void sendHtml(String to, String subject, String plainText, String htmlBody) {
        if (isConfigured(sendgridApiKey) && isConfigured(sendgridFromEmail)) {
            sendWithSendgrid(to, subject, plainText, htmlBody);
        } else {
            sendWithSmtpHtml(to, subject, plainText, htmlBody);
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

    private void sendWithSmtpHtml(String to, String subject, String plainText, String html) {
        try {
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
            if (isConfigured(sendgridFromEmail)) {
                helper.setFrom(sendgridFromEmail);
            }
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(plainText, html);
            mailSender.send(msg);
        } catch (MessagingException e) {
            throw new RuntimeException("Erreur lors de l'envoi du mail HTML", e);
        }
    }

    private void sendWithSendgrid(String to, String subject, String text, String html) {
        List<Map<String, Object>> content = new ArrayList<>();
        content.add(Map.of("type", "text/plain", "value", text));
        if (html != null && !html.isBlank()) {
            content.add(Map.of("type", "text/html", "value", html));
        }

        Map<String, Object> body = Map.of(
                "personalizations", List.of(
                        Map.of("to", List.of(Map.of("email", to)))
                ),
                "from", Map.of("email", sendgridFromEmail),
                "subject", subject,
                "content", content
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

