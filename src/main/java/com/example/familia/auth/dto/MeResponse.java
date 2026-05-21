package com.example.familia.auth.dto;

import com.example.familia.Personne.Personne;

public record MeResponse(
        Long idUser,
        String email,
        String username,
        String role,
        Boolean active,
        Personne personne
) {}

