package com.example.familia.Commentaire;

import lombok.*;
import jakarta.persistence.*;
import com.example.familia.AppUser.AppUser;
import com.example.familia.Famille.Famille;

@Entity
@Table(name="commentaire")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Commentaire {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_commentaire")
    private Long idCommentaire;

    @Column(name="description")
    private String description;

    @ManyToOne
    @JoinColumn(name="id_famille")
    private Famille famille;

    @ManyToOne
    @JoinColumn(name="id_user")
    private AppUser user;

    @Column(name="created_by")
    private Long createdBy;

}


