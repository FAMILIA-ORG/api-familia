package com.example.familia.Famille;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/familles")
@RequiredArgsConstructor
public class FamilleController {

    private final FamilleService familleService;

    @GetMapping
    public List<Famille> getAll() {
        return familleService.getAllFamilies();
    }

    @GetMapping("/{id}")
    public Famille getById(@PathVariable Long id) {
        return familleService.getFamilyById(id);
    }

    @PostMapping
    public Famille create(@RequestBody Famille famille) {
        return familleService.createFamille(famille);
    }

    @PutMapping("/{id}")
    public Famille update(@PathVariable Long id, @RequestBody Famille famille) {
        return familleService.updateFamille(id, famille);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        familleService.deleteFamille(id);
    }
}
