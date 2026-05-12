package com.example.familia.Famille;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "famille")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Famille {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_famille")
    private Long idFamille;

    @Column(name = "nom_de_famille")
    private String nomDeFamille;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @Column(name = "origine_geographique")
    private String origineGeographique;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

}
