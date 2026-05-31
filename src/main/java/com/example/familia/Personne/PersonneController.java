package com.example.familia.Personne;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.familia.RelationPersonne.RelationPersonne;
import com.example.familia.RelationPersonne.RelationPersonneService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/personnes")
@RequiredArgsConstructor
public class PersonneController {

    private final PersonneService personneService;
    private final PhotoService photoService;
    private final RelationPersonneService relationPersonneService;

    @GetMapping
    public List<Personne> getAllPersonnes() {
        return personneService.getAllPersonnes();
    }

    @GetMapping("/{id}")
    public Personne getPersonneById(@PathVariable Long id) {
        return personneService.getPersonneById(id);
    }

    @PostMapping
    public Personne create(@RequestBody PersonneRequest request) {
        return personneService.createPersonne(request);
    }

    @PutMapping("/{id}")
    public Personne updatePersonne(@PathVariable Long id, @RequestBody PersonneRequest request) {
        return personneService.updatePersonne(id, request);
    }

    @DeleteMapping("/{id}")
    public void deletePersonne(@PathVariable Long id) {
        personneService.deletePersonne(id);
    }

    @GetMapping("/{id}/relations")
    public List<RelationPersonne> getRelationsByPersonne(@PathVariable Long id) {
        return relationPersonneService.getRelationsByPersonne(id);
    }

    @PostMapping("/{id}/photo")
    public Personne uploadPhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return photoService.uploadPhoto(id, file);
    }

    @DeleteMapping("/{id}/photo")
    public Personne deletePhoto(@PathVariable Long id) {
        return photoService.deletePhoto(id);
    }
}
