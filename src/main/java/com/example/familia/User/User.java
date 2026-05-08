package com.example.familia.User;

import lombok.*;
import jakarta.persistence.*;
import com.example.familia.User.User;
import com.example.familia.Personne.Personne;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long idUser;

    @Column(name = "role")
    private String role;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "is_active")
    private Integer isActive;

    @OneToOne
    @JoinColumn(name = "id_personne")
    private Personne personne;
}