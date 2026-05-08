package com.example.familia.Personne;

import lombok.*;
import jakarta.persistence.*;
import java.util.Set;
import java.util.HashSet;
import java.time.LocalDate;
import com.example.familia.Famille.Famille;
import com.example.familia.Interet.Interet;

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

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @Column(name = "lieu_de_naissance")
    private String lieuDeNaissance;

    @Column(name = "sexe")
    private String sexe;

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

    @Column(name="parrain_1")
    private String parrain1;

    @Column(name="parrain_2")
    private String parrain2;

    @Column(name = "description_metier")
    private String descriptionMetier;

    @Column(name = "adresse")
    private String adresse;

    @ManyToOne
    @JoinColumn(name = "id_famille")
    private Famille famille;


    @ManyToMany
    @JoinTable(
    name = "interets_personne",
    joinColumns = @JoinColumn(name = "id_personne"),
    inverseJoinColumns = @JoinColumn(name = "id_interet"))
    private Set<Interet> interets = new HashSet<>();
}

