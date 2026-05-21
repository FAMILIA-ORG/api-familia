package com.example.familia.Parainage;

import com.example.familia.Personne.Personne;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "parainage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Parainage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parainage")
    private Long idParainage;

    @ManyToOne
    @JoinColumn(name = "id_parrain", nullable = false)
    private Personne parrain;

    @ManyToOne
    @JoinColumn(name = "id_filleul", nullable = false)
    private Personne filleul;

    @Column(name = "created_by")
    private Long createdBy;
}
