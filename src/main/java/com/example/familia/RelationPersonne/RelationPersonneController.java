package com.example.familia.RelationPersonne;

import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    public RelationPersonne createRelationPersonne(@RequestBody RelationRequest request){
        return relationPersonneService.createRelationPersonne(request);
    }

    @PutMapping("/{id}")
    public RelationPersonne updateRelationPersonne(@PathVariable Long id, @RequestBody RelationRequest request){
        return relationPersonneService.updateRelationPersonne(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteRelationPersonne(@PathVariable Long id){
        relationPersonneService.deleteRelationPersonne(id);
    }
}