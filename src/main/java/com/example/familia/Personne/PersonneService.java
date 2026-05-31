package com.example.familia.Personne;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.familia.Famille.Famille;
import com.example.familia.Famille.FamilleRepository;
import com.example.familia.Interet.Interet;
import com.example.familia.Interet.InteretRepository;
import com.example.familia.security.SecurityUtils;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class PersonneService {

    private final PersonneRepository personneRepository;
    private final FamilleRepository familleRepository;
    private final InteretRepository interetRepository;
    private final SecurityUtils securityUtils;

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

    public Personne createPersonne(PersonneRequest request) {
        Personne personne = new Personne();
        applyFields(personne, request);
        personne.setCreatedBy(securityUtils.getCurrentUserId());
        return personneRepository.save(personne);
    }

    public Personne updatePersonne(Long id, PersonneRequest request) {
        Personne personne = requirePersonneById(id);
        applyFields(personne, request);
        return personneRepository.save(personne);
    }

    private void applyFields(Personne personne, PersonneRequest req) {
        personne.setNom(req.getNom());
        personne.setPrenom(req.getPrenom());
        personne.setDateNaissance(req.getDateNaissance());
        personne.setDateDeces(req.getDateDeces());
        personne.setLieuDeNaissance(req.getLieuDeNaissance());
        personne.setSexe(req.getSexe());
        personne.setBiographie(req.getBiographie());
        personne.setNationalite(req.getNationalite());
        personne.setGeneration(req.getGeneration());
        personne.setMetier(req.getMetier());
        personne.setDescriptionMetier(req.getDescriptionMetier());
        personne.setAdresse(req.getAdresse());

        if (req.getIdFamille() != null) {
            Famille famille = familleRepository.findById(req.getIdFamille())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Famille introuvable"));
            personne.setFamille(famille);
        } else {
            personne.setFamille(null);
        }

        if (req.getInteretIds() != null) {
            List<Interet> interets = interetRepository.findAllById(req.getInteretIds());
            if (interets.size() != req.getInteretIds().size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Un ou plusieurs intérêts introuvables");
            }
            personne.setInterets(new HashSet<>(interets));
        }
    }

    public void deletePersonne(Long id) {
        personneRepository.deleteById(id);
    }
}
