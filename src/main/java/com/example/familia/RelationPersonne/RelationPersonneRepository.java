package com.example.familia.RelationPersonne;

import com.example.familia.Personne.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RelationPersonneRepository extends JpaRepository<RelationPersonne, Long> {

    List<RelationPersonne> findByPersonneSourceOrPersonneCible(Personne source, Personne cible);

    boolean existsByPersonneSourceAndPersonneCibleAndTypeRelation(
            Personne source, Personne cible, TypeRelation type);
}