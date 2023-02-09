package de.muenchen.kobit.backend.competence.view;

import de.muenchen.kobit.backend.competence.Competence;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CompetenceView {

    private Competence competence;
    private String germanDescription;
    private String shortDescription;

    public CompetenceView(
            Competence competence, String germanDescription, String shortDescription) {
        this.competence = competence;
        this.germanDescription = germanDescription;
        this.shortDescription = shortDescription;
    }
}
