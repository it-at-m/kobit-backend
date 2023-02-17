package de.muenchen.kobit.backend.competence.model;

import de.muenchen.kobit.backend.competence.Competence;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@IdClass(CompetenceToContactPointId.class)
@Table(name = "competence")
public class CompetenceToContactPoint {

    // composite primary key
    @Id private UUID contactPointId;

    @Id
    @Enumerated(EnumType.STRING)
    private Competence competence;
}
