package com.example.familia.Commentaire;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.familia.Commentaire.Commentaire;
import com.example.familia.Commentaire.CommentaireService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/commentaires")
@RequiredArgsConstructor

public class CommentaireController {
    private final CommentaireService commentaireService;
    @GetMapping
    public List<Commentaire> getAllCommentaires(){
        return commentaireService.getAllCommentaires();
    }

    @GetMapping("/{id}")
    public Commentaire getCommentaireById(@PathVariable Long id){
        return commentaireService.getCommentaireById(id);
    }

    @PostMapping
    public Commentaire createCommentaire(@RequestBody Commentaire commentaire){
        return commentaireService.createCommentaire(commentaire);
    }

    @PutMapping("/{id}")
    public Commentaire updateCommentaire(@PathVariable Long id, @RequestBody Commentaire commentaire){
        return commentaireService.updateCommentaire(id, commentaire);
    }

    @DeleteMapping("/{id}")
    public void deleteCommentaire(@PathVariable Long id){
        commentaireService.deleteCommentaire(id);
    }

}