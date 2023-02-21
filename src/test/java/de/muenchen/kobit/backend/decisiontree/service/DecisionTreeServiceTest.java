package de.muenchen.kobit.backend.decisiontree.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import de.muenchen.kobit.backend.competence.Competence;
import de.muenchen.kobit.backend.competence.service.CompetenceService;
import de.muenchen.kobit.backend.contactpoint.view.ContactPointView;
import de.muenchen.kobit.backend.decisiontree.branches.*;
import de.muenchen.kobit.backend.decisiontree.view.DecisionPoint;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DecisionTreeServiceTest {

    private final WorkConflictBranch workConflictBranch = mock(WorkConflictBranch.class);
    private final MobbingBranch mobbingBranch = mock(MobbingBranch.class);
    private final DiscriminationBranch discriminationBranch = mock(DiscriminationBranch.class);
    private final HealthIssuesBranch healthIssuesBranch = mock(HealthIssuesBranch.class);
    private final Root root = mock(Root.class);
    private final CompetenceService competenceService = mock(CompetenceService.class);

    private DecisionTreeService decisionTreeService;

    @BeforeEach
    void init() {
        clearAllCaches();
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
    void getNextDecisionPointOrContactPointsTest_decisionPoint() {
        var competences = List.of(Competence.HEALTH_PROBLEMS);
        var decisionPoint =
                new DecisionPoint(
                        Competence.HEALTH_PROBLEMS,
                        "question",
                        List.of(Competence.ADDICTION.toCompetenceView()));
        when(healthIssuesBranch.getNextNode(Competence.HEALTH_PROBLEMS)).thenReturn(decisionPoint);
        var result = decisionTreeService.getNextDecisionPointOrContactPoints(competences);
        verify(competenceService, never()).findAllContactPointsForCompetences(any());
        verify(mobbingBranch, never()).getNextNode(any());
        verify(discriminationBranch, never()).getNextNode(any());
        verify(workConflictBranch, never()).getNextNode(any());
        assertThat(result.getContactPoints()).isEqualTo(null);
        assertThat(result.getDecisionPoint()).isEqualTo(decisionPoint);
    }

    @Test
    void getNextDecisionPointOrContactPointsTest_empty() {
        List<Competence> competences = List.of();
        var decisionPoint =
                new DecisionPoint(
                        Competence.HEALTH_PROBLEMS,
                        "question",
                        List.of(Competence.ADDICTION.toCompetenceView()));
        when(root.getRootOptions()).thenReturn(decisionPoint);
        var result = decisionTreeService.getNextDecisionPointOrContactPoints(competences);
        verify(competenceService, never()).findAllContactPointsForCompetences(any());
        verify(mobbingBranch, never()).getNextNode(any());
        verify(discriminationBranch, never()).getNextNode(any());
        verify(workConflictBranch, never()).getNextNode(any());
        assertThat(result.getContactPoints()).isEqualTo(null);
        assertThat(result.getDecisionPoint()).isEqualTo(decisionPoint);
    }

    @Test
    void getRootTest() {
        var decisionPoint =
                new DecisionPoint(
                        Competence.HEALTH_PROBLEMS,
                        "question",
                        List.of(Competence.ADDICTION.toCompetenceView()));
        when(root.getRootOptions()).thenReturn(decisionPoint);
        var result = decisionTreeService.getRoot();
        assertThat(result.getDecisionPoint()).isEqualTo(decisionPoint);
    }

    @Test
    void getNextDecisionPointOrContactPointsTest_end() {
        var id = UUID.randomUUID();
        var contactPoints = new ArrayList<ContactPointView>();
        contactPoints.add(
                new ContactPointView(
                        id, "test", "test", "test", "test", List.of(), List.of(), List.of()));
        contactPoints.add(
                new ContactPointView(
                        id, "test", "atest", "test", "test", List.of(), List.of(), List.of()));
        List<Competence> competences = List.of(Competence.ANTI_DEMOCRACY);
        when(competenceService.findAllContactPointsForCompetences(competences))
                .thenReturn(contactPoints);
        var result = decisionTreeService.getNextDecisionPointOrContactPoints(competences);
        verify(healthIssuesBranch, never()).getNextNode(any());
        verify(mobbingBranch, never()).getNextNode(any());
        verify(discriminationBranch, never()).getNextNode(any());
        verify(workConflictBranch, never()).getNextNode(any());
        assertThat(result.getDecisionPoint()).isEqualTo(null);
        assertThat(result.getContactPoints()).isEqualTo(contactPoints);
    }
}
