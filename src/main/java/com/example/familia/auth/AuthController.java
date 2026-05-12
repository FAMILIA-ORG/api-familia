package com.example.familia.auth;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.familia.AppUser.AppUserRepository;
import com.example.familia.auth.dto.AuthResponse;
import com.example.familia.auth.dto.ForgotPasswordRequest;
import com.example.familia.auth.dto.LoginRequest;
import com.example.familia.auth.dto.MeResponse;
import com.example.familia.auth.dto.RegisterRequest;
import com.example.familia.auth.dto.ResetPasswordRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final PasswordService passwordService;
    private final AppUserRepository appUserRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        authService.register(req);
        return ResponseEntity.ok(Map.of("message", "Inscription OK. Vérifiez votre email pour activer le compte."));
    }

    @GetMapping("/activate")
    public ResponseEntity<?> activate(@RequestParam String token) {
        authService.activateAccount(token);
        return ResponseEntity.ok(Map.of("message", "Compte activé. Vous pouvez vous connecter."));
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest req) {
        String token = authService.login(req);
        return new AuthResponse(token);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest req) {
        passwordService.forgotPassword(req.getEmail());
        return ResponseEntity.ok(Map.of("message", "Si l'email existe, un lien de réinitialisation a été envoyé."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest req) {
        passwordService.resetPassword(req.getToken(), req.getNewPassword());
        return ResponseEntity.ok(Map.of("message", "Mot de passe mis à jour."));
    }

    @GetMapping("/me")
    public MeResponse me(Authentication authentication) {
        String email = authentication.getName();
        var user = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Utilisateur introuvable"));

        return new MeResponse(
                user.getIdUser(),
                user.getEmail(),
                user.getUsername(),
                user.getRole(),
                user.isActive(),
                user.getPersonne() == null ? null : user.getPersonne().getIdPersonne()
        );
    }
}

