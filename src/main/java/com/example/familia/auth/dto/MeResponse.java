package com.example.familia.auth.dto;

public record MeResponse(
        Long id,
        String email,
        String username,
        String role,
        boolean active,
        Long personneId
) {}

