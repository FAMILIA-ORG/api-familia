package com.example.familia.auth;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import com.example.familia.AppUser.AppUser;
import com.example.familia.AppUser.AppUserRepository;
import com.example.familia.Personne.Personne;
import com.example.familia.Personne.PersonneRepository;
import com.example.familia.auth.dto.LoginRequest;
import com.example.familia.auth.dto.RegisterRequest;
import com.example.familia.auth.token.VerificationToken;
import com.example.familia.auth.token.VerificationTokenRepository;
import com.example.familia.mail.MailService;
import com.example.familia.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final AppUserRepository appUserRepository;
    private final PersonneRepository personneRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Value("${app.frontend-url}")
    private String baseUrl ;

    @Transactional
    public void register(RegisterRequest req) {
        if (req.getEmail() == null || req.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email requis");
        }
        if (req.getPassword() == null || req.getPassword().isBlank()) {
            throw new IllegalArgumentException("Mot de passe requis");
        }
        if (appUserRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email déjà utilisé");
        }

        Personne personne = new Personne();
        personne.setPrenom(req.getUsername());
        personne = personneRepository.save(personne);

        AppUser user = new AppUser();
        user.setEmail(req.getEmail().trim().toLowerCase());
        user.setUsername(req.getUsername());
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setIsActive(0);
        user.setPersonne(personne);
        user = appUserRepository.save(user);

        String token = UUID.randomUUID().toString().replace("-", "");
        VerificationToken vt = new VerificationToken(
                null,
                token,
                user,
                Instant.now().plus(Duration.ofHours(24)),
                null);
        verificationTokenRepository.save(vt);

        String activationLink = baseUrl + "/activation?token=" + token;

        // Send the activation email synchronously. Registration only succeeds
        // once the activation email has been sent.
        EmailContent activationEmail = buildActivationEmail(user.getUsername(), activationLink);
        mailService.sendHtml(
                user.getEmail(),
                "Activation de votre compte",
                activationEmail.plainText,
                activationEmail.html);
    }

    @Transactional
    public void activateAccount(String token) {
        VerificationToken vt = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token invalide"));

        if (vt.getUsedAt() != null) {
            throw new IllegalArgumentException("Token déjà utilisé");
        }
        if (vt.getExpiresAt().isBefore(Instant.now())) {
            throw new IllegalArgumentException("Token expiré");
        }

        AppUser user = vt.getUser();
        user.setIsActive(1);
        appUserRepository.save(user);

        vt.setUsedAt(Instant.now());
        verificationTokenRepository.save(vt);
    }

    public String login(LoginRequest req) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        return jwtService.generateToken(auth.getName());
    }

    private record EmailContent(String plainText, String html) {
    }

    private EmailContent buildActivationEmail(String prenom, String activationLink) {
        String safePrenom = HtmlUtils.htmlEscape(prenom);
        String safeLink = HtmlUtils.htmlEscape(activationLink);

        String plainText = "Bonjour " + prenom + ",\n\n"
                + "Merci pour votre inscription.\n"
                + "Pour activer votre compte, cliquez sur le lien suivant (ou copiez-le dans votre navigateur) :\n\n"
                + activationLink + "\n\n"
                + "Ce lien expire dans 24 heures.\n\n"
                + "Si vous n'êtes pas à l'origine de cette demande, vous pouvez ignorer cet e-mail.";

        String html = """
                <!DOCTYPE html>
                <html lang="fr">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Activez votre compte Familia</title>
                </head>
                <body style="margin:0;padding:0;background-color:#f4f4f4;font-family:Arial,Helvetica,sans-serif;">
                    <table role="presentation" width="100%" cellspacing="0" cellpadding="0" border="0">
                        <tr>
                            <td align="center" style="padding:40px 15px;">
                                <table role="presentation" width="600" cellspacing="0" cellpadding="0" border="0" style="max-width:600px;width:100%;background:#ffffff;border-radius:8px;box-shadow:0 2px 8px rgba(0,0,0,0.08);">
                                    <tr>
                                        <td style="padding:40px 30px;text-align:left;">
                                            <h1 style="color:#1a1a1a;font-size:24px;margin:0 0 20px;font-weight:600;">Activez votre compte Familia</h1>
                                            <p style="color:#333333;font-size:16px;line-height:1.6;margin:0 0 15px;">Bonjour {prenom},</p>
                                            <p style="color:#333333;font-size:16px;line-height:1.6;margin:0 0 25px;">Merci pour votre inscription. Pour finaliser la création de votre compte, veuillez cliquer sur le bouton ci-dessous.</p>
                                            <p style="text-align:center;margin:30px 0;">
                                                <a href="{link}" style="display:inline-block;padding:14px 28px;background:#007bff;color:#ffffff;text-decoration:none;border-radius:5px;font-weight:600;font-size:16px;">Activer mon compte</a>
                                            </p>
                                            <p style="color:#666666;font-size:14px;line-height:1.6;margin:0 0 15px;">Ce lien est valable 24 heures. Si vous n'êtes pas à l'origine de cette demande, vous pouvez ignorer cet e-mail.</p>
                                            <p style="color:#888888;font-size:13px;line-height:1.5;margin:0;">Si le bouton ne fonctionne pas, copiez ce lien dans votre navigateur :<br>
                                                <a href="{link}" style="color:#007bff;text-decoration:underline;">{link}</a>
                                            </p>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </body>
                </html>
                """.replace("{prenom}", safePrenom)
                   .replace("{link}", safeLink);

        return new EmailContent(plainText, html);
    }
}

