package com.example.familia.Interet;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.familia.Interet.Interet;
import com.example.familia.Interet.InteretService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/interets")
@RequiredArgsConstructor

public class InteretController {
  private final InteretService interetService;

  @GetMapping
  public List<Interet> getAllInterets(){
    return interetService.getAllInterets();
  }

  @GetMapping("/{id}")
  public Interet getInteretById(@PathVariable Long id){
    return interetService.getInteretById(id);
  }

  @PostMapping
  public Interet createInteret(@RequestBody Interet interet){
    return interetService.createInteret(interet);
  }

  @PutMapping("/{id}")
  public Interet updateInteret(@PathVariable Long id, @RequestBody Interet interet){
    return interetService.updateInteret(id, interet);
  }

  @DeleteMapping("/{id}")
  public void deleteInteret(@PathVariable Long id){
    interetService.deleteInteret(id);
  }
}