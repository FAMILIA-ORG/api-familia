package com.example.familia.auth;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        user.setActive(false);
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
        mailService.send(
                user.getEmail(),
                "Activation de votre compte",
                "Bienvenue.\n\nCliquez pour activer votre compte:\n" + activationLink + "\n\nCe lien expire dans 24h.");
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
        user.setActive(true);
        appUserRepository.save(user);

        vt.setUsedAt(Instant.now());
        verificationTokenRepository.save(vt);
    }

    public String login(LoginRequest req) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        return jwtService.generateToken(auth.getName());
    }
}

