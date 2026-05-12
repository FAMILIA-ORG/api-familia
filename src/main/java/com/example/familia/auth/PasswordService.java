package com.example.familia.auth;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.familia.AppUser.AppUser;
import com.example.familia.AppUser.AppUserRepository;
import com.example.familia.auth.token.PasswordResetToken;
import com.example.familia.auth.token.PasswordResetTokenRepository;
import com.example.familia.mail.MailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordService {
    private final AppUserRepository appUserRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    @Value("${app.frontend-url}")
    private String frontendUrl;

    @Transactional
    public void forgotPassword(String email) {
        if (email == null || email.isBlank()) {
            return;
        }

        appUserRepository.findByEmail(email.trim().toLowerCase()).ifPresent(user -> {
            String token = UUID.randomUUID().toString().replace("-", "");
            PasswordResetToken prt = new PasswordResetToken(
                    null,
                    token,
                    user,
                    Instant.now().plus(Duration.ofMinutes(30)),
                    null);
            passwordResetTokenRepository.save(prt);

            String link = frontendUrl + "/reinitialiser-mot-de-passe?token=" + token;
            mailService.send(
                    user.getEmail(),
                    "Réinitialisation du mot de passe",
                    "Vous avez demandé une réinitialisation.\n\nLien:\n" + link + "\n\nCe lien expire dans 30 minutes.");
        });
    }

    @Transactional
    public void resetPassword(String token, String newPassword) {
        if (newPassword == null || newPassword.isBlank()) {
            throw new IllegalArgumentException("Nouveau mot de passe requis");
        }

        PasswordResetToken prt = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token invalide"));

        if (prt.getUsedAt() != null) {
            throw new IllegalArgumentException("Token déjà utilisé");
        }
        if (prt.getExpiresAt().isBefore(Instant.now())) {
            throw new IllegalArgumentException("Token expiré");
        }

        AppUser user = prt.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        appUserRepository.save(user);

        prt.setUsedAt(Instant.now());
        passwordResetTokenRepository.save(prt);
    }
}

