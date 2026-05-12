package com.example.familia.Personne;


public final class DefaultAvatarUrl {

    public static final String MALE = "/avatars/default-male.png";
    public static final String FEMALE = "/avatars/default-female.png";

    private DefaultAvatarUrl() {
    }

    public static String forSexe(String sexe) {
        if (sexe == null || sexe.isBlank()) {
            return MALE;
        }
        String s = sexe.trim().toUpperCase();
        if (s.startsWith("F")
                || s.contains("FEMME")
                || s.contains("FÉMININ")
                || s.contains("FEMININ")) {
            return FEMALE;
        }
        if (s.startsWith("M")
                || s.startsWith("H")
                || s.contains("HOMME")
                || s.contains("MASCULIN")) {
            return MALE;
        }
        return MALE;
    }
}
