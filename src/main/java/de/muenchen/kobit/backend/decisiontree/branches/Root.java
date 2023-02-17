package de.muenchen.kobit.backend.decisiontree.branches;

import de.muenchen.kobit.backend.competence.Competence;
import de.muenchen.kobit.backend.decisiontree.view.DecisionPoint;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class Root {

    private final String caseQuestion = "Worum geht es?";

    private final DecisionPoint n1 =
            new DecisionPoint(
                    null,
                    caseQuestion,
                    List.of(
                            Competence.WORKPLACE_CONFLICT.toCompetenceView(),
                            Competence.MOBBING.toCompetenceView(),
                            Competence.DOMESTIC_VIOLENCE.toCompetenceView(),
                            Competence.ANTI_DEMOCRACY.toCompetenceView(),
                            Competence.DISCRIMINATION.toCompetenceView(),
                            Competence.SEXUAL_HARASSMENT.toCompetenceView(),
                            Competence.PRIVATE_PROBLEMS.toCompetenceView(),
                            Competence.HEALTH_PROBLEMS.toCompetenceView()));

    public DecisionPoint getRootOptions() {
        return n1;
    }
}
