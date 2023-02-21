package de.muenchen.kobit.backend.competence.model;

import de.muenchen.kobit.backend.competence.Competence;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CompetenceToContactPointId implements Serializable {
    private UUID contactPointId;

    @Enumerated(EnumType.STRING)
    private Competence competence;
}
