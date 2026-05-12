package com.example.familia.Personne;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonneService {

    private final PersonneRepository personneRepository;

    public List<Personne> getAllPersonnes() {
        return personneRepository.findAll();
    }

    public Personne getPersonneById(Long id) {
        return personneRepository.findById(id).orElse(null);
    }

    public Personne requirePersonneById(Long id) {
        Personne p = getPersonneById(id);
        if (p == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Personne introuvable");
        }
        return p;
    }

    public Personne createPersonne(Personne personne) {
        return personneRepository.save(personne);
    }

    public Personne updatePersonne(Long id, Personne data) {
        Personne personne = requirePersonneById(id);
        personne.setPrenom(data.getPrenom());
        personne.setDateNaissance(data.getDateNaissance());
        personne.setLieuDeNaissance(data.getLieuDeNaissance());
        personne.setSexe(data.getSexe());
        personne.setPhoto(data.getPhoto());
        personne.setBiographie(data.getBiographie());
        personne.setNationalite(data.getNationalite());
        personne.setGeneration(data.getGeneration());
        personne.setMetier(data.getMetier());
        personne.setParrain1(data.getParrain1());
        personne.setParrain2(data.getParrain2());
        personne.setDescriptionMetier(data.getDescriptionMetier());
        personne.setAdresse(data.getAdresse());
        personne.setFamille(data.getFamille());
        return personneRepository.save(personne);
    }

    public void deletePersonne(Long id) {
        personneRepository.deleteById(id);
    }
}
