package com.example.familia.Interet;

import lombok.*;
import jakarta.persistence.*;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "interet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Interet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_interet")
    private Long idInteret;

    @Column(name = "nom_interet")
    private String nomInteret;

    @Column(name = "description")
    private String description;
}