package de.muenchen.kobit.backend.decisiontree.relevance.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RelevanceId implements Serializable {

    private UUID contactPointId;

    private UUID pathId;

}
