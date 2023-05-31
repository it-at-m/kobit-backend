package de.muenchen.kobit.backend.decisiontree.service;

import de.muenchen.kobit.backend.competence.Competence;
import de.muenchen.kobit.backend.competence.service.CompetenceService;
import de.muenchen.kobit.backend.competence.view.CompetenceView;
import de.muenchen.kobit.backend.contactpoint.view.ContactPointView;
import de.muenchen.kobit.backend.decisiontree.branches.DiscriminationBranch;
import de.muenchen.kobit.backend.decisiontree.branches.HealthIssuesBranch;
import de.muenchen.kobit.backend.decisiontree.branches.MobbingBranch;
import de.muenchen.kobit.backend.decisiontree.branches.Root;
import de.muenchen.kobit.backend.decisiontree.branches.WorkConflictBranch;
import de.muenchen.kobit.backend.decisiontree.view.DecisionContactPointWrapper;
import de.muenchen.kobit.backend.decisiontree.view.DecisionPoint;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DecisionTreeService {

    private static final List<Competence> competenceWithoutQuestions =
            List.of(
                    Competence.ANTI_DEMOCRACY,
                    Competence.PRIVATE_PROBLEMS,
                    Competence.DOMESTIC_VIOLENCE,
                    Competence.SEXUAL_HARASSMENT);

    private final WorkConflictBranch workConflictBranch;
    private final MobbingBranch mobbingBranch;
    private final DiscriminationBranch discriminationBranch;
    private final HealthIssuesBranch healthIssuesBranch;
    private final Root root;

    private final CompetenceService competenceService;

    DecisionTreeService(
            WorkConflictBranch workConflictBranch,
            MobbingBranch mobbingBranch,
            DiscriminationBranch discriminationBranch,
            HealthIssuesBranch healthIssuesBranch,
            Root root,
            CompetenceService competenceService) {
        this.workConflictBranch = workConflictBranch;
        this.mobbingBranch = mobbingBranch;
        this.discriminationBranch = discriminationBranch;
        this.healthIssuesBranch = healthIssuesBranch;
        this.root = root;
        this.competenceService = competenceService;
    }

    @Transactional(readOnly = true)
    public DecisionContactPointWrapper getNextDecisionPointOrContactPoints(
            List<Competence> selectedCompetences, String department) {
        if (selectedCompetences.isEmpty()) {
            return getRoot();
        }
        Competence rootSelection =
                selectedCompetences.stream()
                        .findFirst()
                        .orElseThrow(
                                () ->
                                        new IllegalStateException(
                                                "This should not have happened! There need to be"
                                                        + " at least one competence selected!"));
        Competence lastSelectedDecision = getLastElement(selectedCompetences);
        DecisionPoint result = getNextDecisionPointOrNull(lastSelectedDecision, rootSelection);
        if (result == null) {

            return new DecisionContactPointWrapper(
                    orderAlphabetically(
                            competenceService.findAllContactPointsForCompetences(
                                    selectedCompetences, department)));

        } else {
            return new DecisionContactPointWrapper(result);
        }
    }

    public DecisionContactPointWrapper getRoot() {
        var t = root.getRootOptions();
        final List<CompetenceView> options = new ArrayList<>(t.getAnswerOptions());
        Collections.sort(options);
        return new DecisionContactPointWrapper(
                new DecisionPoint(t.getCompetence(), t.getQuestion(), options));
    }

    private DecisionPoint getNextDecisionPointOrNull(
            Competence lastSelectedDecision, Competence rootSelection) {
        switch (rootSelection) {
            case HEALTH_PROBLEMS:
                return healthIssuesBranch.getNextNode(lastSelectedDecision);
            case MOBBING:
                return mobbingBranch.getNextNode(lastSelectedDecision);
            case DISCRIMINATION:
                return discriminationBranch.getNextNode(lastSelectedDecision);
            case WORKPLACE_CONFLICT:
                return workConflictBranch.getNextNode(lastSelectedDecision);
            default:
                return null;
        }
    }

    private static Competence getLastElement(List<Competence> selectedCompetences) {
        return selectedCompetences.get(selectedCompetences.size() - 1);
    }

    private static List<ContactPointView> orderAlphabetically(
            List<ContactPointView> contactPointViews) {
        Collator collator = Collator.getInstance(Locale.GERMAN);
        contactPointViews.sort((c1, c2) -> collator.compare(c1.getShortCut(), c2.getShortCut()));
        return contactPointViews;
    }
}
