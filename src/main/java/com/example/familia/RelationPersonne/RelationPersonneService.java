package com.example.familia.RelationPersonne;

import org.springframework.stereotype.Service;
import com.example.familia.RelationPersonne.RelationPersonne;
import com.example.familia.RelationPersonne.RelationPersonneRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RelationPersonneService {
    private final RelationPersonneRepository relationPersonneRepository;

    public List<RelationPersonne> getAllRelationPersonnes(){
        return relationPersonneRepository.findAll();
    }
    
    public RelationPersonne getRelationPersonneById(Long id){
        return relationPersonneRepository.findById(id).orElse(null);
    }

    public RelationPersonne createRelationPersonne(RelationPersonne relationPersonne){
        return relationPersonneRepository.save(relationPersonne);
    }
    
    public RelationPersonne updateRelationPersonne(Long id, RelationPersonne data){
        RelationPersonne relationPersonne = getRelationPersonneById(id);

        relationPersonne.setLibelleRelation(data.getLibelleRelation());
        relationPersonne.setPersonneSource(data.getPersonneSource());
        relationPersonne.setPersonneCible(data.getPersonneCible());

        return relationPersonneRepository.save(relationPersonne);
    }

    public void deleteRelationPersonne(Long id){
        relationPersonneRepository.deleteById(id);
    }
}  