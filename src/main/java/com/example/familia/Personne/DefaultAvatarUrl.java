package com.example.familia.Personne;


public final class DefaultAvatarUrl {

    private static String baseUrl = "http://localhost:8080";

    public static final String MALE = baseUrl + "/avatars/default-male.png";
    public static final String FEMALE = baseUrl + "/avatars/default-female.png";

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
