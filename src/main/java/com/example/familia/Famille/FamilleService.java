package com.example.familia.Famille;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.familia.security.SecurityUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FamilleService {

    private final FamilleRepository familleRepository;
    private final SecurityUtils securityUtils;

    public List<Famille> getAllFamilies() {
        return familleRepository.findAll();
    }

    public Famille getFamilyById(Long id) {
        return familleRepository.findById(id).orElse(null);
    }

    public Famille createFamille(Famille famille) {
        famille.setCreatedBy(securityUtils.getCurrentUserId());
        return familleRepository.save(famille);
    }

    public Famille updateFamille(Long id, Famille data) {
        Famille famille = getFamilyById(id);
        if (famille == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Famille introuvable");
        }
        famille.setNomDeFamille(data.getNomDeFamille());
        famille.setDateCreation(data.getDateCreation());
        famille.setOrigineGeographique(data.getOrigineGeographique());
        famille.setDescription(data.getDescription());
        famille.setPersonneFondatrice(data.getPersonneFondatrice());
        return familleRepository.save(famille);
    }

    public void deleteFamille(Long id) {
        familleRepository.deleteById(id);
    }

    public Famille requireFamilyById(Long id) {
        Famille f = getFamilyById(id);
        if (f == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Famille introuvable");
        }
        return f;
    }
}
