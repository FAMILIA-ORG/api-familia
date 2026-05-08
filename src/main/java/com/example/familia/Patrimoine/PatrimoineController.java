package com.example.familia.Patrimoine;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.familia.Patrimoine.Patrimoine;
import com.example.familia.Patrimoine.PatrimoineService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/patrimoines")
@RequiredArgsConstructor

public class PatrimoineController {
    private final PatrimoineService patrimoineService;

    @GetMapping
    public List<Patrimoine> getAllPatrimoines(){
        return patrimoineService.getAllPatrimoines();
        
    }

    @GetMapping("/{id}")
    public Patrimoine getPatrimoineById(@PathVariable Long id){
        return patrimoineService.getPatrimoineById(id);
    }

    @PostMapping
    public Patrimoine createPatrimoine(@RequestBody Patrimoine patrimoine){
        return patrimoineService.createPatrimoine(patrimoine);
    }

    @PutMapping("/{id}")
    public Patrimoine updatePatrimoine(@PathVariable Long id, @RequestBody Patrimoine patrimoine){
        return patrimoineService.updatePatrimoine(id, patrimoine);
    }

    @DeleteMapping("/{id}")
    public void deletePatrimoine(@PathVariable Long id){
        patrimoineService.deletePatrimoine(id);
    }   

}