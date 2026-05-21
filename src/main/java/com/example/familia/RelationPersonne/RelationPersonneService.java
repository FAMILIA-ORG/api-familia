package com.example.familia.RelationPersonne;

import com.example.familia.Personne.Personne;
import com.example.familia.Personne.PersonneRepository;
import com.example.familia.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RelationPersonneService {

    private final RelationPersonneRepository relationPersonneRepository;
    private final PersonneRepository personneRepository;
    private final SecurityUtils securityUtils;

    private static final Map<TypeRelation, TypeRelation> RECIPROCITE = Map.ofEntries(
            Map.entry(TypeRelation.PERE,         TypeRelation.ENFANT),
            Map.entry(TypeRelation.MERE,         TypeRelation.ENFANT),
            Map.entry(TypeRelation.ENFANT,       TypeRelation.PERE),
            Map.entry(TypeRelation.CONJOINT,     TypeRelation.CONJOINT),
            Map.entry(TypeRelation.FRERE,        TypeRelation.FRERE),
            Map.entry(TypeRelation.SOEUR,        TypeRelation.SOEUR),
            Map.entry(TypeRelation.GRAND_PERE,   TypeRelation.PETIT_ENFANT),
            Map.entry(TypeRelation.GRAND_MERE,   TypeRelation.PETIT_ENFANT),
            Map.entry(TypeRelation.PETIT_ENFANT, TypeRelation.GRAND_PERE),
            Map.entry(TypeRelation.ONCLE,        TypeRelation.NEVEU),
            Map.entry(TypeRelation.TANTE,        TypeRelation.NIECE),
            Map.entry(TypeRelation.NEVEU,        TypeRelation.ONCLE),
            Map.entry(TypeRelation.NIECE,        TypeRelation.TANTE),
            Map.entry(TypeRelation.COUSIN,       TypeRelation.COUSIN),
            Map.entry(TypeRelation.COUSINE,      TypeRelation.COUSINE)
    );

    public List<RelationPersonne> getAllRelationPersonnes() {
        return relationPersonneRepository.findAll();
    }

    public List<RelationPersonne> getRelationsByPersonne(Long idPersonne) {
        Personne p = resolvePersonne(idPersonne);
        return relationPersonneRepository.findByPersonneSourceOrPersonneCible(p, p);
    }

    public RelationPersonne getRelationPersonneById(Long id) {
        return relationPersonneRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Relation introuvable"));
    }

    public RelationPersonne createRelationPersonne(RelationRequest request) {
        if (request.getIdPersonneSource() == null || request.getIdPersonneCible() == null || request.getTypeRelation() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "idPersonneSource, idPersonneCible et typeRelation sont requis");
        }
        if (request.getIdPersonneSource().equals(request.getIdPersonneCible())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Une personne ne peut pas avoir une relation avec elle-même");
        }

        Personne source = resolvePersonne(request.getIdPersonneSource());
        Personne cible  = resolvePersonne(request.getIdPersonneCible());

        if (relationPersonneRepository.existsByPersonneSourceAndPersonneCibleAndTypeRelation(source, cible, request.getTypeRelation())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cette relation existe déjà");
        }

        Long currentUserId = securityUtils.getCurrentUserId();

        RelationPersonne relation = new RelationPersonne();
        relation.setPersonneSource(source);
        relation.setPersonneCible(cible);
        relation.setTypeRelation(request.getTypeRelation());
        relation.setCreatedBy(currentUserId);
        relationPersonneRepository.save(relation);

        TypeRelation typeInverse = RECIPROCITE.get(request.getTypeRelation());
        if (typeInverse != null && !relationPersonneRepository.existsByPersonneSourceAndPersonneCibleAndTypeRelation(cible, source, typeInverse)) {
            RelationPersonne inverse = new RelationPersonne();
            inverse.setPersonneSource(cible);
            inverse.setPersonneCible(source);
            inverse.setTypeRelation(typeInverse);
            inverse.setCreatedBy(currentUserId);
            relationPersonneRepository.save(inverse);
        }

        return relation;
    }

    public RelationPersonne updateRelationPersonne(Long id, RelationRequest request) {
        if (request.getTypeRelation() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "typeRelation est requis");
        }
        RelationPersonne relation = getRelationPersonneById(id);
        relation.setTypeRelation(request.getTypeRelation());
        return relationPersonneRepository.save(relation);
    }

    public void deleteRelationPersonne(Long id) {
        relationPersonneRepository.deleteById(id);
    }

    private Personne resolvePersonne(Long idPersonne) {
        return personneRepository.findById(idPersonne)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Personne introuvable : " + idPersonne));
    }
}