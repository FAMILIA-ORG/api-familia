package com.example.familia.Interet;

import org.springframework.stereotype.Service;
import com.example.familia.Interet.Interet;
import com.example.familia.Interet.InteretRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class InteretService {
  private final InteretRepository interetRepository;

  public List<Interet> getAllInterets(){
    return interetRepository.findAll();
  }

  public Interet getInteretById(Long id){
    return interetRepository.findById(id).orElse(null);
  }

  public Interet createInteret(Interet interet){
     return interetRepository.save(interet);
  }

  public Interet updateInteret(Long id, Interet data){
    Interet interet = getInteretById(id);

    interet.setNomInteret(data.getNomInteret());
    interet.setDescription(data.getDescription());

    return interetRepository.save(interet);
  }

  public void deleteInteret(Long id){
    interetRepository.deleteById(id);
  }
}