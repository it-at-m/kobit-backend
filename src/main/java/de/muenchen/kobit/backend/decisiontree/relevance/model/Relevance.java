package de.muenchen.kobit.backend.decisiontree.relevance.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@IdClass(RelevanceId.class)
public class Relevance {

    @Id
    private UUID contactPointId;

    @Id
    private UUID pathId;

    private Integer position;

}
