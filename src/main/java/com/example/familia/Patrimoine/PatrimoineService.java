package com.example.familia.Patrimoine;

import org.springframework.stereotype.Service;
import com.example.familia.Patrimoine.Patrimoine;
import com.example.familia.Patrimoine.PatrimoineRepository;
import com.example.familia.security.SecurityUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatrimoineService { 
    private final PatrimoineRepository patrimoineRepository;
    private final SecurityUtils securityUtils;

    public List<Patrimoine> getAllPatrimoines(){
        return patrimoineRepository.findAll();
    }

    public Patrimoine getPatrimoineById(Long id){
        return patrimoineRepository.findById(id).orElse(null);
    }

    public Patrimoine createPatrimoine(Patrimoine patrimoine){
        patrimoine.setCreatedBy(securityUtils.getCurrentUserId());
        return patrimoineRepository.save(patrimoine);
    }

    public Patrimoine updatePatrimoine(Long id, Patrimoine data){
        Patrimoine patrimoine = getPatrimoineById(id);

        patrimoine.setValeurEstimee(data.getValeurEstimee());
        patrimoine.setDateAcquisition(data.getDateAcquisition());
        patrimoine.setModeAcquisition(data.getModeAcquisition());
        patrimoine.setStatut(data.getStatut());
        patrimoine.setFamille(data.getFamille());
        patrimoine.setProprietaire(data.getProprietaire());

        return patrimoineRepository.save(patrimoine);
    }

    public void deletePatrimoine(Long id){
        patrimoineRepository.deleteById(id);
    }
}