package de.muenchen.kobit.backend.decisiontree.branches;

import de.muenchen.kobit.backend.competence.Competence;
import de.muenchen.kobit.backend.decisiontree.view.DecisionPoint;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class HealthIssuesBranch {

    private static final String kindOfHealthIssues = "Welches gesundheitliche Problem haben Sie?";

    private final DecisionPoint n1 =
            new DecisionPoint(
                    Competence.HEALTH_PROBLEMS,
                    kindOfHealthIssues,
                    List.of(
                            Competence.PHYSICAL.toCompetenceView(),
                            Competence.PSYCHOLOGICAL.toCompetenceView(),
                            Competence.ADDICTION.toCompetenceView()));

    private final HashMap<Competence, DecisionPoint> branchMap = new HashMap<>();

    public HealthIssuesBranch() {
        branchMap.put(n1.getCompetence(), n1);
    }

    public DecisionPoint getNextNode(Competence competence) {
        return branchMap.get(competence);
    }
}
