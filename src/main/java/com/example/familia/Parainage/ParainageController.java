package com.example.familia.Parainage;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/parainages")
@RequiredArgsConstructor
public class ParainageController {

    private final ParainageService parainageService;

    @GetMapping
    public List<Parainage> getAllParainages() {
        return parainageService.getAllParainages();
    }

    @GetMapping("/{id}")
    public Parainage getParainageById(@PathVariable Long id) {
        return parainageService.getParainageById(id);
    }

    @PostMapping
    public Parainage createParainage(@RequestBody ParainageRequest request) {
        return parainageService.createParainage(request);
    }

    @PutMapping("/{id}")
    public Parainage updateParainage(@PathVariable Long id, @RequestBody ParainageRequest request) {
        return parainageService.updateParainage(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteParainage(@PathVariable Long id) {
        parainageService.deleteParainage(id);
    }
}
