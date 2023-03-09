package de.muenchen.kobit.backend.decisiontree.branches;

import de.muenchen.kobit.backend.competence.Competence;
import de.muenchen.kobit.backend.decisiontree.view.DecisionPoint;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class WorkConflictBranch {

    private static final String roleQuestion = "Welche Rolle haben Sie?";
    private static final String conflictWithWho = "Mit wem haben Sie den Konflikt?";
    private static final String stressLevel = "Wie hoch ist Ihre persönliche Belastung?";
    private static final String escalationIndex =
            "Wie stark hat sich die Situation bereits verschärft?";

    private final DecisionPoint n1 =
            new DecisionPoint(
                    Competence.WORKPLACE_CONFLICT,
                    roleQuestion,
                    List.of(
                            Competence.EMPLOYEE.toCompetenceView(),
                            Competence.JUNIOR.toCompetenceView(),
                            Competence.EXECUTIVE.toCompetenceView()));

    private final DecisionPoint n2 =
            new DecisionPoint(
                    Competence.EMPLOYEE,
                    conflictWithWho,
                    List.of(
                            Competence.OPPOSITE_EMPLOYEE.toCompetenceView(),
                            Competence.OPPOSITE_EXECUTIVE.toCompetenceView(),
                            Competence.OPPOSITE_JUNIOR.toCompetenceView(),
                            Competence.OPPOSITE_OTHER_TEAM.toCompetenceView()));
    private final DecisionPoint n3 =
            new DecisionPoint(
                    Competence.EXECUTIVE,
                    conflictWithWho,
                    List.of(
                            Competence.OPPOSITE_EMPLOYEE.toCompetenceView(),
                            Competence.OPPOSITE_EXECUTIVE.toCompetenceView(),
                            Competence.OPPOSITE_JUNIOR.toCompetenceView(),
                            Competence.OPPOSITE_OTHER_TEAM.toCompetenceView()));
    private final DecisionPoint n4 =
            new DecisionPoint(
                    Competence.JUNIOR,
                    conflictWithWho,
                    List.of(
                            Competence.OPPOSITE_EMPLOYEE.toCompetenceView(),
                            Competence.OPPOSITE_EXECUTIVE.toCompetenceView(),
                            Competence.OPPOSITE_JUNIOR.toCompetenceView(),
                            Competence.OPPOSITE_OTHER_TEAM.toCompetenceView()));

    private final DecisionPoint n5 =
            new DecisionPoint(
                    Competence.OPPOSITE_EMPLOYEE,
                    stressLevel,
                    List.of(
                            Competence.STRESS_LOW.toCompetenceView(),
                            Competence.STRESS_MEDIUM.toCompetenceView(),
                            Competence.STRESS_HIGH.toCompetenceView()));
    private final DecisionPoint n6 =
            new DecisionPoint(
                    Competence.OPPOSITE_EXECUTIVE,
                    stressLevel,
                    List.of(
                            Competence.STRESS_LOW.toCompetenceView(),
                            Competence.STRESS_MEDIUM.toCompetenceView(),
                            Competence.STRESS_HIGH.toCompetenceView()));
    private final DecisionPoint n7 =
            new DecisionPoint(
                    Competence.OPPOSITE_JUNIOR,
                    stressLevel,
                    List.of(
                            Competence.STRESS_LOW.toCompetenceView(),
                            Competence.STRESS_MEDIUM.toCompetenceView(),
                            Competence.STRESS_HIGH.toCompetenceView()));
    private final DecisionPoint n8 =
            new DecisionPoint(
                    Competence.OPPOSITE_OTHER_TEAM,
                    stressLevel,
                    List.of(
                            Competence.STRESS_LOW.toCompetenceView(),
                            Competence.STRESS_MEDIUM.toCompetenceView(),
                            Competence.STRESS_HIGH.toCompetenceView()));

    private final DecisionPoint n9 =
            new DecisionPoint(
                    Competence.STRESS_HIGH,
                    escalationIndex,
                    List.of(
                            Competence.ESCALATION_LOW.toCompetenceView(),
                            Competence.ESCALATION_MEDIUM.toCompetenceView(),
                            Competence.ESCALATION_HIGH.toCompetenceView()));
    private final DecisionPoint n10 =
            new DecisionPoint(
                    Competence.STRESS_MEDIUM,
                    escalationIndex,
                    List.of(
                            Competence.ESCALATION_LOW.toCompetenceView(),
                            Competence.ESCALATION_MEDIUM.toCompetenceView(),
                            Competence.ESCALATION_HIGH.toCompetenceView()));
    private final DecisionPoint n11 =
            new DecisionPoint(
                    Competence.STRESS_LOW,
                    escalationIndex,
                    List.of(
                            Competence.ESCALATION_LOW.toCompetenceView(),
                            Competence.ESCALATION_MEDIUM.toCompetenceView(),
                            Competence.ESCALATION_HIGH.toCompetenceView()));

    private final HashMap<Competence, DecisionPoint> branchMap = new HashMap<>();

    public WorkConflictBranch() {
        branchMap.put(n1.getCompetence(), n1);
        branchMap.put(n2.getCompetence(), n2);
        branchMap.put(n3.getCompetence(), n3);
        branchMap.put(n4.getCompetence(), n4);
        branchMap.put(n5.getCompetence(), n5);
        branchMap.put(n6.getCompetence(), n6);
        branchMap.put(n7.getCompetence(), n7);
        branchMap.put(n8.getCompetence(), n8);
        branchMap.put(n9.getCompetence(), n9);
        branchMap.put(n10.getCompetence(), n10);
        branchMap.put(n11.getCompetence(), n11);
    }

    public DecisionPoint getNextNode(Competence competence) {
        return branchMap.get(competence);
    }
}
