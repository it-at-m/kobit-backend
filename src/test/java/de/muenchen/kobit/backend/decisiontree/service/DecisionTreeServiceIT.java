package de.muenchen.kobit.backend.decisiontree.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import de.muenchen.kobit.backend.competence.Competence;
import de.muenchen.kobit.backend.competence.service.CompetenceService;
import de.muenchen.kobit.backend.competence.view.CompetenceView;
import de.muenchen.kobit.backend.decisiontree.branches.*;
import de.muenchen.kobit.backend.decisiontree.view.DecisionPoint;
import java.util.List;
import java.util.stream.Collectors;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class DecisionTreeServiceIT {

    private final Root root = new Root();
    private final WorkConflictBranch workConflictBranch = new WorkConflictBranch();
    private final MobbingBranch mobbingBranch = new MobbingBranch();
    private final DiscriminationBranch discriminationBranch = new DiscriminationBranch();
    private final HealthIssuesBranch healthIssuesBranch = new HealthIssuesBranch();

    private final CompetenceService competenceService = Mockito.mock(CompetenceService.class);

    private DecisionTreeService decisionTreeService;

    @BeforeEach
    void init() {
        decisionTreeService =
                new DecisionTreeService(
                        workConflictBranch,
                        mobbingBranch,
                        discriminationBranch,
                        healthIssuesBranch,
                        root,
                        competenceService);
    }

    @Test
    void getRootTest() {
        val result = decisionTreeService.getRoot();
        assertThat(result.getDecisionPoint().getAnswerOptions().size()).isEqualTo(8);
        assertEquals(
                result.getDecisionPoint().getAnswerOptions().stream()
                        .map(CompetenceView::getGermanDescription)
                        .collect(Collectors.toList()),
                sortedByGermanName().getAnswerOptions().stream()
                        .map(CompetenceView::getGermanDescription)
                        .collect(Collectors.toList()));
    }

    private DecisionPoint sortedByGermanName() {
        return new DecisionPoint(
                null,
                "Worum geht es?",
                List.of(
                        Competence.WORKPLACE_CONFLICT.toCompetenceView(),
                        Competence.ANTI_DEMOCRACY.toCompetenceView(),
                        Competence.DISCRIMINATION.toCompetenceView(),
                        Competence.HEALTH_PROBLEMS.toCompetenceView(),
                        Competence.DOMESTIC_VIOLENCE.toCompetenceView(),
                        Competence.MOBBING.toCompetenceView(),
                        Competence.PRIVATE_PROBLEMS.toCompetenceView(),
                        Competence.SEXUAL_HARASSMENT.toCompetenceView()));
    }
}
