package com.example.familia.Commentaire;

import org.springframework.stereotype.Service;
import com.example.familia.Commentaire.Commentaire;
import com.example.familia.Commentaire.CommentaireRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentaireService {
    private final CommentaireRepository commentaireRepository;
    public List<Commentaire> getAllCommentaires(){
        return commentaireRepository.findAll();
    }

    public Commentaire getCommentaireById(Long id){
        return commentaireRepository.findById(id).orElse(null);
    }

    public Commentaire createCommentaire(Commentaire commentaire){
        return commentaireRepository.save(commentaire);
    }
    
    public Commentaire updateCommentaire(Long id, Commentaire data){
        Commentaire commentaire = getCommentaireById(id);

        commentaire.setDescription(data.getDescription());
        commentaire.setFamille(data.getFamille());
        commentaire.setUser(data.getUser());

        return commentaireRepository.save(commentaire);
    }
    
    public void deleteCommentaire(Long id){
        commentaireRepository.deleteById(id);
    }
}