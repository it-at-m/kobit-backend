package de.muenchen.kobit.backend.decisiontree.branches;

import de.muenchen.kobit.backend.competence.Competence;
import de.muenchen.kobit.backend.decisiontree.view.DecisionPoint;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MobbingBranch {

    private final String roleQuestion = "Welche Rolle haben Sie?";

    private final String whoIsMobbing = "Durch wen werden Sie gemobbt/fühlen Sie sich gemobbt?";

    private final String stressQuestion = "Wie hoch ist ihre persönliche Belastung?";

    private final DecisionPoint n1 =
            new DecisionPoint(
                    Competence.MOBBING,
                    roleQuestion,
                    List.of(
                            Competence.EMPLOYEE.toCompetenceView(),
                            Competence.JUNIOR.toCompetenceView(),
                            Competence.EXECUTIVE.toCompetenceView()));

    private final DecisionPoint n2 =
            new DecisionPoint(
                    Competence.EMPLOYEE,
                    whoIsMobbing,
                    List.of(
                            Competence.OPPOSITE_EMPLOYEE.toCompetenceView(),
                            Competence.OPPOSITE_EXECUTIVE.toCompetenceView(),
                            Competence.OPPOSITE_JUNIOR.toCompetenceView()));
    private final DecisionPoint n3 =
            new DecisionPoint(
                    Competence.JUNIOR,
                    whoIsMobbing,
                    List.of(
                            Competence.OPPOSITE_EMPLOYEE.toCompetenceView(),
                            Competence.OPPOSITE_EXECUTIVE.toCompetenceView(),
                            Competence.OPPOSITE_JUNIOR.toCompetenceView()));
    private final DecisionPoint n4 =
            new DecisionPoint(
                    Competence.EXECUTIVE,
                    whoIsMobbing,
                    List.of(
                            Competence.OPPOSITE_EMPLOYEE.toCompetenceView(),
                            Competence.OPPOSITE_EXECUTIVE.toCompetenceView(),
                            Competence.OPPOSITE_JUNIOR.toCompetenceView()));

    private final DecisionPoint n5 =
            new DecisionPoint(
                    Competence.OPPOSITE_EMPLOYEE,
                    stressQuestion,
                    List.of(
                            Competence.STRESS_LOW.toCompetenceView(),
                            Competence.STRESS_MEDIUM.toCompetenceView(),
                            Competence.STRESS_HIGH.toCompetenceView()));
    private final DecisionPoint n6 =
            new DecisionPoint(
                    Competence.OPPOSITE_EXECUTIVE,
                    stressQuestion,
                    List.of(
                            Competence.STRESS_LOW.toCompetenceView(),
                            Competence.STRESS_MEDIUM.toCompetenceView(),
                            Competence.STRESS_HIGH.toCompetenceView()));
    private final DecisionPoint n7 =
            new DecisionPoint(
                    Competence.OPPOSITE_JUNIOR,
                    stressQuestion,
                    List.of(
                            Competence.STRESS_LOW.toCompetenceView(),
                            Competence.STRESS_MEDIUM.toCompetenceView(),
                            Competence.STRESS_HIGH.toCompetenceView()));

    private final HashMap<Competence, DecisionPoint> branchMap = new HashMap<>();

    public MobbingBranch() {
        branchMap.put(n1.getCompetence(), n1);
        branchMap.put(n2.getCompetence(), n2);
        branchMap.put(n3.getCompetence(), n3);
        branchMap.put(n4.getCompetence(), n4);
        branchMap.put(n5.getCompetence(), n5);
        branchMap.put(n6.getCompetence(), n6);
        branchMap.put(n7.getCompetence(), n7);
    }

    public DecisionPoint getNextNode(Competence competence) {
        return branchMap.get(competence);
    }
}
