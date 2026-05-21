package com.example.familia.Parainage;

import com.example.familia.Personne.Personne;
import com.example.familia.Personne.PersonneRepository;
import com.example.familia.security.SecurityUtils;
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
    private final SecurityUtils securityUtils;

    public List<Parainage> getAllParainages() {
        return parainageRepository.findAll();
    }

    public Parainage getParainageById(Long id) {
        return parainageRepository.findById(id).orElse(null);
    }

    public Parainage createParainage(ParainageRequest request) {
        Personne parrain = resolvePersonneById(request.getIdParrain());
        Personne filleul = resolvePersonneById(request.getIdFilleul());
        Parainage p = new Parainage();
        p.setParrain(parrain);
        p.setFilleul(filleul);
        p.setCreatedBy(securityUtils.getCurrentUserId());
        return parainageRepository.save(p);
    }

    public Parainage updateParainage(Long id, ParainageRequest request) {
        Parainage parainage = getParainageById(id);
        if (parainage == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Parainage introuvable");
        }
        parainage.setParrain(resolvePersonneById(request.getIdParrain()));
        parainage.setFilleul(resolvePersonneById(request.getIdFilleul()));
        return parainageRepository.save(parainage);
    }

    public void deleteParainage(Long id) {
        parainageRepository.deleteById(id);
    }

    private Personne resolvePersonneById(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "idParrain et idFilleul requis");
        }
        return personneRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Personne introuvable : " + id));
    }
}
