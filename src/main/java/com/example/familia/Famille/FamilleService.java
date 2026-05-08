package com.example.familia.Famille;

import org.springframework.stereotype.Service;
import com.example.familia.Famille.Famille;
import com.example.familia.Famille.FamilleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FamilleService{
    private final FamilleRepository familleRepository;


    public List<Famille> getAllFamilies(){
        return familleRepository.findAll();
    }

    public Famille getFamilyById(Long id){
        return familleRepository.findById(id).orElse(null);
    }

    public Famille createFamille(Famille famille){
        return familleRepository.save(famille);
    }

    public Famille updateFamille(Long id, Famille data) {
        Famille famille = getFamilyById(id);

        famille.setNomDeFamille(data.getNomDeFamille());
        famille.setDateCreation(data.getDateCreation());
        famille.setOrigineGeographique(data.getOrigineGeographique());
        famille.setBlasonUrl(data.getBlasonUrl());
        famille.setDescription(data.getDescription());

        return familleRepository.save(famille);
    }

    public void deleteFamille(Long id){
        familleRepository.deleteById(id);
    }
}