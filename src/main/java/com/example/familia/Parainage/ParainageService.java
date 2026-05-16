package com.example.familia.Parainage;

import com.example.familia.Personne.Personne;
import com.example.familia.Personne.PersonneRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ParainageService {

    private final ParainageRepository parainageRepository;
    private final PersonneRepository personneRepository;

    public List<Parainage> getAllParainages() {
        return parainageRepository.findAll();
    }

    public Parainage getParainageById(Long id) {
        return parainageRepository.findById(id).orElse(null);
    }

    public Parainage createParainage(Parainage data) {
        Personne parrain = resolvePersonne(data.getParrain());
        Personne filleul = resolvePersonne(data.getFilleul());
        if (parrain == null || filleul == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parrain et filleul requis");
        }
        Parainage p = new Parainage();
        p.setParrain(parrain);
        p.setFilleul(filleul);
        return parainageRepository.save(p);
    }

    public Parainage updateParainage(Long id, Parainage data) {
        Parainage parainage = getParainageById(id);
        if (parainage == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Parainage introuvable");
        }
        Personne parrain = resolvePersonne(data.getParrain());
        Personne filleul = resolvePersonne(data.getFilleul());
        if (parrain == null || filleul == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parrain et filleul requis");
        }
        parainage.setParrain(parrain);
        parainage.setFilleul(filleul);
        return parainageRepository.save(parainage);
    }

    public void deleteParainage(Long id) {
        parainageRepository.deleteById(id);
    }

    private Personne resolvePersonne(Personne ref) {
        if (ref == null || ref.getIdPersonne() == null) {
            return null;
        }
        return personneRepository.getReferenceById(ref.getIdPersonne());
    }
}
