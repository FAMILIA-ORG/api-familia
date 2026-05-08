package com.example.familia.Patrimoine;

import org.springframework.stereotype.Service;
import com.example.familia.Patrimoine.Patrimoine;
import com.example.familia.Patrimoine.PatrimoineRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatrimoineService { 
    private final PatrimoineRepository patrimoineRepository;

    public List<Patrimoine> getAllPatrimoines(){
        return patrimoineRepository.findAll();
    }

    public Patrimoine getPatrimoineById(Long id){
        return patrimoineRepository.findById(id).orElse(null);
    }

    public Patrimoine createPatrimoine(Patrimoine patrimoine){
        return patrimoineRepository.save(patrimoine);
    }

    public Patrimoine updatePatrimoine(Long id, Patrimoine data){
        Patrimoine patrimoine = getPatrimoineById(id);

        patrimoine.setValeurEstimee(data.getValeurEstimee());
        patrimoine.setDateAcquisition(data.getDateAcquisition());
        patrimoine.setModeAcquisition(data.getModeAcquisition());
        patrimoine.setStatut(data.getStatut());
        patrimoine.setFamille(data.getFamille());

        return patrimoineRepository.save(patrimoine);
    }

    public void deletePatrimoine(Long id){
        patrimoineRepository.deleteById(id);
    }
}