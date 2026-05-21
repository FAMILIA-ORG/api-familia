package com.example.familia.RelationPersonne;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RelationRequest {
    private Long idPersonneSource;
    private Long idPersonneCible;
    private TypeRelation typeRelation;
}
