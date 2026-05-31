package com.example.familia.Personne;

import com.example.familia.Famille.Famille;
import com.example.familia.Interet.Interet;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "personne")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Personne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_personne")
    private Long idPersonne;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @Column(name = "lieu_de_naissance")
    private String lieuDeNaissance;

    @Column(name = "sexe")
    private String sexe;

    @Getter(lombok.AccessLevel.NONE)
    @Column(name = "photo")
    private String photo;

    @Column(name = "biographie", columnDefinition = "TEXT")
    private String biographie;

    @Column(name = "nationalite")
    private String nationalite;

    @Column(name = "generation")
    private Integer generation;

    @Column(name = "metier")
    private String metier;

    @Column(name = "date_deces")
    private LocalDate dateDeces;

    @Column(name = "description_metier")
    private String descriptionMetier;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "created_by")
    private Long createdBy;

    @ManyToOne
    @JoinColumn(name = "id_famille")
    private Famille famille;

    @ManyToMany
    @JoinTable(
            name = "interets_personne",
            joinColumns = @JoinColumn(name = "id_personne"),
            inverseJoinColumns = @JoinColumn(name = "id_interet"))
    private Set<Interet> interets = new HashSet<>();
    public String getPhoto() {
        if (photo != null && !photo.isBlank()) {
            return photo;
        }
        return DefaultAvatarUrl.forSexe(sexe);
    }
}
