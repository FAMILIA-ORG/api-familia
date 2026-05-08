package com.example.familia.Personne;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.familia.Personne.Personne;
import com.example.familia.Personne.PersonneService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/personnes")
@RequiredArgsConstructor

public class PersonneController {
    private final PersonneService personneService;

    @GetMapping
    public List<Personne> getAllPersonnes(){
        return personneService.getAllPersonnes();
    }

    @GetMapping("/{id}")
    public Personne getPersonneById(@PathVariable Long id){
        return personneService.getPersonneById(id);
    }

    @PostMapping
    public Personne createPersonne(@RequestBody Personne personne){
        return personneService.createPersonne(personne);
    }

    @PutMapping("/{id}")
    public Personne updatePersonne(@PathVariable Long id, @RequestBody Personne personne){
        return personneService.updatePersonne(id, personne);
    }

    @DeleteMapping("/{id}")
    public void deletePersonne(@PathVariable Long id){
        personneService.deletePersonne(id);
    }
}