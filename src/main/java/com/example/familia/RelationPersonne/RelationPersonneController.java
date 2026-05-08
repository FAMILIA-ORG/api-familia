package com.example.familia.RelationPersonne;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.familia.RelationPersonne.RelationPersonne;
import com.example.familia.RelationPersonne.RelationPersonneService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/relationpersonnes")
@RequiredArgsConstructor

public class RelationPersonneController {
    private final RelationPersonneService relationPersonneService;

    @GetMapping
    public List<RelationPersonne> getAllRelations(){
        return relationPersonneService.getAllRelationPersonnes();
    }

    @GetMapping("/{id}")
    public RelationPersonne getRelationPersonneById(@PathVariable Long id){
        return relationPersonneService.getRelationPersonneById(id);
    }

    @PostMapping
    public RelationPersonne createRelationPersonne(@RequestBody RelationPersonne relation){
        return relationPersonneService.createRelationPersonne(relation);
    }

    @PutMapping("/{id}")
    public RelationPersonne updateRelationPersonne(@PathVariable Long id, @RequestBody RelationPersonne relation){
        return relationPersonneService.updateRelationPersonne(id, relation);
    }

    @DeleteMapping("/{id}")
    public void deleteRelationPersonne(@PathVariable Long id){
        relationPersonneService.deleteRelationPersonne(id);
    }
}