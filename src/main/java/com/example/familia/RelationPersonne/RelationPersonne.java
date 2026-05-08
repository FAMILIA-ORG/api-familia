package com.example.familia.RelationPersonne;

import lombok.*;
import jakarta.persistence.*;
import com.example.familia.Personne.Personne;

@Entity
@Table(name = "relation_personne")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RelationPersonne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_relation")
    private Long idRelation;

    @Column(name = "libelle_relation")
    private String libelleRelation;

    @ManyToOne
    @JoinColumn(name = "id_personne_source")
    private Personne personneSource;

    @ManyToOne
    @JoinColumn(name = "id_personne_cible")
    private Personne personneCible;
}