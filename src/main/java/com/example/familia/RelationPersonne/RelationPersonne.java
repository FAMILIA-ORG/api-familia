package com.example.familia.RelationPersonne;

import lombok.*;
import jakarta.persistence.*;
import com.example.familia.Personne.Personne;

@Entity
@Table(
    name = "relation_personne",
    uniqueConstraints = @UniqueConstraint(columnNames = {"id_personne_source", "id_personne_cible", "type_relation"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RelationPersonne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_relation")
    private Long idRelation;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_relation", nullable = false)
    private TypeRelation typeRelation;

    @ManyToOne
    @JoinColumn(name = "id_personne_source")
    private Personne personneSource;

    @ManyToOne
    @JoinColumn(name = "id_personne_cible")
    private Personne personneCible;

    @Column(name = "created_by")
    private Long createdBy;
}