package de.muenchen.kobit.backend.decisiontree.relevance.model;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@IdClass(RelevanceId.class)
public class Relevance {

    @Id private UUID contactPointId;

    @Id private UUID pathId;

    private Integer position;
}
