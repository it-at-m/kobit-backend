package de.muenchen.kobit.backend.decisiontree.branches;

import de.muenchen.kobit.backend.competence.Competence;
import de.muenchen.kobit.backend.decisiontree.view.DecisionPoint;
import java.util.HashMap;
import java.util.List;

import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Component;

@Component
public class DiscriminationBranch {

    private final String roleQuestion = "Welche Rolle haben Sie?";
    private final String kindOfDiscrimination =
            "Aus welchem Grund fühlen Sie sich diskriminiert?";
    private final String kindOfSexualDiscrimination =
            "Mann-Frau Diskriminierung oder LGBTIQ* Diskriminierung?";

    // Welche Rolle haben Sie?
    private final DecisionPoint n1 =
            new DecisionPoint(
                    Competence.DISCRIMINATION,
                    roleQuestion,
                    List.of(
                            Competence.EMPLOYEE.toCompetenceView(),
                            Competence.EXECUTIVE.toCompetenceView(),
                            Competence.JUNIOR.toCompetenceView()));


    // Aus welchem grund fühlen Sie sich diskrimiert?
    private final DecisionPoint n2 =
            new DecisionPoint(
                    Competence.EMPLOYEE,
                    kindOfDiscrimination,
                    List.of(
                            Competence.ETHNIC_RACIAL.toCompetenceView(),
                            Competence.AGE.toCompetenceView(),
                            Competence.DISABLED.toCompetenceView(),
                            Competence.SEXUALITY.toCompetenceView(),
                            Competence.SEXUAL_IDENTITY.toCompetenceView()
                            ));
    private final DecisionPoint n3 =
            new DecisionPoint(
                    Competence.JUNIOR,
                    kindOfDiscrimination,
                    List.of(
                            Competence.ETHNIC_RACIAL.toCompetenceView(),
                            Competence.AGE.toCompetenceView(),
                            Competence.DISABLED.toCompetenceView(),
                            Competence.SEXUALITY.toCompetenceView(),
                            Competence.SEXUAL_IDENTITY.toCompetenceView()));

    private final DecisionPoint n4 =
            new DecisionPoint(
                    Competence.EXECUTIVE,
                    kindOfDiscrimination,
                    List.of(
                            Competence.ETHNIC_RACIAL.toCompetenceView(),
                            Competence.AGE.toCompetenceView(),
                            Competence.DISABLED.toCompetenceView(),
                            Competence.SEXUALITY.toCompetenceView(),
                            Competence.SEXUAL_IDENTITY.toCompetenceView()));


    // other
    private final DecisionPoint n5 =
            new DecisionPoint(
                    Competence.SEXUAL_IDENTITY,
                    kindOfSexualDiscrimination,
                    List.of(
                            Competence.EQUALITY.toCompetenceView(),
                            Competence.LGBTIQ.toCompetenceView()));

    private final HashMap<Competence, DecisionPoint> branchMap = new HashMap<>();

    public DiscriminationBranch() {
        branchMap.put(n1.getCompetence(), n1);
        branchMap.put(n2.getCompetence(), n2);
        branchMap.put(n3.getCompetence(), n3);
        branchMap.put(n4.getCompetence(), n4);
    }

    public DecisionPoint getNextNode(Competence competence) {
        return branchMap.get(competence);
    }
}
