package com.example.familia.Personne;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonneRequest {
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private LocalDate dateDeces;
    private String lieuDeNaissance;
    private String sexe;
    private String biographie;
    private String nationalite;
    private Integer generation;
    private String metier;
    private String descriptionMetier;
    private String adresse;
    private Long idFamille;
    private List<Long> interetIds;
}
