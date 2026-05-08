package com.example.familia.Patrimoine;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;
import com.example.familia.Famille.Famille;

@Entity
@Table(name = "patrimoine")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patrimoine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_patrimoine")
    private Long idPatrimoine;

    @Column(name = "valeur_estimee")
    private String valeurEstimee;

    @Column(name = "date_acquisition")
    private LocalDate dateAcquisition;

    @Column(name = "mode_acquisition")
    private String modeAcquisition;

    @Column(name = "statut")
    private String statut;

    @ManyToOne
    @JoinColumn(name = "id_famille")
    private Famille famille;
}