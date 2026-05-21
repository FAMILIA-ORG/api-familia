package com.example.familia.security;

import com.example.familia.AppUser.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

    private final AppUserRepository appUserRepository;

    public Long getCurrentUserId() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"))
                .getIdUser();
    }
}
