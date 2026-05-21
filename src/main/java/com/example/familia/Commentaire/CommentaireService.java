package com.example.familia.Commentaire;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import com.example.familia.AppUser.AppUser;
import com.example.familia.AppUser.AppUserRepository;
import com.example.familia.Famille.Famille;
import com.example.familia.Famille.FamilleRepository;
import com.example.familia.exception.BadRequestException;
import com.example.familia.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import com.example.familia.Commentaire.CommentaireRequest;
import org.springframework.security.core.context.SecurityContextHolder;



@Service
@RequiredArgsConstructor

public class CommentaireService {
    private final CommentaireRepository commentaireRepository;
    private final FamilleRepository familleRepository;
    private final AppUserRepository appUserRepository;
    private final SecurityUtils securityUtils;


    public List<Commentaire> getAllCommentaires(){
        return commentaireRepository.findAll();
    }

    public Commentaire getCommentaireById(Long id){
        return commentaireRepository.findById(id).orElse(null);
    }

      public Commentaire createCommentaire(CommentaireRequest request) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        AppUser currentUser = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        Famille famille = familleRepository.findById(request.getIdFamille())
                .orElseThrow(() -> new RuntimeException("Famille introuvable"));

        Commentaire commentaire = new Commentaire();
        commentaire.setDescription(request.getDescription());
        commentaire.setFamille(famille);
        commentaire.setUser(currentUser);

        commentaire.setCreatedBy(securityUtils.getCurrentUserId());
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
