package com.example.familia.Personne;


public final class DefaultAvatarUrl {

    private DefaultAvatarUrl() {
    }

    public static String forSexe(String sexe) {
        if (sexe == null || sexe.isBlank()) {
            return "/avatars/default-male.png";
        }
        String s = sexe.trim().toUpperCase();
        if (s.startsWith("F")
                || s.contains("FEMME")
                || s.contains("FÉMININ")
                || s.contains("FEMININ")) {
            return "/avatars/default-female.png";
        }
        if (s.startsWith("M")
                || s.startsWith("H")
                || s.contains("HOMME")
                || s.contains("MASCULIN")) {
            return "/avatars/default-male.png";
        }
        return "/avatars/default-male.png";
    }
}
